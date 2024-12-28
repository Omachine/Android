
package com.exercicios.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {
    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state

    fun onInput(input: String) {
        _state.value = _state.value.copy(input = _state.value.input + input)
    }

    fun onClear() {
        _state.value = CalculatorState()
    }

    fun onDelete() {
        _state.value = _state.value.copy(input = _state.value.input.dropLast(1))
    }

    fun onCalculate() {
        val expression = _state.value.input
        try {
            val result = evaluateExpression(expression)
            val newHistory = _state.value.history.toMutableList().apply {
                add("$expression = $result")
                if (size > 5) removeAt(0) // Keep only the last 5 entries
            }
            _state.value = CalculatorState(result = result, history = newHistory)
        } catch (e: Exception) {
            _state.value = _state.value.copy(result = "Error")
        }
    }

    private fun evaluateExpression(expression: String): String {
        val expr = ExpressionBuilder(expression).build()
        val result = expr.evaluate()
        return result.toString()
    }
}