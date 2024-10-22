package ca.uqac.etu.jcid.chadal.ui.screens

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
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // For paddings, spacings etc.
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.* // Compose material 3
import androidx.compose.runtime.* // For state variables
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import ca.uqac.etu.jcid.chadal.R
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    onCancelButtonClicked: () -> Unit = {},
    onValidateButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var price by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) } // Contrôle l'état d'expansion du menu déroulant

    Scaffold (
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
                .padding(innerPadding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Code-barres : 000000000000",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )

            HorizontalDivider()

            OutlinedTextField(
                value = price,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) { // Assure que seuls les chiffres sont acceptés
                        price = it
                    }
                },
                label = { Text("Prix") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = { expanded = true }) {
                    Text(if (selectedCategory.isEmpty()) "Catégorie" else selectedCategory)
                    Icon(
                        painter = painterResource(id = android.R.drawable.arrow_down_float),
                        contentDescription = null
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    val categories = listOf("Produits laitiers", "Viandes", "Fruits", "Légumes", "Boissons")
                    categories.forEach { category ->
                        DropdownMenuItem(onClick = {
                            selectedCategory = category
                            expanded = false
                        }) {
                            Text(text = category)
                        }
                    }
                }
            }

            Text(
                text = "Informations facultatives",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 40.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nom") },
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_camera),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    colorFilter = ColorFilter.tint(Color.Black)
                )
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
                    onClick = onValidateButtonClicked,
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                ) {
                    Text(stringResource(R.string.finish))
                }
            }
        }
    }
}

fun DropdownMenuItem(onClick: () -> Unit, interactionSource: @Composable () -> Unit) {

}

@Preview
@Composable
fun AddItemScreenPreview() {
    ChaDalTheme {
        AddItemScreen()
    }
}
