package com.app.jikanapi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class AnimeEntity(

    @PrimaryKey
    val malId: Int?,

    val title: String?,
    val titleEnglish: String?,
    val titleJapanese: String?,

    val imageUrl: String?,

    val type: String?,
    val source: String?,
    val episodes: Int?,
    val status: String?,

    val score: Double?,
    val popularity: Int?,
    val rank: Int?,

    val synopsis: String?,
    val season: String?,
    val year: Int?,

    val duration: String?,
    val rating: String?,

    val genres: List<String?>?,
    val themes: List<String?>?,
    val studios: List<String?>?,

    val isFavorite: Boolean = false
)
