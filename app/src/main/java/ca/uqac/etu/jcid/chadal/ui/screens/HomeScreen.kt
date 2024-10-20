package ca.uqac.etu.jcid.chadal.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme
@Composable
fun HomeScreen(

    onStartShoppingButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val showText = remember { mutableStateOf(false) }
    val budgetText = remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Accueil",
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(50.dp))

        // Box with title and input
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
                // Large title "Nouvelle liste" at the top of the rectangle
                Text(
                    text = "Nouvelle liste",
                    style = MaterialTheme.typography.headlineLarge, // Bigger title
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Input field for budget at the bottom
                TextField(
                    value = budgetText.value,
                    onValueChange = {
                        // Only allow digits (0-9)
                        if (it.all { char -> char.isDigit() }) {
                            budgetText.value = it
                        }
                    },
                    label = { Text("Budget") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Button to create a new list
                Button( onClick = onStartShoppingButtonClicked,) {
                    Text(text = "Nouvelle liste")
                }
            }
        }

        // Conditionally show the text "Nouvelle liste créée!" when button is clicked
        if (showText.value) {
            Text(text = "Nouvelle liste créée!", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Card containing the dropdown list of other Cards
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
                // Title of the list
                Text(
                    text = "Liste déroulante",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // LazyColumn for the list of items
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Adjust the height for scrollable area
                ) {

                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Button to show more items
            Button(onClick = {
                // Add 3 more items to the list

            }) {
                Text(text = "Afficher plus",
                    modifier = Modifier
                        .fillMaxWidth(),




                    )

            }
        }
    }

}

@Composable
fun BottomNavigationBar() {
    BottomAppBar {
        IconButton(onClick = { /* TODO: Home action */ }) {
            Icon(Icons.Filled.Home, contentDescription = "Home")
        }
        Spacer(modifier = Modifier.weight(1f, true))
        IconButton(onClick = { /* TODO: Favorite action */ }) {
            Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
        }
        Spacer(modifier = Modifier.weight(1f, true))
        IconButton(onClick = { /* TODO: Settings action */ }) {
            Icon(Icons.Filled.Settings, contentDescription = "Settings")
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