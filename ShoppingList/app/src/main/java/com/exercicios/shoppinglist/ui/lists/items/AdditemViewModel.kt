package com.exercicios.shoppinglist.ui.lists.items

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.exercicios.shoppinglist.models.Item
import com.exercicios.shoppinglist.repositories.ItemRepository

data class AddItemState(
    val name: String = "",
    val qtd: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class AddItemViewModel : ViewModel() {

    var state = mutableStateOf(AddItemState())
        private set

    fun onNameChange(name: String) {
        state.value = state.value.copy(name = name)
    }

    fun onQttChange(qtd: String) {
        state.value = state.value.copy(qtd = qtd)
    }

    fun addItem(listId: String) {
        val quantity = state.value.qtd?.toDoubleOrNull() ?: 0.0
        val item = Item(
            "",
            state.value.name,
            quantity
        )
        ItemRepository.addItem(listId, item)
    }
}