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
    ArticleCategory(R.string.placeholder_category, 0f),
    ArticleCategory(R.string.placeholder_category, .05f)
)