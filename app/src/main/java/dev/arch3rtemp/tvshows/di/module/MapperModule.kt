package dev.arch3rtemp.tvshows.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.arch3rtemp.common.exception.ErrorMapper
import dev.arch3rtemp.common.mapper.Mapper
import dev.arch3rtemp.common_data.exception.DefaultErrorMapper
import dev.arch3rtemp.feature.tvshow.data.local.entity.TvShowEntity
import dev.arch3rtemp.feature.tvshow.data.mapper.TvShowDtoDomainMapper
import dev.arch3rtemp.feature.tvshow.data.mapper.TvShowEntityDomainMapper
import dev.arch3rtemp.feature.tvshow.data.remote.response.TvShowDto
import dev.arch3rtemp.feature.tvshow.domain.model.TvShow
import dev.arch3rtemp.feature.tvshow.ui.mapper.TvShowUiDomainMapper
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    @Singleton
    abstract fun bindTvShowDtoDomainModel(mapper: TvShowDtoDomainMapper): Mapper<TvShowDto, TvShow>

    @Binds
    @Singleton
    abstract fun bindTvShowEntityDomainModel(mapper: TvShowEntityDomainMapper): Mapper<TvShowEntity, TvShow>

    @Binds
    @Singleton
    abstract fun bindTvShowUiDomainModel(mapper: TvShowUiDomainMapper): Mapper<TvShowUi, TvShow>

    @Binds
    abstract fun bindErrorMapper(errorMapper: DefaultErrorMapper): ErrorMapper
}