package com.app.jikanapi.domain.repository

import com.app.jikanapi.data.model.AnimDetailDTO
import com.app.jikanapi.data.model.AnimeEntity
import com.app.jikanapi.data.utils.ResponseResult

interface AnimDetailRepository {
    suspend fun getAnimDetail(id: String): ResponseResult<AnimDetailDTO>
    suspend fun getAnimDetailFromDatabase(id: String): AnimeEntity?
}