// app/src/main/java/com/exercicios/spacefighter/GameOverView.kt
package com.exercicios.spacefighter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameOverView(
    modifier: Modifier = Modifier,
    score: Int = 0,
    onRestartClick: () -> Unit = {},
    onExitClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Game Over",
                fontSize = 100.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "High Score: $score",
                fontSize = 42.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRestartClick) {
                Text(text = "Restart")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onExitClick) {
                Text(text = "Exit to Main Menu")
            }
        }
    }
}