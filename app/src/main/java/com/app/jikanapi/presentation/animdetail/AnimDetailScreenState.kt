package com.app.jikanapi.presentation.animdetail

import com.app.jikanapi.data.model.AnimeEntity

data class AnimDetailScreenState(
    var isLoading: Boolean = true,
    var animData: AnimeEntity? = null,
)
