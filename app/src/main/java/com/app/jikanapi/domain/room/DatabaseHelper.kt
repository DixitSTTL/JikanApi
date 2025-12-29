package com.app.jikanapi.domain.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.jikanapi.data.model.AnimeEntity
import com.app.jikanapi.domain.room.dao.DaoAnim

@Database(entities = [AnimeEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun daoAnim(): DaoAnim

    companion object {
        private var INSTANCE: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHelper {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    DatabaseHelper::class.java,
                    "anim_database"
                ).build()

                INSTANCE = instance
                instance
            }

        }

    }

}