package com.exercicios.shoppinglist.ui.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exercicios.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun AddListView(modifier: Modifier = Modifier, navController: NavController = rememberNavController()) {
    val viewModel: AddListViewModel = viewModel()
    val state = viewModel.state.value

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            placeholder = { Text("Enter list name") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        Button(onClick = {
            viewModel.addList()
            navController.popBackStack()
        }) {
            Text("Add")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddListViewPreview(){
    ShoppingListTheme {
        AddListView()
    }
}