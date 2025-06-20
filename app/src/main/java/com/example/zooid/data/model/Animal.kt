package com.example.zooid.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
data class Animal(
    @PrimaryKey val id: Int,
    val name: String,
    val imagePath: String,
    val audioPath: String,
    val description: String
)
