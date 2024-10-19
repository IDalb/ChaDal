package ca.uqac.etu.jcid.chadal.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListCompositionScreen(
    onAddItemButtonClicked: () -> Unit = {},
    onFinishShoppingButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Text("Composition de la liste de courses")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = onFinishShoppingButtonClicked
        ) {
            Text("Finir les courses")
        }
        Button(
            modifier = Modifier.weight(1f),
            //enabled = selectedValue.isNotEmpty(),
            onClick = onAddItemButtonClicked
        ) {
            Text("Ajouter un article")
        }
    }
}