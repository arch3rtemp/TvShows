package dev.arch3rtemp.feature.tvshow.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowUi(
    val id: Int,
    val name: String,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val firstAirDate: String,
    val voteAverage: Double,
    val voteCount: Int
) : Parcelable
