package ca.uqac.etu.jcid.chadal.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Insert
    suspend fun insertShoppingList(shoppingList: ShoppingList)

    @Query("SELECT * FROM shopping_list ORDER BY id DESC")
    fun getAllShoppingLists(): Flow<List<ShoppingList>>
}
