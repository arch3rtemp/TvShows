package dev.arch3rtemp.feature.tvshow.data.local.source

import dev.arch3rtemp.feature.tvshow.data.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

interface TvShowLocalSource {

    suspend fun savePopularTvShows(tvShows: List<TvShowEntity>)
    suspend fun getPopularTvShows(): List<TvShowEntity>
    suspend fun observeTvShows(): Flow<List<TvShowEntity>>
    suspend fun getTvShow(id: Int): TvShowEntity
}