package ca.uqac.etu.jcid.chadal

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.uqac.etu.jcid.chadal.data.AppDatabase
import ca.uqac.etu.jcid.chadal.data.DataStoreManager
import ca.uqac.etu.jcid.chadal.ui.ShoppingListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import ca.uqac.etu.jcid.chadal.ui.screens.AddItemScreen
import ca.uqac.etu.jcid.chadal.ui.screens.HomeScreen
import ca.uqac.etu.jcid.chadal.ui.screens.ListCompositionScreen
import ca.uqac.etu.jcid.chadal.ui.screens.ListSummaryScreen
import ca.uqac.etu.jcid.chadal.ui.screens.OldListScreen
import ca.uqac.etu.jcid.chadal.ui.screens.ScanScreen

enum class ChadalScreens {
    Home,
    ListComposition,
    Scan,
    AddItem,
    ListSummary,
    OldList,
}

@Composable
fun ChadalApp(
    viewModel: ShoppingListViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val shoppingListDao = remember { AppDatabase.getDatabase(context).shoppingListDao() }
    val dataStoreManager = remember { DataStoreManager(context, shoppingListDao) }
    Scaffold () { it
        NavHost(
            navController = navController,
            startDestination = ChadalScreens.Home.name
        ) {
            composable(route = ChadalScreens.Home.name) {
                var noBudgetOpenDialog by remember { mutableStateOf(false) }
                var budget = 0.0

                fun startShopping() {
                    viewModel.resetShoppingList()
                    if (budget != 0.0) uiState.budget = budget
                    navController.navigate(ChadalScreens.ListComposition.name)
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
            composable(route = ChadalScreens.OldList.name) {
                OldListScreen(    navController = navController,
                    shoppingListDao = shoppingListDao)
            }
            composable(route = ChadalScreens.ListComposition.name) {
                ListCompositionScreen(
                    listUiState = uiState,
                    onAddItemButtonClicked = { navController.navigate(ChadalScreens.Scan.name) },
                    onRemoveItemButtonClicked = { article -> viewModel.removeArticle(article) },
                    onFinishShoppingButtonClicked = {
                        navController.navigate(ChadalScreens.ListSummary.name)
                    }
                )
            }
            composable(route = ChadalScreens.Scan.name) {
                ScanScreen(
                    onNoBarcodeButtonClicked = {
                        navController.navigate(ChadalScreens.AddItem.name)
                    },
                    onCancelButtonClicked = {
                        navController.popBackStack(ChadalScreens.ListComposition.name, false)
                    }
                )
            }
            composable(route = ChadalScreens.AddItem.name) {
                AddItemScreen(
                    onValidateButtonClicked = { article ->
                        viewModel.addArticle(article)
                        navController.popBackStack(ChadalScreens.ListComposition.name, false)
                    },
                    onCancelButtonClicked = {
                        navController.popBackStack(ChadalScreens.Scan.name, false)
                    }
                )
            }
            composable(route = ChadalScreens.ListSummary.name) {
                ListSummaryScreen(
                    shoppingList = viewModel.uiState.value,
                    onFinishButtonClicked = {
                        navController.popBackStack(ChadalScreens.Home.name, false)
                    },
                    onCancelButtonClicked = {
                        navController.popBackStack(ChadalScreens.ListComposition.name, false)
                    }
                )
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