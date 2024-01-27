package dev.arch3rtemp.tvshows.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.arch3rtemp.common.mapper.Mapper
import dev.arch3rtemp.tvshows.data.mapper.TvShowDtoDomainMapper
import dev.arch3rtemp.tvshows.data.remote.response.TvShowDto
import dev.arch3rtemp.tvshows.domain.model.TvShow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    @Singleton
    abstract fun bindTvShowDtoDomainModel(mapper: TvShowDtoDomainMapper): Mapper<TvShowDto, TvShow>
}