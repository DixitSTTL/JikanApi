package com.app.jikanapi.data.repository.animList

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.app.jikanapi.data.model.AnimeEntity
import com.app.jikanapi.data.network.NetworkClient
import com.app.jikanapi.domain.room.DatabaseHelper

@OptIn(ExperimentalPagingApi::class)
class AnimRemoteMediator(
    private val api: NetworkClient,
    private val database: DatabaseHelper
) : RemoteMediator<Int, AnimeEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeEntity>
    ): MediatorResult {
        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.APPEND -> state.pages.size + 1
                LoadType.PREPEND -> return MediatorResult.Success(true)
            }

            val response = api.getFlowImageList(page)

            response.data?.let { data ->
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.daoAnim().clearAll()
                    }
                    database.daoAnim().insertAll(
                        data.map { it.toEntity() }
                    )
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = response.data == null
            )

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
