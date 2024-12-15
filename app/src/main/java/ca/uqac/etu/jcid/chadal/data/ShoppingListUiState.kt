package ca.uqac.etu.jcid.chadal.data

/**
 * A data class that represents a value received when scanning a barcode. It helps containing both
 * the raw (unformatted) and display (formatted) value of the barcode.
 * A category has its own tax percentage (i.e. 0.05 = 5% tax)
 */
data class BarcodeValue(
    var rawValue: String,
    var displayValue: String
)


/**
 * A data class that represents the uiState of a shopping list.
 * It is mainly used in the 'List Composition' screen.
 */
data class ShoppingListUiState (
    val articles: List<Article> = listOf(),
    var total: Double = calculateTotal(articles),
    var budget: Double? = null,

    var name:String = "",

    var lastScanValue: BarcodeValue = BarcodeValue("", "")
)

fun calculateTotal(articles: List<Article>): Double {
    var total = 0.0
    articles.forEach {
        // Tax calculation & application
        val price = it.price * (1 + it.taxPercentage)
        total += price
    }
    return total
}

