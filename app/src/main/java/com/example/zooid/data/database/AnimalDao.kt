package com.example.zooid.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zooid.data.model.Animal

@Dao
interface AnimalDao {
    @Query("SELECT * FROM animals")
    suspend fun getAllAnimals(): List<Animal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animals: List<Animal>)
}
