package com.example.zooid.data.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromList(options: List<String>): String = options.joinToString("||")

    @TypeConverter
    fun toList(optionsString: String): List<String> = optionsString.split("||")
}
