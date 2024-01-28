package dev.arch3rtemp.tvshows.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.arch3rtemp.feature.tvshow.data.local.dao.TvShowDao
import dev.arch3rtemp.feature.tvshow.data.local.entity.TvShowEntity
import dev.arch3rtemp.tvshows.storage.converter.RoomJsonConverter

@Database(entities = [TvShowEntity::class], version = 1, exportSchema = false)
@TypeConverters(RoomJsonConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tvShowDao(): TvShowDao
}