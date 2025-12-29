package com.app.jikanapi.data.repository.animdetail

import com.app.jikanapi.data.model.AnimDetailDTO
import com.app.jikanapi.data.model.AnimeEntity
import com.app.jikanapi.data.network.NetworkClient
import com.app.jikanapi.data.utils.ResponseResult
import com.app.jikanapi.domain.repository.AnimDetailRepository
import com.app.jikanapi.domain.room.DatabaseHelper

class AnimDetailRepositoryImpl(
    private val networkClient: NetworkClient,
    private val databaseHelper: DatabaseHelper
) : AnimDetailRepository {
    override suspend fun getAnimDetail(id: String): ResponseResult<AnimDetailDTO> {
        return networkClient.getAnimDetail(id)
    }

    override suspend fun getAnimDetailFromDatabase(id: String): AnimeEntity? {
        return databaseHelper.daoAnim().getAnimById(id)
    }

}