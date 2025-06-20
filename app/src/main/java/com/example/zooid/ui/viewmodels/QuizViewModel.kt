package com.example.zooid.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zooid.data.model.QuizQuestion
import com.example.zooid.features.quiz.QuizManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel(private val quizManager: QuizManager): ViewModel() {
    private val _questions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val questions: StateFlow<List<QuizQuestion>> = _questions

    fun loadQuiz(count: Int) {
        viewModelScope.launch {
            _questions.value = quizManager.generateQuiz(count)
        }
    }
}

