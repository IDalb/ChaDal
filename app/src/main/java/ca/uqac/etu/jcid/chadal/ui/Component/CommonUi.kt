package ca.uqac.etu.jcid.chadal.ui.Component

import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ca.uqac.etu.jcid.chadal.ChadalApp
import ca.uqac.etu.jcid.chadal.R
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme

/**
 * Composable that displays formatted [price] that will be formatted and displayed on screen
 */
@Composable
fun CardList(modifier: Modifier = Modifier) {
OutlinedCard (modifier = modifier) {
    Text(
        text = stringResource(R.string.title_list_old),
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall
    )
}
}

@Preview
@Composable
fun CardListPreview() {
    ChaDalTheme() {

            ChadalApp()

    }
}
