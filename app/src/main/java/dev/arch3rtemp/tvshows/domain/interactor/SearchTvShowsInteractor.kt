package dev.arch3rtemp.tvshows.domain.interactor

import dev.arch3rtemp.tvshows.common.Resource
import dev.arch3rtemp.tvshows.domain.model.TvShow
import dev.arch3rtemp.tvshows.domain.repository.TvShowRepository

class SearchTvShowsInteractor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(query: String, page: Int): Resource<List<TvShow>> {
        return tvShowRepository.searchTvShows(query, page)
    }
}