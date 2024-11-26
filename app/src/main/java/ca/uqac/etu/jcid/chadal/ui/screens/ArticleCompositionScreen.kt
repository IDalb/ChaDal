package ca.uqac.etu.jcid.chadal.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.uqac.etu.jcid.chadal.R
import ca.uqac.etu.jcid.chadal.data.Article
import ca.uqac.etu.jcid.chadal.data.ArticleDao
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ArticleCompositionScreen(
    currentShoppingListId: Int,
    articleDao: ArticleDao,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {

    val articles by articleDao.getArticlesByShoppingListId(currentShoppingListId).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Articles de la liste") },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            modifier = modifier.padding(innerPadding),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(articles) { article ->
                ArticleCard2(
                    article = article,
                    modifier = modifier.animateItemPlacement()
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArticleCard2(
    modifier: Modifier = Modifier,
    article: Article,

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
            Column(modifier = modifier) {
                if (article.imageResource.isNullOrEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().aspectRatio(7f / 4f)
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
                Column(modifier = modifier.padding(8.dp)) {
                    Text(
                        text = article.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(article.categoryName),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "%.2f CAD".format(article.price),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp)
                    )
                }
            }
        }

        // Context menu
        DropdownMenu(
            expanded = contextualMenuExpanded,
            onDismissRequest = { contextualMenuExpanded = false }
        ) {
            DropdownMenuItem(
                leadingIcon = { Icon(painterResource(R.drawable.delete), null) },
                text = { Text("Delete article") },
                onClick = {
                    contextualMenuExpanded = false
                }
            )
        }
    }
}


