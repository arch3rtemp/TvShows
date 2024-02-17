package dev.arch3rtemp.feature.tvshow.domain.interactor

import dev.arch3rtemp.feature.tvshow.domain.model.TvShow
import dev.arch3rtemp.feature.tvshow.domain.repository.TvShowRepository

class GetSelectedTvShowInteractor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(id: Int): Result<TvShow> {
        return tvShowRepository.getTvShow(id)
    }
}