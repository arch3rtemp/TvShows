package dev.arch3rtemp.feature.tvshow.ui.mapper

import dev.arch3rtemp.common.mapper.Mapper
import dev.arch3rtemp.feature.tvshow.domain.model.TvShow
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi
import javax.inject.Inject

class TvShowUiDomainMapper @Inject constructor() : Mapper<TvShowUi, TvShow> {
    override fun from(i: TvShowUi?): TvShow {
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

    override fun to(o: TvShow?): TvShowUi {
        return TvShowUi(
            id = o?.id ?: Mapper.ABSENT_NUMERAL,
            name = o?.name.orEmpty(),
            adult = o?.adult ?: false,
            backdropPath = o?.backdropPath.orEmpty(),
            genreIds = o?.genreIds ?: emptyList(),
            originCountry = o?.originCountry ?: emptyList(),
            originalLanguage = o?.originalLanguage.orEmpty(),
            originalName = o?.originalName.orEmpty(),
            overview = o?.overview.orEmpty(),
            popularity = o?.popularity ?: Mapper.ABSENT_NUMERAL.toDouble(),
            posterPath = o?.posterPath.orEmpty(),
            firstAirDate = o?.firstAirDate.orEmpty(),
            voteAverage = o?.voteAverage ?: Mapper.ABSENT_NUMERAL.toDouble(),
            voteCount = o?.voteCount ?: Mapper.ABSENT_NUMERAL
        )
    }
}