package ca.uqac.etu.jcid.chadal.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme

@Composable
fun ListCompositionScreen(
    onAddItemButtonClicked: () -> Unit = {},
    onFinishShoppingButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Composition de la liste de courses")
        BottomAppBar(
            actions = {
                OutlinedButton(
                    onClick = onFinishShoppingButtonClicked,
                    modifier = Modifier.padding(16.dp, 0.dp)
                ) {
                    Icon(
                        Icons.Filled.Done,
                        null,
                        modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
                    )
                    Text("Finir les courses")
                }
            },
            floatingActionButton = {
                FloatingActionButton (
                    onClick = { onAddItemButtonClicked() }
                ) {
                    Icon(Icons.Filled.Add, "Add article")
                }
            }
        )
    }
}

@Preview
@Composable
fun ListCompositionScreenPreview() {
    ChaDalTheme {
        ListCompositionScreen(
            modifier = Modifier.fillMaxHeight().padding(16.dp)
        )
    }
}