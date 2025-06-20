package com.example.zooid.data.repository

import com.example.zooid.data.database.AnimalDao
import com.example.zooid.data.model.Animal

class AnimalRepository(private val dao: AnimalDao) {
    suspend fun getAllAnimals() = dao.getAllAnimals()
    suspend fun insertAnimals(list: List<Animal>) = dao.insertAll(list)
}
