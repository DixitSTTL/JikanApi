package com.app.jikanapi.presentation.main

import androidx.paging.PagingData
import com.app.jikanapi.data.model.AnimeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MainScreenState(
    var isLoading: Boolean = true,
    var animFlowList: Flow<PagingData<AnimeEntity>> = emptyFlow(),
)
