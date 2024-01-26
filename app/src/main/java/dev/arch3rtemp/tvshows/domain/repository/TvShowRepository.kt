package dev.arch3rtemp.tvshows.domain.repository

import dev.arch3rtemp.tvshows.common.Resource
import dev.arch3rtemp.tvshows.domain.model.TvShow

interface TvShowRepository {
    suspend fun getPopularTvShows(page: Int): Resource<List<TvShow>>

    suspend fun searchTvShows(query: String, page: Int): Resource<List<TvShow>>
}