package dev.arch3rtemp.feature.tvshow.domain.interactor

import dev.arch3rtemp.common.util.Resource
import dev.arch3rtemp.feature.tvshow.domain.model.TvShow
import dev.arch3rtemp.feature.tvshow.domain.repository.TvShowRepository

class GetSelectedTvShowInteractor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(id: Int): Resource<TvShow> {
        return tvShowRepository.getTvShow(id)
    }
}