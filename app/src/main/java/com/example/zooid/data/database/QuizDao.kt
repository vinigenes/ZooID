package com.example.zooid.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zooid.data.model.QuizQuestion

@Dao
interface QuizDao {
    @Query("SELECT * FROM quiz_questions ORDER BY RANDOM() LIMIT :limit")
    suspend fun getQuestionsWithLimit(limit: Int): List<QuizQuestion>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuizQuestion>)
}
