package com.exercicios.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exercicios.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
                CalculatorScreen(
                    state = state,
                    onInput = viewModel::onInput,
                    onClear = viewModel::onClear,
                    onDelete = viewModel::onDelete,
                    onCalculate = viewModel::onCalculate
                )
            }
        }
    }
}

@Composable
fun CalculatorScreen(
    state: CalculatorState,
    onInput: (String) -> Unit,
    onClear: () -> Unit,
    onDelete: () -> Unit,
    onCalculate: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = state.input,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.result,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { onInput("(") }, modifier = Modifier.weight(1f)) { Text("(") }
                Button(onClick = { onInput(")") }, modifier = Modifier.weight(1f)) { Text(")") }
                Button(onClick = { onClear() }, modifier = Modifier.weight(1f)) { Text("C") }
                Button(onClick = { onDelete() }, modifier = Modifier.weight(1f)) { Text("DEL") }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { onInput("1") }, modifier = Modifier.weight(1f)) { Text("1") }
                Button(onClick = { onInput("2") }, modifier = Modifier.weight(1f)) { Text("2") }
                Button(onClick = { onInput("3") }, modifier = Modifier.weight(1f)) { Text("3") }
                Button(onClick = { onInput("+") }, modifier = Modifier.weight(1f)) { Text("+") }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { onInput("4") }, modifier = Modifier.weight(1f)) { Text("4") }
                Button(onClick = { onInput("5") }, modifier = Modifier.weight(1f)) { Text("5") }
                Button(onClick = { onInput("6") }, modifier = Modifier.weight(1f)) { Text("6") }
                Button(onClick = { onInput("-") }, modifier = Modifier.weight(1f)) { Text("-") }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { onInput("7") }, modifier = Modifier.weight(1f)) { Text("7") }
                Button(onClick = { onInput("8") }, modifier = Modifier.weight(1f)) { Text("8") }
                Button(onClick = { onInput("9") }, modifier = Modifier.weight(1f)) { Text("9") }
                Button(onClick = { onInput("*") }, modifier = Modifier.weight(1f)) { Text("*") }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { onInput("0") }, modifier = Modifier.weight(1f)) { Text("0") }
                Button(onClick = { onInput("/") }, modifier = Modifier.weight(1f)) { Text("/") }
                Button(onClick = { onCalculate() }, modifier = Modifier.weight(2f)) { Text("=") }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.height(200.dp)) {
            LazyColumn {
                items(state.history) { historyItem ->
                    Text(text = historyItem)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorTheme {
        CalculatorScreen(
            state = CalculatorState(),
            onInput = {},
            onClear = {},
            onDelete = {},
            onCalculate = {}
        )
    }
}