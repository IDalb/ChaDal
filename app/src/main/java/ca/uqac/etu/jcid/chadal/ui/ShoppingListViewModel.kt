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

    fun addArticle(article: Article) {
        _uiState.update { currentState ->
            currentState.copy(
                articles = currentState.articles + article,
                total = calculateTotal()
            )
        }
    }

    fun removeArticle(article: Article) {
        _uiState.update { currentState ->
            currentState.copy(
                articles = currentState.articles - article,
                total = calculateTotal()
            )
        }
    }

    private fun calculateTotal(): Double {
        var total:Double = 0.0
        _uiState.value.articles.forEach {
            // Tax calculation & application
            total += it.price * (1 + it.category.taxPercentage)
        }
        return total
    }
}