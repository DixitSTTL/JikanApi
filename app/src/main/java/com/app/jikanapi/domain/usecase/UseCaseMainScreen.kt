package com.app.jikanapi.domain.usecase

import androidx.paging.PagingData
import com.app.jikanapi.data.model.AnimeEntity
import com.app.jikanapi.domain.repository.AnimListRepository
import kotlinx.coroutines.flow.Flow

class UseCaseMainScreen(private val animListScreenRepository: AnimListRepository) {

    fun fetchFlowImage(): Flow<PagingData<AnimeEntity>> {
        return animListScreenRepository.getFlowAnimList()
    }

}