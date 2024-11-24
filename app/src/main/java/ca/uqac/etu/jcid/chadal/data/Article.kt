package ca.uqac.etu.jcid.chadal.data

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_table")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val categoryName: Int, // Modifié pour accepter un ID de ressource
    val taxPercentage: Float,
    val price: Double,
    val imageResource: String? = null
)

