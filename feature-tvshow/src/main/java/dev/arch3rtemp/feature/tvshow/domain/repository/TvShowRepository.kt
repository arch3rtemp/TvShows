package dev.arch3rtemp.feature.tvshow.domain.repository

import dev.arch3rtemp.common.util.Resource
import dev.arch3rtemp.feature.tvshow.domain.model.TvShow

interface TvShowRepository {
    suspend fun getPopularTvShows(page: Int): Resource<List<TvShow>>

    suspend fun searchTvShows(query: String, page: Int): Resource<List<TvShow>>

    suspend fun getTvShow(id: Int): Resource<TvShow>
}