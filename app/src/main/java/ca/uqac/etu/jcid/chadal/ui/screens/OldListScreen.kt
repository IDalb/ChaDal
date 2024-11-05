package ca.uqac.etu.jcid.chadal.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ca.uqac.etu.jcid.chadal.R
import ca.uqac.etu.jcid.chadal.ui.Component.CardList
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OldListScreen(
    navController: NavController? = null, // NavController est maintenant optionnel
    modifier: Modifier = Modifier
) {
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
        bottomBar = {
            if (navController != null) {
                BottomNavigationBar(
                    navController = navController,
                    onOldListButtonClicked = {
                        navController?.navigate("previous_lists")
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(16.dp, 32.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Liste de course",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(1) {
                    CardList(
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OldListScreenPreview() {
    ChaDalTheme {
        OldListScreen(navController = null) // NavController est nul pour l'aperçu
    }
}
