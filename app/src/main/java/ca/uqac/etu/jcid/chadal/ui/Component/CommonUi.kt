package ca.uqac.etu.jcid.chadal.ui.Component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.uqac.etu.jcid.chadal.ChadalApp
import ca.uqac.etu.jcid.chadal.data.ShoppingListEntity
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme

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
                text = "Course: Nombre d'article ${course.article}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Budget: ${course.budget} $",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .height(60.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Date: ${course.date}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}




