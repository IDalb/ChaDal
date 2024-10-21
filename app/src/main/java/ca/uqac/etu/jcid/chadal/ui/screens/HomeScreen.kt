package ca.uqac.etu.jcid.chadal.ui.screens

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
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onStartShoppingButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val showText = remember { mutableStateOf(false) }
    val budgetText = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Accueil") },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(50.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Nouvelle liste",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

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

                    Button(onClick = onStartShoppingButtonClicked) {
                        Text(text = "Nouvelle liste")
                    }
                }
            }

            if (showText.value) {
                Text(text = "Nouvelle liste créée!", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Liste déroulante",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        // Your list items go here
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = { /* Your click action */ }) {
                    Text(
                        text = "Afficher plus",
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    ChaDalTheme {
        HomeScreen(
            onStartShoppingButtonClicked = {},
            modifier = Modifier.fillMaxSize().padding(16.dp)
        )
    }
}