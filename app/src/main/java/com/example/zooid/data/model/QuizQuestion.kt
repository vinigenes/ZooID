// data/model/QuizQuestion.kt
package com.example.zooid.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "quiz_questions")
data class QuizQuestion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionText: String,
    val imagePath: String?,
    val audioPath: String?,
    val correctAnswer: String,
    val options: List<String> // Alterado para List<String> (usar TypeConverter)
)

