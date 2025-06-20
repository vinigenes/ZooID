package com.example.zooid.features.quiz

import com.example.zooid.data.model.QuizQuestion
import com.example.zooid.data.repository.QuizRepository

class QuizManager(private val repository: QuizRepository) {
    suspend fun generateQuiz(count: Int): List<QuizQuestion> {
        return repository.getRandomQuestions(count)
    }
}