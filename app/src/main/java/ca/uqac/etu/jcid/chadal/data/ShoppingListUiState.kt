package ca.uqac.etu.jcid.chadal.data

data class ShoppingListUiState (
    val articles: List<Article> = listOf(),
    val total: Float = 0f,
    val budget: Float = 0f
)