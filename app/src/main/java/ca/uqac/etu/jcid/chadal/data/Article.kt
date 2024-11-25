package ca.uqac.etu.jcid.chadal.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "article_table",
    foreignKeys = [
        ForeignKey(
            entity = ShoppingListEntity::class,
            parentColumns = ["id"],
            childColumns = ["shoppingListId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("shoppingListId")]
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val shoppingListId: Long, // Clé étrangère pour lier à ShoppingListEntity
    val name: String,
    val categoryName: Int, // Int pour référencer un ArticleCategory
    val taxPercentage: Float,
    val price: Double,
    val imageResource: String? = null // URI ou chemin vers l'image
)
