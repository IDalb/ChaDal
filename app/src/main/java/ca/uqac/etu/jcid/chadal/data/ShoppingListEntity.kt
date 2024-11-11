package ca.uqac.etu.jcid.chadal.data

// ShoppingListEntity.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class ShoppingListEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val budget: Int,
    val date: String
)
