package ca.uqac.etu.jcid.chadal.data

import androidx.annotation.StringRes
import ca.uqac.etu.jcid.chadal.R
import java.util.Locale.Category

/**
 * A data class to represent a category of articles.
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