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
import androidx.compose.ui.unit.dp
import ca.uqac.etu.jcid.chadal.data.ShoppingListEntity

/**
 * Composable that represents a card showing some information about a shopping list (such as its
 * name, number of articles, date...)
 */
@Composable
fun CardList(
    course: ShoppingListEntity,
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

            Text(
                text = course.titre,
                style = MaterialTheme.typography.headlineMedium, // Style plus grand pour le titre
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )


            Text(
                text = "Nombre d'articles : ${course.article}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )


            Text(
                text = "Budget : ${course.budget} $",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            // Total
            Text(
                text = "Total : ${course.total} $",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Date : ${course.date}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}






