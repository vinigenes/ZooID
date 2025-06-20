package com.example.zooid.utils

fun String.toOptionList(): List<String> = this.split("||")
fun List<String>.toOptionString(): String = this.joinToString("||")