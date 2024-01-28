package dev.arch3rtemp.feature.tvshow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.arch3rtemp.feature.tvshow.data.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tvShows: List<TvShowEntity>)

    @Query("SELECT * FROM tv_show_table")
    suspend fun selectTvShows(): List<TvShowEntity>

    @Query("SELECT * FROM tv_show_table")
    fun observeTvShows(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tv_show_table WHERE id == :id")
    suspend fun selectTvShow(id: Int): TvShowEntity
}