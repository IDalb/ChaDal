package ca.uqac.etu.jcid.chadal.ui.Component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.uqac.etu.jcid.chadal.ChadalApp
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme

/**
 * Composable that displays formatted [price] that will be formatted and displayed on screen
 */
@Composable
fun CardList(

    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Titre en haut
            Text(
                text = "placeholder",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Prix en dessous du titre
            Text(
                text = "price",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f)) // Espace flexible pour pousser les éléments en bas

            // Ligne pour le nombre d'articles et la date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Nombre d'articles en bas à gauche
                Text(
                    text = "Articles",
                    style = MaterialTheme.typography.bodySmall
                )

                // Date en bas à droite
                Text(
                    text = "date",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview
@Composable
fun CardListPreview() {
    ChaDalTheme {
        CardList( modifier = Modifier)
    }

    }



