package ca.uqac.etu.jcid.chadal.data

import androidx.annotation.DrawableRes

data class Article(
    val name: String = "",
    val category: ArticleCategory,
    val price: Float,
    @DrawableRes val imageResourceId: Int
)
