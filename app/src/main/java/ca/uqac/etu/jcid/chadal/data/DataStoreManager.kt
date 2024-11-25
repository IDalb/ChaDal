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

    suspend fun saveShoppingListToDatabase(budget: Double, date: String, article: Int, total: Double): Long {
        // Créez un objet ShoppingListEntity avec les données
        val shoppingListEntity = ShoppingListEntity(budget = budget, date = date, article = article, total = total)

        // Insérez l'objet dans la base de données et récupérez l'ID généré
        return shoppingListDao.insertShoppingList(shoppingListEntity)
    }

    fun clearAllShoppingLists() {
        shoppingListDao.deleteAllShoppingLists()
    }
}

