package com.exercicios.shoppinglist.ui.lists

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.exercicios.shoppinglist.TAG
import com.exercicios.shoppinglist.models.ListItems
import com.exercicios.shoppinglist.repositories.ListItemsRepository

data class ListListsState(
    val listItemsList : List<ListItems> = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ListListsViewModel : ViewModel(){

    var state = mutableStateOf(ListListsState())
        private set

    fun getLists(){

        ListItemsRepository.getLists{ listItemsList ->
            state.value = state.value.copy(
                listItemsList = listItemsList
            )
        }
    }

    fun logout() {
        val auth = Firebase.auth
        val currentUser = auth.signOut()
    }


}