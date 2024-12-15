package ca.uqac.etu.jcid.chadal.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.uqac.etu.jcid.chadal.data.Article
import ca.uqac.etu.jcid.chadal.data.ShoppingListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * The view model of a shopping list
 * It contains all the methods that are used to manipulate a shopping list (add/remove an article,
 * reset the list...)
 */
class ShoppingListViewModel (
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingListUiState())
    val uiState: StateFlow<ShoppingListUiState> = _uiState.asStateFlow()
    private val _currentShoppingListId = MutableLiveData<Long>()


    fun resetShoppingList() {
        _uiState.update { currentState ->
            currentState.copy(
                articles = listOf(),
                total = 0.0,
                budget = null,
                name = ""
            )
        }
    }

    fun addArticle(article: Article) {
        _uiState.update { currentState ->
            currentState.copy(
                articles = currentState.articles + article,
            )
        }
    }

    fun removeArticle(article: Article) {
        _uiState.update { currentState ->
            currentState.copy(
                articles = currentState.articles - article,
            )

        }
    }

    fun setBudget(budget: Double) {
        _uiState.value = _uiState.value.copy(budget = budget)
    }

    fun setCurrentShoppingListId(id: Long) {
        _currentShoppingListId.value = id
    }

    fun getCurrentShoppingListId(): Long {
        return _currentShoppingListId.value ?: 0L
    }

    fun updateShoppingListInfo(total: Double, articleCount: Int) {
        _uiState.value = _uiState.value.copy(
            total = total,
            articles = _uiState.value.articles,
            budget = _uiState.value.budget,
            lastScanValue = _uiState.value.lastScanValue
        )
    }
}
