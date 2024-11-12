package ca.uqac.etu.jcid.chadal.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "shopping_list")

class DataStoreManager(
    private val context: Context,
    private val shoppingListDao: ShoppingListDao
) {

    companion object {
        val BUDGET_KEY = intPreferencesKey("budget_key")
        val DATE_KEY = stringPreferencesKey("date_key")
    }


    val shoppingListFlow: Flow<ShoppingList> = context.dataStore.data
        .map { preferences ->
            val budget = preferences[BUDGET_KEY] ?: 0
            val date = preferences[DATE_KEY] ?: ""
            ShoppingList(budget, date)
        }


    suspend fun saveShoppingListToDataStore(budget: Int, date: String) {
        context.dataStore.edit { preferences ->
            preferences[BUDGET_KEY] = budget
            preferences[DATE_KEY] = date
        }
    }


    suspend fun saveShoppingListToDatabase(budget: Int, date: String) {
        val shoppingListEntity = ShoppingListEntity(budget = budget, date = date)
        shoppingListDao.insertShoppingList(shoppingListEntity)
    }
}
