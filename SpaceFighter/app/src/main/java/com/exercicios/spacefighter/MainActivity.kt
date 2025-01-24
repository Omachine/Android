package com.exercicios.spacefighter

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.exercicios.spacefighter.ui.theme.SpaceFighterTheme
import com.google.firebase.FirebaseApp



class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val highScoreManager = HighScoreManager(this)
            SpaceFighterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    NavHost(navController = navController,
                        startDestination = "auth") {
                        composable("auth") {
                            AuthScreen(onAuthSuccess = {
                                navController.navigate("game_start")
                            })
                        }
                        composable("game_start") {
                            GameHomeView(
                                onPlayClick = {
                                    navController.navigate("game_screen")
                                },
                                onHighScoresClick = {
                                    navController.navigate("high_scores")
                                }
                            )
                        }
                        composable("game_screen") {
                            GameScreenView { score ->
                                navController.navigate("game_over/$score")
                            }
                        }
                        composable(
                            "game_over/{score}",
                            arguments = listOf(
                                navArgument("score") { type = NavType.IntType; defaultValue = 0 }
                            )
                        ) { backStackEntry ->
                            val score = backStackEntry.arguments?.getInt("score") ?: 0
                            GameOverView(
                                score = score,
                                onRestartClick = {
                                    navController.navigate("game_screen") {
                                        popUpTo("game_over") { inclusive = true }
                                    }
                                },
                                onExitClick = {
                                    navController.navigate("game_start") {
                                        popUpTo("game_over") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("high_scores") {
                            val highScore = remember { mutableStateOf(0) }

                            LaunchedEffect(Unit) {
                                highScoreManager.getHighScore { score ->
                                    highScore.value = score
                                }
                            }

                            HighScoresView(
                                highScore = highScore.value,
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}