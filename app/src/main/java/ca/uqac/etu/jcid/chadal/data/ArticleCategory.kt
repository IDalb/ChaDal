package ca.uqac.etu.jcid.chadal.data

import androidx.annotation.StringRes
import ca.uqac.etu.jcid.chadal.R

/**
 * A data class that represents a category of articles.
 * A category has its own tax percentage (i.e. 0.05 = 5% tax)
 */
data class ArticleCategory(
    @StringRes val name: Int,
    val taxPercentage: Float
)

val categories = listOf(
    ArticleCategory(R.string.category_meat_fish_eggs, 0f),
    ArticleCategory(R.string.category_fruits, 0f),
    ArticleCategory(R.string.category_cereals, 0f),
    ArticleCategory(R.string.category_produce, 0f),
    ArticleCategory(R.string.category_baby, 0f),
    ArticleCategory(R.string.category_drinks_no_tax, 0f),
    ArticleCategory(R.string.category_meals, .14975f),
    ArticleCategory(R.string.category_candy, .14975f),
    ArticleCategory(R.string.category_drinks_tax, .14975f),
    ArticleCategory(R.string.category_alcohol, .14975f)
)

fun getImageResourceForCategory(categoryName: String): String {
    return when (categoryName) {
        "category_meat_fish_eggs" -> "viande_poisson_oeuf.png"
        "fruits" -> "fruits.png"
        "cereals" -> "cereals.png"
        "produce" -> "produce.png"
        "baby" -> "baby.png"
        "drinks_no_tax" -> "drinks_no_tax.png"
        "meals" -> "meals.png"
        "candy" -> "candy.png"
        "drinks_tax" -> "drinks_tax.png"
        "alcohol" -> "alcohol.png"
        else -> "viande_poisson_oeuf.png" // Default image if the category doesn't match
    }
}
