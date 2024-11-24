package ca.uqac.etu.jcid.chadal.data

import androidx.room.Embedded
import androidx.room.Relation

data class ShoppingListWithArticles(
    @Embedded val shoppingList: ShoppingListEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "shoppingListId"
    )
    val articles: List<Article>
)
