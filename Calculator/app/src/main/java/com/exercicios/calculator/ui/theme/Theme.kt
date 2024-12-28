
package com.exercicios.calculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    primary = LightBlue,
    onPrimary = White,
    secondary = DarkBlue,
    onSecondary = White,
    background = LightGray,
    onBackground = DarkGray,
    surface = White,
    onSurface = DarkGray
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkBlue,
    onPrimary = White,
    secondary = LightBlue,
    onSecondary = DarkGray,
    background = DarkGray,
    onBackground = LightGray,
    surface = DarkGray,
    onSurface = LightGray
)

@Composable
fun CalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}