package com.example.zooid.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zooid.components.QuizName
import com.example.zooid.data.caatingaQuestions
import com.example.zooid.data.ppbioQuestions
import com.example.zooid.model.Question
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun QuizScreen(title: String) {
    // Escolhe a lista de perguntas com base no título
    val questions: List<Question> = when (title) {
        "Aves da Caatinga" -> caatingaQuestions
        "Aves da PPBIO" -> ppbioQuestions
        else -> emptyList()
    }

    // Controle de progresso
    var currentIndex by remember { mutableStateOf(0) }
    var answeredCorrectly by remember { mutableStateOf(false) } // controla se acertou a questão atual
    val isFinished = currentIndex >= questions.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Digite o Nome Científico:",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()  // ocupa largura total
                .padding(top = 16.dp, bottom = 24.dp),
            textAlign = TextAlign.Start
        )

        if (isFinished) {
            Text("Fim do quiz! 🎉", style = MaterialTheme.typography.titleLarge)
        } else {
            val question = questions[currentIndex]

            QuizName(
                imageResId = question.imageResId,
                correctAnswer = question.correctAnswer,
                onAnswerCorrect = {
                    // Só marca que acertou, sem avançar ainda
                    answeredCorrectly = true
                },
                onNext = {
                    // Avança para a próxima questão e reseta o estado
                    currentIndex++
                    answeredCorrectly = false
                },
                answeredCorrectly = answeredCorrectly // para controlar UI dentro do QuizName
            )
        }
    }
}
