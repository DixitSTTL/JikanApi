package com.app.jikanapi.domain.repository

import androidx.paging.PagingData
import com.app.jikanapi.data.model.AnimeEntity
import kotlinx.coroutines.flow.Flow

interface AnimListRepository {
    fun getFlowAnimList(): Flow<PagingData<AnimeEntity>>
}