package ca.uqac.etu.jcid.chadal.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShoppingListViewModel : ViewModel() {
    private val _currentShoppingListId = MutableStateFlow<Long?>(null)
    val currentShoppingListId: StateFlow<Long?> = _currentShoppingListId

    fun setCurrentShoppingListId(id: Long) {
        _currentShoppingListId.value = id
    }
}
