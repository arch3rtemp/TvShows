package dev.arch3rtemp.tvshows.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.arch3rtemp.tvshows.domain.interactor.GetPopularTvShowsInteractor
import dev.arch3rtemp.tvshows.domain.interactor.SearchTvShowsInteractor
import dev.arch3rtemp.tvshows.domain.repository.TvShowRepository

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
}