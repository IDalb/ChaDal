package ca.uqac.etu.jcid.chadal.data

data class BarcodeValue(
    var rawValue: String,
    var displayValue: String
)

data class ShoppingListUiState (
    val articles: List<Article> = listOf(),
    var total: Double = 0.0,
    var budget: Double? = null,

    var name:String = "",

    // Temporary scan data
    var lastScanValue: BarcodeValue = BarcodeValue("", "")
)