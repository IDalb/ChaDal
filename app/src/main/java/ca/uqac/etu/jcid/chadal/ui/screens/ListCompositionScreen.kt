package ca.uqac.etu.jcid.chadal.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import ca.uqac.etu.jcid.chadal.R
import ca.uqac.etu.jcid.chadal.data.Article
import ca.uqac.etu.jcid.chadal.data.ArticleCategory
import ca.uqac.etu.jcid.chadal.data.ShoppingListUiState
import ca.uqac.etu.jcid.chadal.data.categories
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.drawablepainter.rememberDrawablePainter


@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListCompositionScreen(
    modifier: Modifier = Modifier,
    currentShoppingListId: Long?, // Ajoutez ce paramètre
    listUiState: ShoppingListUiState,
    onAddItemButtonClicked: () -> Unit = {},
    onRemoveItemButtonClicked: (Article) -> Unit = {},
    onFinishShoppingButtonClicked: () -> Unit = {}

) {
    updateTotal(listUiState)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_list_composition)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = modifier
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onAddItemButtonClicked() },
                icon = { Icon(Icons.Filled.Add, null) },
                text = { Text(stringResource(R.string.add_article)) }
            )
        },
        bottomBar = {
            BottomAppBar {
                Column(modifier = Modifier.fillMaxSize()) {
                    if (listUiState.budget != null && listUiState.budget != 0.0)
                        LinearProgressIndicator(
                            progress = {
                                (listUiState.total / listUiState.budget!!)
                                    .toFloat()
                                    .coerceIn(0f, 1f)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .height(8.dp)
                        )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 8.dp)
                    ) {
                        OutlinedButton(
                            onClick = onFinishShoppingButtonClicked
                        ) {
                            Icon(
                                Icons.Filled.Done,
                                null,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(stringResource(R.string.finish))
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(
                                    R.string.total,
                                    String.format("%.2f", listUiState.total),
                                    stringResource(R.string.currency_cad)
                                ),
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = if (listUiState.budget != null) stringResource(
                                    R.string.budget,
                                    String.format("%.2f", listUiState.budget),
                                    stringResource(R.string.currency_cad)
                                ) else stringResource(
                                    R.string.budget,
                                    stringResource(R.string.unlimited),
                                    ""
                                ),
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            modifier = modifier
                .padding(innerPadding)
                .clipToBounds(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listUiState.articles) {
                ArticleCard(
                    article = it,
                    modifier = modifier.animateItemPlacement(),
                    onRemoveItemButtonClicked = onRemoveItemButtonClicked
                )
            }
        }
    }
}
fun updateTotal(listUiState: ShoppingListUiState) {
    val total = listUiState.articles.sumOf {
        val price = it.price * (1 + it.taxPercentage)
        price
    }
    listUiState.total = total
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onRemoveItemButtonClicked: (Article) -> Unit
) {
    val haptics = LocalHapticFeedback.current
    var contextualMenuExpanded by remember { mutableStateOf(false) }

    Box {
        OutlinedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = modifier.combinedClickable(
                onClick = {},
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    contextualMenuExpanded = true
                }
            )
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                // Affichage de l'image
                if (article.imageResource.isNullOrEmpty()) {
                    // Image par défaut si aucune ressource n'est disponible
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(7f / 4f)
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(model = article.imageResource),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(7f / 4f)
                    )
                }

                // Nom de l'article
                Text(
                    text = article.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                // Nom de la catégorie
                Text(
                    text = article.categoryName.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "%.2f CAD".format(
                        article.price * (1 + article.taxPercentage / 100) // Correction : taxPercentage est en %
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Menu contextuel
        DropdownMenu(
            expanded = contextualMenuExpanded,
            onDismissRequest = { contextualMenuExpanded = false }
        ) {
            DropdownMenuItem(
                leadingIcon = { Icon(painterResource(R.drawable.delete), null) },
                text = { Text(stringResource(R.string.delete_article)) },
                onClick = {
                    contextualMenuExpanded = false
                    onRemoveItemButtonClicked(article)
                }
            )
        }
    }
}



/*
@Preview
@Composable
fun ListCompositionScreenPreview() {
    ChaDalTheme {
        ListCompositionScreen(
            listUiState = ShoppingListUiState(
                List(5) {
                    Article(
                        stringResource(R.string.placeholder_article_name),
                        ArticleCategory(R.string.placeholder_category, 0f),
                        0.0,
                        ContextCompat.getDrawable(
                            LocalContext.current,
                            R.drawable.ic_launcher_foreground)
                    )
                },
                budget = 10.0
            )
        )
    }
}
*/