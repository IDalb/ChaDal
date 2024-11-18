package ca.uqac.etu.jcid.chadal.data

import android.graphics.drawable.Drawable

data class Article(
    val name: String = "",
    val category: ArticleCategory,
    val price: Double,
    val imageResource: Drawable?
)
