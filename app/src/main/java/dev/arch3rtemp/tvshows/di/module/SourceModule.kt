package dev.arch3rtemp.tvshows.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.arch3rtemp.tvshows.data.remote.source.TvShowRemoteSource
import dev.arch3rtemp.tvshows.data.remote.source.TvShowRemoteSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    @Singleton
    abstract fun bindTvShowRemoteDataSource(tvShowRemoteSource: TvShowRemoteSourceImpl): TvShowRemoteSource
}