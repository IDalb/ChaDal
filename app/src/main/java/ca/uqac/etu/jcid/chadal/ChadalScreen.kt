package ca.uqac.etu.jcid.chadal

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.uqac.etu.jcid.chadal.data.AppDatabase
import ca.uqac.etu.jcid.chadal.data.BarcodeValue
import ca.uqac.etu.jcid.chadal.data.DataStoreManager
import ca.uqac.etu.jcid.chadal.ui.ShoppingListViewModel
import ca.uqac.etu.jcid.chadal.ui.screens.AddItemScreen
import ca.uqac.etu.jcid.chadal.ui.screens.HomeScreen
import ca.uqac.etu.jcid.chadal.ui.screens.ListCompositionScreen
import ca.uqac.etu.jcid.chadal.ui.screens.ListSummaryScreen
import ca.uqac.etu.jcid.chadal.ui.screens.OldListScreen
import ca.uqac.etu.jcid.chadal.ui.screens.ScanScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.runtime.rememberCoroutineScope
import ca.uqac.etu.jcid.chadal.data.ShoppingListEntity
import ca.uqac.etu.jcid.chadal.ui.screens.AllArticleScreen
import ca.uqac.etu.jcid.chadal.ui.screens.ArticleCompositionScreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class ChadalScreens {
    Home,
    ListComposition,
    Scan,
    AddItem,
    ListSummary,
    OldList,
    ArticleComposition,
    AllArticle
}
@Composable
fun ChadalApp(
    viewModel: ShoppingListViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val shoppingListDao = remember { AppDatabase.getDatabase(context).shoppingListDao() }
    val articleDao = remember { AppDatabase.getDatabase(context).articleDao() }
    val dataStoreManager = remember { DataStoreManager(context, shoppingListDao, articleDao) }
    val coroutineScope = rememberCoroutineScope()
    val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

    var currentShoppingListId by remember { mutableStateOf<Long?>(null) }

    Scaffold() { it
        NavHost(
            navController = navController,
            startDestination = ChadalScreens.Home.name
        ) {
            composable(route = ChadalScreens.Home.name) {
                var noBudgetOpenDialog by remember { mutableStateOf(false) }
                var budget = 0.0

                fun startShopping() {
                    val budgetRemember = if (budget == 0.0) null else budget
                    viewModel.resetShoppingList()

                    if (budgetRemember != null) {
                        viewModel.setBudget(budgetRemember)
                    }
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            uiState.budget?.let { budget ->
                                val listId = dataStoreManager.saveShoppingListToDatabase(
                                    budget = budget,
                                    date = date,
                                    article = uiState.articles.size,
                                    total = uiState.total
                                )

                                withContext(Dispatchers.Main) {
                                    viewModel.setCurrentShoppingListId(listId)
                                }
                            }
                        }

                        navController.navigate(ChadalScreens.ListComposition.name)

                    }

                }

                Dialog(
                    opened = noBudgetOpenDialog,
                    onDismissRequest = { noBudgetOpenDialog = false },
                    onConfirmation = { noBudgetOpenDialog = false; startShopping() },
                    dialogTitle = stringResource(R.string.no_budget_dialog_title),
                    dialogText = stringResource(R.string.no_budget_dialog_message)
                )

                HomeScreen(
                    onStartShoppingButtonClicked = { b ->
                        budget = b
                        if (budget == 0.0)
                            noBudgetOpenDialog = true
                        else
                            startShopping()

                    },
                    navController = navController,
                    dataStoreManager = dataStoreManager,
                    shoppingListDao = shoppingListDao,
                )
            }

            composable(route = ChadalScreens.ListSummary.name) {
                ListSummaryScreen(
                    listUiState = uiState,
                    onFinishButtonClicked = {
                        val total = uiState.total
                        val articleCount = uiState.articles.size
                        val shoppingListId = viewModel.getCurrentShoppingListId()
                        if (shoppingListId != null) {
                            coroutineScope.launch {
                                withContext(Dispatchers.IO) {
                                    dataStoreManager.updateShoppingListInDatabase(
                                        shoppingListId = shoppingListId,
                                        budget = uiState.budget ?: 0.0,
                                        articleCount = articleCount,
                                        total = total
                                    )
                                }
                            }
                        }

                        navController.popBackStack(ChadalScreens.Home.name, false)
                    },
                    onCancelButtonClicked = {
                        navController.popBackStack(ChadalScreens.ListComposition.name, false)
                    }
                )
            }



            composable(route = ChadalScreens.OldList.name) {
                OldListScreen(
                    navController = navController,
                    dataStoreManager = dataStoreManager,
                    shoppingListDao = shoppingListDao
                )
            }
            composable(route = ChadalScreens.ListComposition.name) {
                ListCompositionScreen(
                    listUiState = uiState,
                    onAddItemButtonClicked = { navController.navigate(ChadalScreens.Scan.name) },
                    onRemoveItemButtonClicked = { article ->
                        viewModel.removeArticle(article)
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                articleDao.deleteArticle(article)
                            }
                        }
                    },
                    onFinishShoppingButtonClicked = {
                        navController.navigate(ChadalScreens.ListSummary.name)
                    }
                )
            }
            composable(route = ChadalScreens.Scan.name) {
                var manualEntryOpenDialog by remember { mutableStateOf(false) }
                FieldDialog(
                    opened = manualEntryOpenDialog,
                    onDismissRequest = { manualEntryOpenDialog = false },
                    onConfirmation = { value ->
                        manualEntryOpenDialog = false
                        viewModel.uiState.value.lastScanValue = BarcodeValue(value, value)
                        navController.navigate(ChadalScreens.AddItem.name)
                    },
                    dialogTitle = stringResource(R.string.enter_code_manually),
                )

                ScanScreen(
                    onBarcodeScanned = { barcodeValue ->
                        viewModel.uiState.value.lastScanValue = barcodeValue
                        navController.navigate(ChadalScreens.AddItem.name)
                    },
                    onNoBarcodeButtonClicked = {
                        viewModel.uiState.value.lastScanValue = BarcodeValue("", "")
                        navController.navigate(ChadalScreens.AddItem.name)
                    },
                    onManualEntryButtonClicked = { manualEntryOpenDialog = true },
                    onCancelButtonClicked = {
                        navController.popBackStack(ChadalScreens.ListComposition.name, false)
                    },
                )
            }
            composable(route = ChadalScreens.AddItem.name) {
                val currentShoppingListId = viewModel.getCurrentShoppingListId()
                AddItemScreen(
                    listUiState = uiState,
                    currentShoppingListId = currentShoppingListId,
                    articleDao = articleDao,
                    onValidateButtonClicked = { article ->
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                val newArticle = article.copy(shoppingListId = currentShoppingListId)
                                articleDao.insertArticle(newArticle)

                            }
                            viewModel.addArticle(article)
                        }
                        navController.popBackStack(ChadalScreens.ListComposition.name, false)
                    },
                    onCancelButtonClicked = {
                        navController.popBackStack(ChadalScreens.Scan.name, false)
                    }
                )
            }
            composable(route = ChadalScreens.AllArticle.name) {
                AllArticleScreen(
                    navController = navController,
                    articleDao = articleDao,
                    onRemoveArticleButtonClicked = { article ->
                        viewModel.removeArticle(article)
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {

                                articleDao.deleteArticle(article)
                            }
                        }
                    }
                )

            }


            composable("ArticleCompositionScreen/{shoppingListId}") { backStackEntry ->
                val shoppingListId = backStackEntry.arguments?.getString("shoppingListId")?.toInt() ?: 0
                val articles = articleDao.getArticlesByShoppingListId(shoppingListId)
                    .collectAsState(initial = emptyList()).value
                ArticleCompositionScreen(
                    currentShoppingListId = shoppingListId,
                    articleDao = articleDao,
                    onBackClicked = { navController.popBackStack() }
                )
                println("Nombre d'articles : ${articles.size}")
                articles.forEach { article ->
                    println("Article : ${article.name}, Prix : ${article.price}, Catégorie : ${article.categoryName}")
                    }
            }
        }
    }
}

@Composable
fun Dialog(
    opened: Boolean? = null,
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
    dialogTitle: String = "",
    dialogText: String = "",
    icon: ImageVector? = null
    ) {

    var openDialog by remember { mutableStateOf(true) }
    if (opened != null) openDialog = opened

    when {
        openDialog -> {
            AlertDialog(
                onDismissRequest = { openDialog = false; onDismissRequest() },
                confirmButton = {
                    TextButton(onClick = { openDialog = false; onConfirmation() }) {
                        Text(stringResource(R.string.next))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { openDialog = false; onDismissRequest() }) {
                        Text(stringResource(R.string.cancel))
                    }
                },
                title = { Text(dialogTitle) },
                text = { Text(dialogText) },
                icon = { if (icon != null) Icon(icon, contentDescription = null) }
            )
        }
    }
}

@Composable
fun FieldDialog(
    opened: Boolean? = null,
    onDismissRequest: () -> Unit = {},
    onConfirmation: (String) -> Unit = {},
    dialogTitle: String = "",
    icon: ImageVector? = null
) {

    var openDialog by remember { mutableStateOf(true) }
    if (opened != null) openDialog = opened

    var valueInput by remember { mutableStateOf("") }

    when {
        openDialog -> {
            AlertDialog(
                onDismissRequest = { openDialog = false; onDismissRequest() },
                confirmButton = {
                    TextButton(onClick = { openDialog = false; onConfirmation(valueInput) }) {
                        Text(stringResource(R.string.next))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { openDialog = false; onDismissRequest() }) {
                        Text(stringResource(R.string.cancel))
                    }
                },
                title = { Text(dialogTitle) },
                text = {
                    OutlinedTextField(
                        value = valueInput,
                        onValueChange = { valueInput = it },
                        label = { Text(stringResource(R.string.barcode)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    ) },
                icon = { if (icon != null) Icon(icon, contentDescription = null) }
            )
        }
    }
}