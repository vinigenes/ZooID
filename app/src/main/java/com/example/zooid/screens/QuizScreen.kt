package com.example.zooid.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.example.zooid.data.familyOrnitolabQuestions
import com.example.zooid.data.ppbioQuestions

@Composable
fun QuizScreen(
    id: Int,
    onExit: () -> Unit
) {
    val questions = remember(id) {
        when (id) {
            1 -> caatingaQuestions.shuffled()
            2 -> ppbioQuestions.shuffled()
            3 -> familyOrnitolabQuestions.shuffled()
            else -> emptyList()
        }
    }

    // Texto customizado para cada pacote
    val labelText = when (id) {
        3 -> "Digite o nome científico da família:"
        else -> "Digite o nome científico:"
    }

    var currentIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var isFinished by remember { mutableStateOf(false) }

    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onExit) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Sair"
                )
            }
        }

        LinearProgressIndicator(
            progress = ((currentIndex + 1).toFloat() / questions.size),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (!imeVisible) {
            Text(
                text = labelText,
                style = MaterialTheme.typography.titleLarge.copy(),
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
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
}


