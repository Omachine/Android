// app/src/main/java/com/exercicios/spacefighter/HighScoreManager.kt
package com.exercicios.spacefighter

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HighScoreManager(private val context: Context) {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun saveHighScore(score: Int) {
        val user = auth.currentUser
        user?.let {
            db.collection("users").document(it.uid).update("highScore", score)
        }
    }

    fun getHighScore(callback: (Int) -> Unit) {
        val user = auth.currentUser
        user?.let {
            db.collection("users").document(it.uid).get()
                .addOnSuccessListener { document ->
                    val highScore = document.getLong("highScore")?.toInt() ?: 0
                    callback(highScore)
                }
        }
    }
}