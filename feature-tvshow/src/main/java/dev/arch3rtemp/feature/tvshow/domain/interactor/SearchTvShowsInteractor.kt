package dev.arch3rtemp.feature.tvshow.domain.interactor

import dev.arch3rtemp.common.util.Resource
import dev.arch3rtemp.feature.tvshow.domain.model.TvShow
import dev.arch3rtemp.feature.tvshow.domain.repository.TvShowRepository

class SearchTvShowsInteractor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(query: String, page: Int): Resource<List<TvShow>> {
        return tvShowRepository.searchTvShows(query, page)
    }
}