package com.example.zooid.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.zooid.quiz.QuizName
import com.example.zooid.quiz.QuizMultiple
import com.example.zooid.data.caatingaQuestions
import com.example.zooid.data.familyOrnitolabQuestions
import com.example.zooid.data.ppbioQuestions
import com.example.zooid.model.QuizType
import androidx.compose.ui.unit.sp


@Composable
fun QuizScreen(
    id: Int,
    onExit: () -> Unit
) {
    val baseQuestions = remember(id) {
        when (id) {
            1 -> caatingaQuestions
            2 -> ppbioQuestions
            3 -> familyOrnitolabQuestions
            else -> emptyList()
        }
    }

    val questions = remember(baseQuestions) {
        baseQuestions.map { question ->
            val randomType = if ((0..1).random() == 0) QuizType.TEXT else QuizType.MULTIPLE

            if (randomType == QuizType.MULTIPLE) {
                val wrongOptions = baseQuestions
                    .filter { it.correctAnswer != question.correctAnswer }
                    .shuffled()
                    .take(3)
                    .map { it.correctAnswer }

                val allOptions = (wrongOptions + question.correctAnswer).shuffled()

                question.copy(
                    quizType = QuizType.MULTIPLE,
                    options = allOptions
                )
            } else {
                question.copy(quizType = QuizType.TEXT)
            }
        }.shuffled()
    }

    val labelText = when (id) {
        3 -> "Selecione o nome científico da família:"
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
                .padding(vertical = 4.dp),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (!imeVisible) {
            Text(
                text = labelText,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp), // Aqui diminui a fonte
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
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
                    when (question.quizType) {
                        QuizType.TEXT -> {
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
                        QuizType.MULTIPLE -> {
                            QuizMultiple(
                                imageResId = question.imageResId,
                                options = question.options,
                                correctIndex = question.options.indexOf(question.correctAnswer)
                                    .takeIf { it >= 0 } ?: 0,
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
    }
}
