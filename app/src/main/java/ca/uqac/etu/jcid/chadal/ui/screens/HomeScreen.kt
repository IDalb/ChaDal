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

@Composable
fun HomeScreen(
    onStartShoppingButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onStartShoppingButtonClicked,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text("Commencer les courses")
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