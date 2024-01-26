package dev.arch3rtemp.tvshows.data.repository

import dev.arch3rtemp.tvshows.common.Resource
import dev.arch3rtemp.tvshows.data.mapper.TvShowDtoDomainMapper
import dev.arch3rtemp.tvshows.data.remote.source.TvShowRemoteSource
import dev.arch3rtemp.tvshows.domain.model.TvShow
import dev.arch3rtemp.tvshows.domain.repository.TvShowRepository
import retrofit2.HttpException
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val tvShowRemoteSource: TvShowRemoteSource,
    private val tvShowDtoDomainMapper: TvShowDtoDomainMapper
) : TvShowRepository {
    override suspend fun getPopularTvShows(page: Int): Resource<List<TvShow>> {
        return try {

            val response = tvShowRemoteSource.fetchPopularTvShows(page)
            val tvShowList = response.results

            Resource.Success(tvShowDtoDomainMapper.fromList(tvShowList))
        } catch (e: HttpException) {
            Resource.Error(e.code(), e.message())
        } catch (e: Exception) {
            Resource.Exception(e)
        }
    }
}