package ca.uqac.etu.jcid.chadal.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ca.uqac.etu.jcid.chadal.ChadalScreens
import ca.uqac.etu.jcid.chadal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onStartShoppingButtonClicked: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val showText = remember { mutableStateOf(false) }
    val budgetText = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_home)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues).padding(0.dp, 12.dp)
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Nouvelle liste",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    TextField(
                        value = budgetText.value,
                        onValueChange = {
                            if (it.all { char -> char.isDigit() }) {
                                budgetText.value = it
                            }
                        },
                        label = { Text("Budget") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = onStartShoppingButtonClicked,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Commencer")
                    }
                }
            }

            if (showText.value) {
                Text(text = "Nouvelle liste créée!", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Dernières courses",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        // Your list items go here
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { /* Your click action */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Afficher plus")
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

    NavigationBar {
        NavigationBarItem(
            selected = selectedTabIndex == 0,
            onClick = { selectedTabIndex = 0 },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Accueil") }
        )
        NavigationBarItem(
            selected = selectedTabIndex == 1,
            onClick = { selectedTabIndex = 1 },
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Previous lists") },
            label = { Text("Anciennes listes") }
        )
        NavigationBarItem(
            selected = selectedTabIndex == 2,
            onClick = { selectedTabIndex = 2 },
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Saved products") },
            label = { Text("Produits") }
        )
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    ChaDalTheme {
        HomeScreen(
            onStartShoppingButtonClicked = {},
            navController = rememberNavController(),
            modifier = Modifier.fillMaxSize().padding(16.dp)
        )
    }
}