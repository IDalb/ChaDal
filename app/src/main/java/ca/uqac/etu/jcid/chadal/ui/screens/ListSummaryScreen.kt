package ca.uqac.etu.jcid.chadal.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.uqac.etu.jcid.chadal.R
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListSummaryScreen(
    modifier: Modifier = Modifier,
    onCancelButtonClicked: () -> Unit = {},
    onFinishButtonClicked: () -> Unit = {}
) {
    var listName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_list_summary)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = modifier
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp, 32.dp),
        ) {
            Text(
                text = "11" + " " + stringResource(R.string.article),
                style = MaterialTheme.typography.bodyLarge,
            )

            HorizontalDivider(modifier = modifier.padding(16.dp, 24.dp))

            Text(
                text = stringResource(R.string.total) + ": " + "00" + stringResource(R.string.currency_cad),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(R.string.budget) + ": " + "000" + stringResource(R.string.currency_cad),
                style = MaterialTheme.typography.titleSmall
            )

            LinearProgressIndicator(
                progress = { 0.70f },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp)
                    .height(16.dp)
                    .border(
                        border = BorderStroke(1.dp, Color.DarkGray)
                    )
            )

            OutlinedTextField(
                value = listName,
                onValueChange = { listName = it },
                label = { Text("Nom de la liste (optionnel)") },
                modifier = modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(0.dp, 48.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = onCancelButtonClicked
                ) {
                    Text(stringResource(R.string.cancel))
                }
                Button(
                    modifier = Modifier.weight(1f),
                    //enabled = selectedValue.isNotEmpty(),
                    onClick = onFinishButtonClicked
                ) {
                    Text(stringResource(R.string.finish))
                }
            }
        }
    }
}

@Preview
@Composable
fun ListSummaryScreenPreview() {
    ChaDalTheme {
        ListSummaryScreen()
    }
}