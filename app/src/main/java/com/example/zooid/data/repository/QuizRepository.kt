package com.example.zooid.data.repository

import com.example.zooid.data.database.QuizDao
import com.example.zooid.data.model.QuizQuestion

class QuizRepository(private val dao: QuizDao) {
    suspend fun getRandomQuestions(count: Int): List<QuizQuestion> {
        return dao.getQuestionsWithLimit(count)
    }
}
