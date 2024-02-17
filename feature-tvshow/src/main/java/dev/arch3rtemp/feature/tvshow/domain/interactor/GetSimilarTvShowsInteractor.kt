package dev.arch3rtemp.feature.tvshow.domain.interactor

import dev.arch3rtemp.feature.tvshow.domain.model.TvShow
import dev.arch3rtemp.feature.tvshow.domain.repository.TvShowRepository
import javax.inject.Inject

class GetSimilarTvShowsInteractor @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(seriesId: String, page: Int): Result<List<TvShow>> {
        return tvShowRepository.getSimilarTvShows(seriesId, page)
    }
}