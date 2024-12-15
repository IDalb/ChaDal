package ca.uqac.etu.jcid.chadal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A data class that represents a shopping list for the database.
 * It is mainly used in order to show statistics to the user (for instance, this class holds an int
 * for the number of articles in a list, but not the list of articles itself)
 * A shopping list has a title, a budget (that can be unlimited), a date, a number of articles and
 * a total price.
 */

@Entity(tableName = "shopping_list")
data class ShoppingListEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titre: String,
    val budget: Double,
    val date: String,
    val article: Int,
    val total: Double

)