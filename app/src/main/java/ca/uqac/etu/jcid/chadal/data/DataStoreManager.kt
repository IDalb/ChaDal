package ca.uqac.etu.jcid.chadal.data

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey


class DataStoreManager(
    private val context: Context,
    private val shoppingListDao: ShoppingListDao
) {

    companion object {
        val BUDGET_KEY = doublePreferencesKey("budget_key")
        val DATE_KEY = stringPreferencesKey("date_key")
        val ARTICLE_KEY = stringPreferencesKey("article_key")
        val TOTAL_KEY = stringPreferencesKey("total_key")
    }



    suspend fun saveShoppingListToDatabase(budget: Double, date: String, article: Int, total: Double) {
        val shoppingListEntity = ShoppingListEntity(budget = budget, date = date, article = article, total = total)
        shoppingListDao.insertShoppingList(shoppingListEntity)
    }

    fun clearAllShoppingLists() {
        shoppingListDao.deleteAllShoppingLists()
    }
}
