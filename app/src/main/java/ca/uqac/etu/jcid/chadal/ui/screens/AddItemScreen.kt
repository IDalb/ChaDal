package ca.uqac.etu.jcid.chadal.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import ca.uqac.etu.jcid.chadal.R
import ca.uqac.etu.jcid.chadal.data.Article
import ca.uqac.etu.jcid.chadal.data.ArticleDao
import ca.uqac.etu.jcid.chadal.data.ShoppingListUiState
import ca.uqac.etu.jcid.chadal.data.categories
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects
import androidx.compose.runtime.rememberCoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    modifier: Modifier = Modifier,
    listUiState: ShoppingListUiState,
    currentShoppingListId: Long,
    articleDao: ArticleDao,
    onValidateButtonClicked: (Article) -> Unit,
    onCancelButtonClicked: () -> Unit

) {
    var priceInput by remember { mutableStateOf("") }
    val price = priceInput.toDoubleOrNull() ?: 0.0

    var name by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    val context = LocalContext.current
    var uri by remember { mutableStateOf(Uri.EMPTY) }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_add_item)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = modifier
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (listUiState.lastScanValue.displayValue == "") stringResource(R.string.no_barcode)
                else stringResource(
                    R.string.barcode_value,
                    listUiState.lastScanValue.displayValue
                ),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )

            Divider()


            OutlinedTextField(
                label = { Text(stringResource(R.string.price)) },
                placeholder = { Text("0") },
                singleLine = true,
                trailingIcon = { Text(stringResource(R.string.currency_cad)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                value = priceInput,
                onValueChange = {
                    if (it.toDoubleOrNull() != null || it.isEmpty())
                        priceInput = it
                },
                isError = priceInput.isNotEmpty() && priceInput.toDoubleOrNull() == null,
                modifier = Modifier.fillMaxWidth()
            )


            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = stringResource(selectedCategory.name),
                    onValueChange = {},
                    label = { Text(stringResource(R.string.placeholder_category)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    readOnly = true,
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(stringResource(category.name)) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            }
                        )
                    }
                }
            }
            Text(
                text = stringResource(R.string.optional_infos),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 40.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.article_name)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )


            if (!LocalInspectionMode.current) {
                TakePhotoFromCamera { uri = it }
            }

            Spacer(modifier = Modifier.weight(1f))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = onCancelButtonClicked,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) { Text(stringResource(R.string.cancel)) }

                Button(
                    onClick = {
                        println("current id" + currentShoppingListId)

                        val imageUri = if (uri == Uri.EMPTY) {
                            getImageUriForCategory(context, context.getString(selectedCategory.name)).toString()

                        } else {
                            uri.toString()
                        }

                        val article = Article(
                            shoppingListId = currentShoppingListId,
                            name = name,
                            categoryName = selectedCategory.name,
                            taxPercentage = selectedCategory.taxPercentage,
                            price = price,
                            imageResource = imageUri
                        )

                        onValidateButtonClicked(article)
                    },
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                ) {
                    Text(stringResource(R.string.finish))
                }

            }
        }
    }
}



fun CheckFieldsAndSubmit() {

}

@Composable
fun PhotoCapture(capturedImageUri: Uri, captureFunction: ()->Unit = {}) {
    val composable: @Composable ()->Unit

    if (capturedImageUri.path?.isNotEmpty() == true) {
        composable = {
                Image(
                painter = rememberAsyncImagePainter(capturedImageUri),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    } else {
        composable = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.add_photo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(40.dp)
                )
                Text(stringResource(R.string.add_image))
            }
        }
    }

    OutlinedButton(
        onClick = captureFunction,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .height(200.dp)
            .aspectRatio(1f, true)
    ) { composable.invoke() }
}

@Composable
fun TakePhotoFromCamera(setMethod: (Uri)->Unit) {
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    var capturedImageUri by remember { mutableStateOf(Uri.EMPTY) }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        capturedImageUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission granted!", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission denied...", Toast.LENGTH_SHORT).show()
        }
    }

    setMethod(capturedImageUri)

    PhotoCapture(capturedImageUri) {
        val permissionCheckResult =
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            cameraLauncher.launch(uri)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH-mm_ss", Locale.US).format(Date())
    val imageFileName = "ARTICLE_IMG_$timeStamp"
    val image = File.createTempFile(imageFileName, ".jpg", externalCacheDir)

    return image
}


/*
@Preview
@Composable
fun AddItemScreenPreview() {
    ChaDalTheme {
        AddItemScreen(listUiState = ShoppingListUiState(),
            currentShoppingListId = 100, articleDao = articleDao)
    }
}
*/