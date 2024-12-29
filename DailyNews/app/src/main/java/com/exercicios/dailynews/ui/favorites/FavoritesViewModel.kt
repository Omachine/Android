package com.exercicios.dailynews.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exercicios.dailynews.models.Article
import com.exercicios.dailynews.repositories.ArticleRepository
import com.exercicios.dailynews.repositories.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

data class ArticlesState(
    val articles: List<Article> = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class FavoritesViewModel(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArticlesState())
    val uiState: StateFlow<ArticlesState> = _uiState.asStateFlow()

    fun fetchArticles() {
        articleRepository
            .fetchArticlesFromDb()
            .onEach { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = ArticlesState(
                            articles = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                    is ResultWrapper.Error -> {
                        _uiState.value = ArticlesState(
                            isLoading = false,
                            error = result.message
                        )
                    }
                    is ResultWrapper.Loading -> {
                        _uiState.value = ArticlesState(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}