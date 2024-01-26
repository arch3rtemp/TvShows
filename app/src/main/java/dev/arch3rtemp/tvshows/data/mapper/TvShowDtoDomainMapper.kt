package dev.arch3rtemp.tvshows.data.mapper

import dev.arch3rtemp.tvshows.common.Mapper
import dev.arch3rtemp.tvshows.common.Mapper.Companion.ABSENT_NUMERAL
import dev.arch3rtemp.tvshows.data.remote.response.TvShowDto
import dev.arch3rtemp.tvshows.domain.model.TvShow
import javax.inject.Inject

class TvShowDtoDomainMapper @Inject constructor() : Mapper<TvShowDto, TvShow> {
    override fun from(i: TvShowDto?): TvShow {
        return TvShow(
            id = i?.id ?: ABSENT_NUMERAL,
            name = i?.name.orEmpty(),
            adult = i?.adult ?: false,
            backdropPath = i?.backdropPath.orEmpty(),
            genreIds = i?.genreIds ?: emptyList(),
            originCountry = i?.originCountry ?: emptyList(),
            originalLanguage = i?.originalLanguage.orEmpty(),
            originalName = i?.originalName.orEmpty(),
            overview = i?.overview.orEmpty(),
            popularity = i?.popularity ?: ABSENT_NUMERAL.toDouble(),
            posterPath = i?.posterPath.orEmpty(),
            firstAirDate = i?.firstAirDate.orEmpty(),
            voteAverage = i?.voteAverage ?: ABSENT_NUMERAL.toDouble(),
            voteCount = i?.voteCount ?: ABSENT_NUMERAL
        )
    }

    override fun to(o: TvShow?): TvShowDto {
        return TvShowDto(
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