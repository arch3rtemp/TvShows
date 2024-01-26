package dev.arch3rtemp.tvshows.data.remote.source

import dev.arch3rtemp.tvshows.data.remote.response.TvShowResponse

interface TvShowRemoteSource {
    suspend fun fetchPopularTvShows(page: Int): TvShowResponse
}