package com.exercicios.shoppinglist.ui.lists.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exercicios.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun AddItemView(modifier: Modifier = Modifier, navController: NavController = rememberNavController(), listId: String) {
    val viewModel: AddItemViewModel = viewModel()
    val state = viewModel.state.value

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            placeholder = { Text("Enter item name") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = state.qtd ?: "",
            onValueChange = { viewModel.onQttChange(it) },
            placeholder = { Text("Enter quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        Button(onClick = {
            viewModel.addItem(listId)
            navController.popBackStack()
        }) {
            Text("Add")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddListViewPreview() {
    ShoppingListTheme {
        AddItemView(listId = "none")
    }
}