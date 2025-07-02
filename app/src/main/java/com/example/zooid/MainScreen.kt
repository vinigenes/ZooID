package com.example.zooid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zooid.components.AppSearchBar
import com.example.zooid.components.QuizPack
import com.example.zooid.components.QuizPackCard
import com.example.zooid.ui.ZooIdTheme
import com.example.zooid.components.AppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onStartQuiz: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    // Lista de pacotes fictícios
    val quizPacks = listOf(
        QuizPack(
            "Aves da Caatinga",
            "Identifique as aves de um bioma singular do Brasil.",
            100,
            imageResId = R.drawable.caatinga,
            imageSizeDp = 60
        ),
        QuizPack(
            "Aves da Caatinga",
            "Identifique as aves de interesse do PPBIO.",
            100,
            imageResId = R.drawable.ppbio,
            imageSizeDp = 60
        ),
    )

    // Filtro opcional (não obrigatório agora)
    val filteredPacks = quizPacks.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            AppTopBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AppSearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { newQuery -> searchQuery = newQuery }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(filteredPacks) { pack ->
                    QuizPackCard(
                        title = pack.title,
                        description = pack.description,
                        speciesCount = pack.speciesCount,
                        imageResId = pack.imageResId,
                        imageSizeDp = pack.imageSizeDp,
                        onStartClick = { onStartQuiz(pack.title) }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ZooIdTheme {
        MainScreen(onStartQuiz = {})
    }
}