package ca.uqac.etu.jcid.chadal.data

data class BarcodeValue(
    var rawValue: String,
    var displayValue: String
)

data class ShoppingListUiState (
    val articles: List<Article> = listOf(),
    var total: Double = calculateTotal(articles),
    var budget: Double? = null,

    var name:String = "",

    // Temporary scan data
    var lastScanValue: BarcodeValue = BarcodeValue("", "")

)

fun calculateTotal(articles: List<Article>): Double {
    var total:Double = 0.0
    articles.forEach {
        // Tax calculation & application
        val price = it.price * (1 + it.taxPercentage)
        total += price
    }
    return total
}

fun deleteArticle(articles: List<Article>){

}
