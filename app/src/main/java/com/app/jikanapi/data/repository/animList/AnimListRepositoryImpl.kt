package com.app.jikanapi.data.repository.animList

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.jikanapi.data.model.AnimeEntity
import com.app.jikanapi.data.network.NetworkClient
import com.app.jikanapi.data.repository.animList.dataSource.AnimListPagingSource
import com.app.jikanapi.domain.repository.AnimListRepository
import com.app.jikanapi.domain.room.DatabaseHelper
import kotlinx.coroutines.flow.Flow

class AnimListRepositoryImpl(
    private val animListPagingSource: AnimListPagingSource,
    private val networkClient: NetworkClient,
    private val databaseHelper: DatabaseHelper
) : AnimListRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getFlowAnimList(): Flow<PagingData<AnimeEntity>> = Pager(
        config = PagingConfig(
            pageSize = 25,
            initialLoadSize = 25,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            databaseHelper.daoAnim().getAllAnim()
        },
        remoteMediator = AnimRemoteMediator(networkClient, databaseHelper)
    ).flow

}