package dev.arch3rtemp.tvshows.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.arch3rtemp.feature.tvshow.data.repository.TvShowRepositoryImpl
import dev.arch3rtemp.feature.tvshow.domain.repository.TvShowRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTvShowRepository(tvShowRepository: TvShowRepositoryImpl): TvShowRepository
}