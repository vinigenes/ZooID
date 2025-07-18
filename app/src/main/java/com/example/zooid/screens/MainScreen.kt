package com.example.zooid.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zooid.R
import com.example.zooid.components.AppSearchBar
import com.example.zooid.components.QuizPack
import com.example.zooid.components.QuizPackCard
import com.example.zooid.ui.ZooIdTheme
import com.example.zooid.components.AppTopBar
import com.example.zooid.data.familyOrnitolabQuestions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onStartQuiz: (Int) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    val quizPacks = listOf(
        QuizPack(
            id = 1,
            "Aves da Caatinga",
            "Identifique as aves de um bioma singular do Brasil.",
            100,
            imageResId = R.drawable.caatinga,
            imageSizeDp = 60
        ),
        QuizPack(
            id = 2,
            "Aves do PPBIO",
            "Identifique as aves de interesse do Programa de Pesquisa em Biodiversidade.",
            100,
            imageResId = R.drawable.ppbio,
            imageSizeDp = 60
        ),
        QuizPack(
            id = 3,
            title = "Famílias de Aves ",
            description = "Identifique as famílias das aves presentes na coleção do Ornitolab",
            speciesCount = familyOrnitolabQuestions.size,
            imageResId = R.drawable.logoornitolab,
            imageSizeDp = 60
        )
    )

    val filteredPacks = quizPacks.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            AppTopBar()
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // Fundo preto por trás da imagem
        ) {
            
            // Conteúdo da tela
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
                            id = pack.id,
                            title = pack.title,
                            description = pack.description,
                            speciesCount = pack.speciesCount,
                            imageResId = pack.imageResId,
                            imageSizeDp = pack.imageSizeDp,
                            onStartClick = { onStartQuiz(pack.id) }
                        )
                    }
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