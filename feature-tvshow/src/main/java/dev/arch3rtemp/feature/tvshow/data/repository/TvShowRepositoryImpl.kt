package dev.arch3rtemp.feature.tvshow.data.repository

import dev.arch3rtemp.common.exception.ErrorMapper
import dev.arch3rtemp.common.exception.mapError
import dev.arch3rtemp.feature.tvshow.data.local.source.TvShowLocalSource
import dev.arch3rtemp.feature.tvshow.data.mapper.TvShowDtoDomainMapper
import dev.arch3rtemp.feature.tvshow.data.mapper.TvShowEntityDomainMapper
import dev.arch3rtemp.feature.tvshow.data.remote.source.TvShowRemoteSource
import dev.arch3rtemp.feature.tvshow.domain.model.TvShow
import dev.arch3rtemp.feature.tvshow.domain.repository.TvShowRepository
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val tvShowRemoteSource: TvShowRemoteSource,
    private val tvShowLocalSource: TvShowLocalSource,
    private val tvShowDtoDomainMapper: TvShowDtoDomainMapper,
    private val tvShowEntityDomainMapper: TvShowEntityDomainMapper,
    private val errorMapper: ErrorMapper
) : TvShowRepository {

    override suspend fun getPopularTvShows(page: Int):Result<List<TvShow>> = runCatching {
        val response = tvShowRemoteSource.fetchPopularTvShows(page)
        val tvShowList = response.results
        tvShowDtoDomainMapper.fromList(tvShowList)
    }.mapError(errorMapper::invoke)

    override suspend fun searchTvShows(query: String, page: Int):Result<List<TvShow>> = runCatching {
        val response = tvShowRemoteSource.searchTvShows(query, page)
        val tvShowList = response.results
        tvShowDtoDomainMapper.fromList(tvShowList)
    }.mapError(errorMapper::invoke)

    override suspend fun getTvShow(id: Int): Result<TvShow> = runCatching {
        tvShowEntityDomainMapper.from(tvShowLocalSource.getTvShow(id))
    }

    override suspend fun getSimilarTvShows(seriesId: String, page: Int): Result<List<TvShow>> = runCatching {
        val response = tvShowRemoteSource.fetchSimilarTvShows(seriesId, page)
        val tvShowList = response.results

        tvShowDtoDomainMapper.fromList(tvShowList)
    }.mapError(errorMapper::invoke)
}