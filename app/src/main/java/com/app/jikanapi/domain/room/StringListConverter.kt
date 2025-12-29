package com.app.jikanapi.domain.room

import androidx.room.TypeConverter

class StringListConverter {

    @TypeConverter
    fun fromList(list: List<String?>?): String {
        return list?.joinToString(separator = "|") ?: ""
    }

    @TypeConverter
    fun toList(data: String?): List<String?> {
        return data
            ?.takeIf { it.isNotEmpty() }
            ?.split("|")
            ?: emptyList()
    }
}
