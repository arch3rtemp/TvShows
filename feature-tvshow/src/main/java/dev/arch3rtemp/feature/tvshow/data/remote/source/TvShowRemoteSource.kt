package dev.arch3rtemp.feature.tvshow.data.remote.source

import dev.arch3rtemp.feature.tvshow.data.remote.response.TvShowResponse

interface TvShowRemoteSource {
    suspend fun fetchPopularTvShows(page: Int): TvShowResponse
    suspend fun searchTvShows(query: String, page: Int): TvShowResponse
    suspend fun fetchSimilarTvShows(seriesId: String): TvShowResponse
}