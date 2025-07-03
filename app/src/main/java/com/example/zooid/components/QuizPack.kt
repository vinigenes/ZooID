package com.example.zooid.components

data class QuizPack(
    val id: Int,
    val title: String,
    val description: String,
    val speciesCount: Int,
    val imageResId: Int,        // ID da imagem
    val imageSizeDp: Int = 80
)
