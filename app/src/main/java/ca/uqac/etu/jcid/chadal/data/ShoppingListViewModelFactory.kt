package ca.uqac.etu.jcid.chadal.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShoppingListViewModelFactory(
    private val shoppingListDao: ShoppingListDao,
    private val articleDao: ArticleDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ca.uqac.etu.jcid.chadal.ui.ShoppingListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
