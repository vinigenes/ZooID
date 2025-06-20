package com.example.zooid.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.zooid.data.model.Animal
import com.example.zooid.data.model.QuizQuestion

@Database(entities = [Animal::class, QuizQuestion::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animalDao(): AnimalDao
    abstract fun quizDao(): QuizDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "zooid_db"
                ).build().also { INSTANCE = it }
            }
    }
}
