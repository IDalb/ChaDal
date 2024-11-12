package ca.uqac.etu.jcid.chadal.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ca.uqac.etu.jcid.chadal.data.DataStoreManager
import ca.uqac.etu.jcid.chadal.data.ShoppingListDao
import ca.uqac.etu.jcid.chadal.ui.Component.CardList
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OldListScreen(
    navController: NavController,
    shoppingListDao: ShoppingListDao, // Ajout du DAO en paramètre
    modifier: Modifier = Modifier
) {

    val shoppingLists by shoppingListDao.getAllShoppingLists().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Anciennes listes") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(8.dp, 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Liste de courses",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(shoppingLists) { shoppingList ->
                    CardList(
                        course = shoppingList,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}


/*
@Preview
@Composable
fun OldListScreenPreview() {
    ChaDalTheme {
        OldListScreen( navController = rememberNavController()) // NavController est nul pour l'aperçu
    }
}
*/