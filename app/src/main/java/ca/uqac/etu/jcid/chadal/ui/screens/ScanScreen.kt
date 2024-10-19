package ca.uqac.etu.jcid.chadal.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScanScreen(
    onCancelButtonClicked: () -> Unit = {},
    onNoBarcodeButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Text("Scan de l'article")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = onCancelButtonClicked
        ) {
            Text("Retour arrière")
        }
        Button(
            modifier = Modifier.weight(1f),
            //enabled = selectedValue.isNotEmpty(),
            onClick = onNoBarcodeButtonClicked
        ) {
            Text("Pas de code-barres")
        }
    }
}