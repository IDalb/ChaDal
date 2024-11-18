package ca.uqac.etu.jcid.chadal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class ShoppingListEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val budget: Double,
    val date: String,
    val article: Int

)