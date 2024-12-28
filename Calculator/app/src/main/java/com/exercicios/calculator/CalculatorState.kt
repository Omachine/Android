
package com.exercicios.calculator

data class CalculatorState(
    val input: String = "",
    val result: String = "",
    val history: List<String> = emptyList()
)