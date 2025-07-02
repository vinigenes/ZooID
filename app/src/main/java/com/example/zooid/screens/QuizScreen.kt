package com.example.zooid.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.zooid.components.QuizName
import com.example.zooid.data.caatingaQuestions
import com.example.zooid.data.ppbioQuestions
import com.example.zooid.model.Question

@Composable
fun QuizScreen(title: String) {
    val questions: List<Question> = when (title) {
        "Aves da Caatinga" -> caatingaQuestions
        "Aves da PPBIO" -> ppbioQuestions
        else -> emptyList()
    }

    var currentIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var isFinished by remember { mutableStateOf(false) }

    // Detecta se o teclado está aberto
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp)) // espaço inicial no topo

        // Mostrar o texto apenas se o teclado NÃO estiver aberto
        if (!imeVisible) {
            Text(
                text = "Digite o nome científico:",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Start
            )
        }

        when {
            questions.isEmpty() -> {
                Text("Nenhuma questão disponível", style = MaterialTheme.typography.bodyLarge)
            }
            isFinished -> {
                // Tela de resultados
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Quiz Concluído!",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = "Pontuação: $score/${questions.size}",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            else -> {
                val question = questions[currentIndex]

                QuizName(
                    imageResId = question.imageResId,
                    correctAnswer = question.correctAnswer,
                    onAnswerCorrect = { score++ },
                    onNext = {
                        if (currentIndex + 1 >= questions.size) {
                            isFinished = true
                        } else {
                            currentIndex++
                        }
                    }
                )
            }
        }
    }
}
