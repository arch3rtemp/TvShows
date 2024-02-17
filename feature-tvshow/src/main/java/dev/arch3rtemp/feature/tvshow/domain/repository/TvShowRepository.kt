package dev.arch3rtemp.feature.tvshow.domain.repository

import dev.arch3rtemp.feature.tvshow.domain.model.TvShow

interface TvShowRepository {
    suspend fun getPopularTvShows(page: Int): Result<List<TvShow>>

    suspend fun searchTvShows(query: String, page: Int): Result<List<TvShow>>

    suspend fun getTvShow(id: Int): Result<TvShow>

    suspend fun getSimilarTvShows(seriesId: String, page: Int): Result<List<TvShow>>
}