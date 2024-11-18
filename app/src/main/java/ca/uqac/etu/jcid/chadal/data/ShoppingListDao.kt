package ca.uqac.etu.jcid.chadal.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ShoppingListDao {
    @Insert
    fun insertShoppingList(shoppingList: ShoppingListEntity) :Long

    @Delete
    fun deleteShoppingListTable(shoppingList: ShoppingListEntity)

    @Query("SELECT * FROM shopping_list ORDER BY id DESC")
    fun getAllShoppingLists(): Flow<List<ShoppingListEntity>>


    @Query("DELETE FROM shopping_list")
    fun deleteAllShoppingLists()
}
