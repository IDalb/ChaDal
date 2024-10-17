package ca.uqac.etu.jcid.chadal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChaDalTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigationBar() }
                ) { innerPadding ->
                    HomeScreenLayout(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HomeScreenLayout(modifier: Modifier = Modifier) {
    val showText = remember { mutableStateOf(false) }

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
        // Title of the Home Screen
        Text(
            text = "Accueil",
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start),
            style = MaterialTheme.typography.titleLarge
        )
        // Displaying the budget
        Text(
            text = "Budget: $0.00",
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(50.dp))

        // Button to display "Nouvelle liste"
        Button(onClick = { showText.value = true }) {
            Text(text = "Nouvelle liste")
        }

        // Conditionally show the text "Nouvelle liste" when button is clicked
        if (showText.value) {
            Text(text = "Nouvelle liste", style = MaterialTheme.typography.bodyLarge)
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ChaDalTheme {
        HomeScreenLayout()
    }
}
