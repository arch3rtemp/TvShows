package dev.arch3rtemp.feature.tvshow.data.local.source

import dev.arch3rtemp.feature.tvshow.data.local.dao.TvShowDao
import dev.arch3rtemp.feature.tvshow.data.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowLocalSourceImpl @Inject constructor(
    private val tvShowDao: TvShowDao
) : TvShowLocalSource {
    override suspend fun savePopularTvShows(tvShows: List<TvShowEntity>) {
        tvShowDao.insertTvShows(tvShows)
    }

    override suspend fun getPopularTvShows(): List<TvShowEntity> {
        return tvShowDao.selectTvShows()
    }

    override suspend fun observeTvShows(): Flow<List<TvShowEntity>> {
        return tvShowDao.observeTvShows()
    }

    override suspend fun getTvShow(id: Int): TvShowEntity {
        return tvShowDao.selectTvShow(id)
    }
}