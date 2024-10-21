package ca.uqac.etu.jcid.chadal.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.uqac.etu.jcid.chadal.R
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListCompositionScreen(
    modifier: Modifier = Modifier,
    onAddItemButtonClicked: () -> Unit = {},
    onFinishShoppingButtonClicked: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Composition de la liste de courses") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = modifier
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton (
                onClick = { onAddItemButtonClicked() },
                icon = { Icon(Icons.Filled.Add, "Ajouter un article") },
                text = { Text("Ajouter un article") }
            )
        },
        bottomBar = {
            Column {
                LinearProgressIndicator(
                    progress = { 0.70f },
                    modifier = Modifier.fillMaxWidth().height(8.dp)
                )
                Row (
                    modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp)
                ) {
                    OutlinedButton(
                        onClick = onFinishShoppingButtonClicked
                    ) {
                        Icon(
                            Icons.Filled.Done,
                            null,
                            modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
                        )
                        Text("Terminer")
                    }
                    Column (
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Total: 00$",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Budget: 888$",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyVerticalGrid (
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(innerPadding)
        ) {
            items(11) {
                ArticleCard(modifier = modifier)
            }
        }
    }
}

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
    ) {
        Column(modifier = modifier) {
            Image(
                painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().aspectRatio(7f/4f)
            )
            Column(modifier = modifier.padding(8.dp)) {
                Text("Nom article", style = MaterialTheme.typography.titleMedium)
                Text("Catégorie", style = MaterialTheme.typography.labelMedium)
                Text("Prix", style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ListCompositionScreenPreview() {
    ChaDalTheme {
        ListCompositionScreen()
    }
}