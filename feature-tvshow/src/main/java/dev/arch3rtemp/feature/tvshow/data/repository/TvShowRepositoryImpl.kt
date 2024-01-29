package dev.arch3rtemp.feature.tvshow.data.repository

import dev.arch3rtemp.common.exception.NoInternetException
import dev.arch3rtemp.common.util.Resource
import dev.arch3rtemp.feature.tvshow.data.local.source.TvShowLocalSource
import dev.arch3rtemp.feature.tvshow.data.mapper.TvShowDtoDomainMapper
import dev.arch3rtemp.feature.tvshow.data.mapper.TvShowEntityDomainMapper
import dev.arch3rtemp.feature.tvshow.data.remote.source.TvShowRemoteSource
import dev.arch3rtemp.feature.tvshow.domain.model.TvShow
import dev.arch3rtemp.feature.tvshow.domain.repository.TvShowRepository
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val tvShowRemoteSource: TvShowRemoteSource,
    private val tvShowLocalSource: TvShowLocalSource,
    private val tvShowDtoDomainMapper: TvShowDtoDomainMapper,
    private val tvShowEntityDomainMapper: TvShowEntityDomainMapper
) : TvShowRepository {
    override suspend fun getPopularTvShows(page: Int): Resource<List<TvShow>> {
        return try {

            val response = tvShowRemoteSource.fetchPopularTvShows(page)
            val tvShowList = response.results

            Resource.Success(tvShowDtoDomainMapper.fromList(tvShowList))
        } catch (e: HttpException) {
            Resource.Error(e.code(), e.message())
        } catch (e: Exception) {
            when (e) {

                is UnknownHostException, is SocketTimeoutException, is ConnectException ->
                    Resource.Exception(NoInternetException(e.message, e.cause))

                else -> Resource.Exception(e)
            }
        }
    }

    override suspend fun searchTvShows(query: String, page: Int): Resource<List<TvShow>> {
        return try {
            val response = tvShowRemoteSource.searchTvShows(query, page)
            val tvShowList = response.results

            Resource.Success(tvShowDtoDomainMapper.fromList(tvShowList))
        } catch (e: HttpException) {
            Resource.Error(e.code(), e.message())
        } catch (e: Exception) {
            Resource.Exception(e)
        }
    }

    override suspend fun getTvShow(id: Int): Resource<TvShow> {
        val tvShow = tvShowEntityDomainMapper.from(tvShowLocalSource.getTvShow(id))
        return Resource.Success(tvShow)
    }

    override suspend fun getSimilarTvShows(seriesId: String, page: Int): Resource<List<TvShow>> {
        return try {
            val response = tvShowRemoteSource.fetchSimilarTvShows(seriesId, page)
            val tvShowList = response.results

            Resource.Success(tvShowDtoDomainMapper.fromList(tvShowList))
        } catch (e: HttpException) {
            Resource.Error(e.code(), e.message())
        } catch (e: Exception) {
            Resource.Exception(e)
        }
    }
}