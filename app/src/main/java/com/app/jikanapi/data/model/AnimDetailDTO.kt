package com.app.jikanapi.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AnimDetailDTO(
    @SerialName("data")
    val `data`: AnimDataDTO
)
