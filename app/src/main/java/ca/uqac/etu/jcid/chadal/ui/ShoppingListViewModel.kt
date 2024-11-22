package ca.uqac.etu.jcid.chadal.ui

import androidx.lifecycle.ViewModel
import ca.uqac.etu.jcid.chadal.data.Article
import ca.uqac.etu.jcid.chadal.data.ShoppingListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShoppingListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingListUiState())
    val uiState: StateFlow<ShoppingListUiState> = _uiState.asStateFlow()

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

}