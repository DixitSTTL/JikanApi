package com.app.jikanapi.domain.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.jikanapi.data.model.AnimeEntity

@Dao
interface DaoAnim {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: AnimeEntity)

    @Update
    fun update(photo: AnimeEntity)

    @Query("SELECT * FROM anime")
    fun getAllAnim(): PagingSource<Int, AnimeEntity>

    @Query("DELETE FROM anime")
    suspend fun clearAll()

    @Query("SELECT * FROM anime WHERE malId = :id")
    suspend fun getAnimById(id: String): AnimeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animList: List<AnimeEntity>)
}