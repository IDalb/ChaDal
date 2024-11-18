package ca.uqac.etu.jcid.chadal.data

data class ShoppingListUiState (
    val articles: List<Article> = listOf(),
    var total: Double = 0.0,
    var budget: Double? = null,

    var name:String = ""
)