package com.exercicios.dailynews.repositories

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.exercicios.dailynews.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val context: Context,
    private val firestore: FirebaseFirestore
) {

    fun fetchArticles(path: String): Flow<ResultWrapper<List<Article>>> =
        flow {
            emit(ResultWrapper.Loading())
            try {
                val articles = fetchArticlesFromFirestore(path)
                emit(ResultWrapper.Success(articles))
            } catch (e: IOException) {
                emit(ResultWrapper.Error(e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)

    private suspend fun fetchArticlesFromFirestore(path: String): List<Article> {
        val snapshot = firestore.collection(path).get().await()
        return snapshot.documents.map { document ->
            document.toObject(Article::class.java)!!
        }
    }

    fun fetchArticlesFromDb(): Flow<ResultWrapper<List<Article>>> =
        flow {
            val articles = fetchArticlesFromFirestore("favorites")
            emit(ResultWrapper.Success(articles))
        }.flowOn(Dispatchers.IO)
}