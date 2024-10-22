package ca.uqac.etu.jcid.chadal

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ca.uqac.etu.jcid.chadal.ui.ShoppingListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ca.uqac.etu.jcid.chadal.ui.screens.AddItemScreen
import ca.uqac.etu.jcid.chadal.ui.screens.HomeScreen
import ca.uqac.etu.jcid.chadal.ui.screens.ListCompositionScreen
import ca.uqac.etu.jcid.chadal.ui.screens.ListSummaryScreen
import ca.uqac.etu.jcid.chadal.ui.screens.ScanScreen

enum class ChadalScreens {
    Home,
    ListComposition,
    Scan,
    AddItem,
    ListSummary
}

@Composable
fun ChadalApp(
    viewModel: ShoppingListViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) } // Ajout de la barre de navigation
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ChadalScreens.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ChadalScreens.Home.name) {
                HomeScreen(
                    onStartShoppingButtonClicked = {
                        navController.navigate(ChadalScreens.ListComposition.name)
                    },
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                )
            }
            composable(route = ChadalScreens.ListComposition.name) {
                ListCompositionScreen(
                    onAddItemButtonClicked = {
                        navController.navigate(ChadalScreens.Scan.name)
                    },
                    onFinishShoppingButtonClicked = {
                        navController.navigate(ChadalScreens.ListSummary.name)
                    },
                    modifier = Modifier
                )
            }
            composable(route = ChadalScreens.Scan.name) {
                ScanScreen(
                    onNoBarcodeButtonClicked = {
                        navController.navigate(ChadalScreens.AddItem.name)
                    },
                    onCancelButtonClicked = {
                        navController.popBackStack(ChadalScreens.ListComposition.name, false)
                    },
                    modifier = Modifier
                )
            }
            composable(route = ChadalScreens.AddItem.name) {
                AddItemScreen(
                    onValidateButtonClicked = {
                        navController.popBackStack(ChadalScreens.ListComposition.name, false)
                    },
                    onCancelButtonClicked = {
                        navController.popBackStack(ChadalScreens.Scan.name, false)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = ChadalScreens.ListSummary.name) {
                ListSummaryScreen(
                    onFinishButtonClicked = {
                        navController.popBackStack(ChadalScreens.Home.name, false)
                    },
                    onCancelButtonClicked = {
                        navController.popBackStack(ChadalScreens.ListComposition.name, false)
                    },
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomAppBar {
        IconButton(onClick = { navController.navigate(ChadalScreens.Home.name) }) {
            Icon(Icons.Filled.Home, contentDescription = "Home")
        }
        Spacer(modifier = Modifier.weight(1f, true))
        IconButton(onClick = { navController.navigate(ChadalScreens.ListComposition.name) }) {
            Icon(Icons.Filled.ShoppingCart, contentDescription = "List Composition")
        }
        Spacer(modifier = Modifier.weight(1f, true))
        IconButton(onClick = { navController.navigate(ChadalScreens.ListSummary.name) }) {
            Icon(Icons.Filled.List, contentDescription = "List Summary")
        }
    }
}