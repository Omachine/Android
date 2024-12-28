package com.exercicios.shoppinglist.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exercicios.shoppinglist.ui.theme.ShoppingListTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProfileView(modifier: Modifier = Modifier, navController: NavController = rememberNavController()) {
    val viewModel: ProfileViewModel = viewModel()
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = remember { CoroutineScope(Dispatchers.Main) }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = state.name ?: "",
            onValueChange = viewModel::onNameChange,
            placeholder = { Text("Enter name") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        Text(state.user?.email ?: "")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.saveUser()
            navController.popBackStack()
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Profile saved successfully")
            }
        }) {
            Text("Save")
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.getUser()
    }

    SnackbarHost(hostState = snackbarHostState)
}

@Preview(showBackground = true)
@Composable
fun ProfileViewPreview(){
    ShoppingListTheme {
        ProfileView()
    }
}