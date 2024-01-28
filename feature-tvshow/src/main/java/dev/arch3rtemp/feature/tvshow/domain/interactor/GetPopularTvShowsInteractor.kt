package dev.arch3rtemp.feature.tvshow.domain.interactor

import dev.arch3rtemp.common.util.Resource
import dev.arch3rtemp.feature.tvshow.domain.model.TvShow
import dev.arch3rtemp.feature.tvshow.domain.repository.TvShowRepository

class GetPopularTvShowsInteractor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(page: Int): Resource<List<TvShow>> {
        return tvShowRepository.getPopularTvShows(page)
    }
}