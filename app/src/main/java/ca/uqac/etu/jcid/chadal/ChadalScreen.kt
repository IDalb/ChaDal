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
import ca.uqac.etu.jcid.chadal.ui.screens.AllArticleScreen
import ca.uqac.etu.jcid.chadal.ui.screens.ArticleCompositionScreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * The main class of our app.
 * It contains the navigation to the different screens as well as navigation logic.
 */

// The different screens of our app
enum class ChadalScreens {
    Home,
    ListComposition,
    Scan,
    AddItem,
    ListSummary,
    OldList,
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
            // Home screen
            composable(route = ChadalScreens.Home.name) {
                var noBudgetOpenDialog by remember { mutableStateOf(false) }
                var budget = 0.0

                // Method used when creating a shopping list from the home screen
                fun startShopping() {
                    // If no budget is set (null), we deactivate the budget progress bar and
                    // display it as 'unlimited'
                    val budgetRemember = budget ?: 0.0
                    viewModel.resetShoppingList()

                    viewModel.setBudget(budgetRemember)
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            uiState.budget?.let { budget ->
                                val listId = dataStoreManager.saveShoppingListToDatabase(
                                    budget = budget,
                                    date = date,
                                    article = uiState.articles.size,
                                    total = uiState.total,
                                    titre = uiState.name
                                )

                                withContext(Dispatchers.Main) {
                                    viewModel.setCurrentShoppingListId(listId)
                                }
                            }
                        }

                        navController.navigate(ChadalScreens.ListComposition.name)

                    }

                }

                // Dialog that pops up to warn the users that he hasn't entered a budget.
                // It asks for its confirmation
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

            // List summary screen (at the end of composition, just before returning to home screen)
            composable(route = ChadalScreens.ListSummary.name) {
                ListSummaryScreen(
                    listUiState = uiState,
                    onFinishButtonClicked = { listName ->
                        val total = uiState.total
                        val articleCount = uiState.articles.size
                        val titre = listName.ifEmpty { uiState.name }
                        val shoppingListId = viewModel.getCurrentShoppingListId()
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                dataStoreManager.updateShoppingListInDatabase(
                                    shoppingListId = shoppingListId,
                                    budget = uiState.budget ?: 0.0,
                                    articleCount = articleCount,
                                    total = total,
                                    titre = titre
                                )
                            }
                        }

                        navController.popBackStack(ChadalScreens.Home.name, false)
                    },
                    onCancelButtonClicked = {
                        navController.popBackStack(ChadalScreens.ListComposition.name, false)
                    }
                )
            }


            // Old lists screen (shows the previous lists thanks to the list database)
            composable(route = ChadalScreens.OldList.name) {
                OldListScreen(
                    navController = navController,
                    dataStoreManager = dataStoreManager,
                    shoppingListDao = shoppingListDao
                )
            }

            // List composition screen (from which the user adds or removes items to a list)
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

            // Scan screen (on which the user can use his camera to scan a barcode)
            composable(route = ChadalScreens.Scan.name) {
                var manualEntryOpenDialog by remember { mutableStateOf(false) }

                // Dialog that pops up if the user chooses to enter the barcode manually (if the
                // code is unreadable for instance)
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

            // Item addition screen (on which the user enters all data related to a new article;
            // this is the script that would be skipped if the scanned article is already in the
            // database, however, we couldn't add this feature in time.)
            composable(route = ChadalScreens.AddItem.name) {
                val shoppingListId = viewModel.getCurrentShoppingListId()
                AddItemScreen(
                    listUiState = uiState,
                    currentShoppingListId = shoppingListId,
                    articleDao = articleDao,
                    onValidateButtonClicked = { article ->
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                val newArticle = article.copy(shoppingListId = shoppingListId)
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

            // All items screen, on which all saved items are displayed. This screen is accessible
            // from the home screen
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

            // Screen similar to the list composition screen, used when the user wants to consult
            // an old list (from the "Old lists" screen). It shows the articles and their prices
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

/**
 * A component that represents a dialog that show on the screen
 * It can contains a title, an icon, a text, a confirm button and a dismiss button
 */
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

/**
 * A component that represents a dialog that has a text field
 * It is similar to a regular dialog in terms of features, but it returns a value
 */
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