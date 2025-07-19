package com.example.zooid.model

data class Question(
    val imageResId: Int,
    val correctAnswer: String,
    val options: List<String> = emptyList(),
    val quizType: QuizType = QuizType.TEXT
)

enum class QuizType {
    TEXT,
    MULTIPLE
}