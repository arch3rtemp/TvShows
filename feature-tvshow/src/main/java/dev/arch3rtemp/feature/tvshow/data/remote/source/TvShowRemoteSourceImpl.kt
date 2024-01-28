package dev.arch3rtemp.feature.tvshow.data.remote.source

import dev.arch3rtemp.feature.tvshow.data.remote.response.TvShowResponse
import dev.arch3rtemp.feature.tvshow.data.remote.service.TvShowService
import javax.inject.Inject

class TvShowRemoteSourceImpl @Inject constructor(
    private val tvShowService: TvShowService
) : TvShowRemoteSource {
    override suspend fun fetchPopularTvShows(page: Int): TvShowResponse {
        return tvShowService.fetchPopularTvShows(page)
    }

    override suspend fun searchTvShows(query: String, page: Int): TvShowResponse {
        return tvShowService.searchTvShows(query, page)
    }
}