package dev.arch3rtemp.tvshows.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.arch3rtemp.feature.tvshow.domain.interactor.GetPopularTvShowsInteractor
import dev.arch3rtemp.feature.tvshow.domain.interactor.GetSimilarTvShowsInteractor
import dev.arch3rtemp.feature.tvshow.domain.interactor.SearchTvShowsInteractor
import dev.arch3rtemp.feature.tvshow.domain.repository.TvShowRepository

@Module
@InstallIn(ViewModelComponent::class)
class InteractorModule {

    @Provides
    @ViewModelScoped
    fun provideGetPopularTvShowInteractor(tvShowsRepository: TvShowRepository): GetPopularTvShowsInteractor {
        return GetPopularTvShowsInteractor(tvShowsRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSearchTvShowInteractor(tvShowsRepository: TvShowRepository): SearchTvShowsInteractor {
        return SearchTvShowsInteractor(tvShowsRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSimilarTvShowInteractor(tvShowRepository: TvShowRepository): GetSimilarTvShowsInteractor {
        return GetSimilarTvShowsInteractor(tvShowRepository)
    }
}