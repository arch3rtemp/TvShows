package dev.arch3rtemp.feature.tvshow.data.mapper

import dev.arch3rtemp.common.mapper.Mapper
import dev.arch3rtemp.feature.tvshow.data.local.entity.TvShowEntity
import dev.arch3rtemp.feature.tvshow.domain.model.TvShow
import javax.inject.Inject

class TvShowEntityDomainMapper @Inject constructor() : Mapper<TvShowEntity, TvShow> {
    override fun from(i: TvShowEntity?): TvShow {
        return TvShow(
            id = i?.id ?: Mapper.ABSENT_NUMERAL,
            name = i?.name.orEmpty(),
            adult = i?.adult ?: false,
            backdropPath = i?.backdropPath.orEmpty(),
            genreIds = i?.genreIds ?: emptyList(),
            originCountry = i?.originCountry ?: emptyList(),
            originalLanguage = i?.originalLanguage.orEmpty(),
            originalName = i?.originalName.orEmpty(),
            overview = i?.overview.orEmpty(),
            popularity = i?.popularity ?: Mapper.ABSENT_NUMERAL.toDouble(),
            posterPath = i?.posterPath.orEmpty(),
            firstAirDate = i?.firstAirDate.orEmpty(),
            voteAverage = i?.voteAverage ?: Mapper.ABSENT_NUMERAL.toDouble(),
            voteCount = i?.voteCount ?: Mapper.ABSENT_NUMERAL
        )
    }

    override fun to(o: TvShow?): TvShowEntity {
        return TvShowEntity(
            id = o?.id,
            name = o?.name,
            adult = o?.adult,
            backdropPath = o?.backdropPath,
            genreIds = o?.genreIds,
            originCountry = o?.originCountry,
            originalLanguage = o?.originalLanguage,
            originalName = o?.originalName,
            overview = o?.overview,
            popularity = o?.popularity,
            posterPath = o?.posterPath,
            firstAirDate = o?.firstAirDate,
            voteAverage = o?.voteAverage,
            voteCount = o?.voteCount
        )
    }
}