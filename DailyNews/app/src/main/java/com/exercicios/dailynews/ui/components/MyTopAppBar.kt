package com.exercicios.dailynews.ui.components

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exercicios.dailynews.models.Article
import com.exercicios.dailynews.ui.theme.DailyNewsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    navController: NavController,
    title: String,
    isBaseScreen: Boolean,
    article: Article?
) {
    val scope = rememberCoroutineScope()
    var articleIsFavorite by remember { mutableStateOf(false) }
    val firestore = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    TopAppBar(
        title = {
            Text(
                text = article?.title ?: title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (!isBaseScreen) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = {
            if (!isBaseScreen) {
                IconButton(onClick = {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, article?.title)
                        putExtra(Intent.EXTRA_SUBJECT, article?.description)
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(sendIntent, null))
                }) {
                    Icon(imageVector = Icons.Filled.Share, contentDescription = "Share")
                }
                IconButton(onClick = {
                    scope.launch(Dispatchers.IO) {
                        if (article != null) {
                            firestore.collection("favorites")
                                .document(article.url)
                                .set(article)
                                .addOnSuccessListener {
                                    articleIsFavorite = true
                                }
                                .addOnFailureListener {
                                    articleIsFavorite = false
                                }
                        }
                    }
                }) {
                    Icon(
                        imageVector = if (articleIsFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorites"
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MyTopBarPreview() {
    DailyNewsTheme {
        MyTopAppBar(
            navController = rememberNavController(),
            title = "Test Title",
            isBaseScreen = false,
            article = Article(
                title = "news title",
                url = ""
            )
        )
    }
}