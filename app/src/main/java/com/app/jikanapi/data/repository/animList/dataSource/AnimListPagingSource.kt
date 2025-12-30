package com.app.jikanapi.data.repository.animList.dataSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.jikanapi.data.model.AnimeEntity
import com.app.jikanapi.data.network.NetworkClient
import com.app.jikanapi.domain.room.DatabaseHelper

class AnimListPagingSource(
    private val networkClient: NetworkClient,
    private val databaseHelper: DatabaseHelper
) :
    PagingSource<Int, AnimeEntity>() {
    override fun getRefreshKey(state: PagingState<Int, AnimeEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }


    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeEntity> {
        return try {
            val page = params.key ?: 1
            val response = networkClient.getFlowAnimList(page = page)
            Log.d("TAG", "load: " + response)

            /*if (page==1){
                databaseHelper.daoAnim().clearAll()
            }
            databaseHelper.daoAnim().insertAll(response.data.map { it.toEntity() })*/

            response.data?.let { data ->

                LoadResult.Page(
                    data = data.map { it.toEntity() },
                    prevKey = if (page == 1) null else page.minus(1),
                    nextKey = page.plus(1),
                )
            } ?: run {
                LoadResult.Error(
                    throwable = Throwable("Data not found")
                )
            }
        } catch (e: Exception) {
            Log.d("TAG", "loadeee: " + e.message)
            LoadResult.Error(e)

        }
    }
}