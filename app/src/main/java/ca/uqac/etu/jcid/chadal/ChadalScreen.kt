package ca.uqac.etu.jcid.chadal

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
                HomeScreen(
                    onStartShoppingButtonClicked = {
                        navController.navigate(ChadalScreens.ListComposition.name)
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
                    onAddItemButtonClicked = {
                        navController.navigate(ChadalScreens.Scan.name)
                    },
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
                    onValidateButtonClicked = {
                        navController.popBackStack(ChadalScreens.ListComposition.name, false)
                    },
                    onCancelButtonClicked = {
                        navController.popBackStack(ChadalScreens.Scan.name, false)
                    }
                )
            }
            composable(route = ChadalScreens.ListSummary.name) {
                ListSummaryScreen(
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

