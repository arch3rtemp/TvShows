package dev.arch3rtemp.tvshows.data.remote.source

import dev.arch3rtemp.tvshows.data.remote.response.TvShowResponse
import dev.arch3rtemp.tvshows.data.remote.service.TvShowService
import javax.inject.Inject

class TvShowRemoteSourceImpl @Inject constructor(
    private val tvShowService: TvShowService
) : TvShowRemoteSource {
    override suspend fun fetchPopularTvShows(page: Int): TvShowResponse {
        return tvShowService.fetchPopularTvShows(page)
    }
}