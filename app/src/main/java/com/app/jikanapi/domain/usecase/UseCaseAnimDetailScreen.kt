package com.app.jikanapi.domain.usecase

import com.app.jikanapi.data.model.AnimDetailDTO
import com.app.jikanapi.data.model.AnimeEntity
import com.app.jikanapi.data.utils.ResponseResult
import com.app.jikanapi.domain.repository.AnimDetailRepository

class UseCaseAnimDetailScreen(private val animDetailRepository: AnimDetailRepository) {

    suspend fun fetchAnimDetail(id: String): ResponseResult<AnimDetailDTO> {
        return animDetailRepository.getAnimDetail(id)
    }

    suspend fun fetchAnimDetailFromDatabase(id: String): AnimeEntity? {
        return animDetailRepository.getAnimDetailFromDatabase(id)
    }

}