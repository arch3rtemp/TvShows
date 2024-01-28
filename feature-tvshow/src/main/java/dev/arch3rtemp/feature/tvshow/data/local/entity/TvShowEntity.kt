package dev.arch3rtemp.feature.tvshow.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_table")
data class TvShowEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val name: String?,
    val adult: Boolean?,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "genre_ids")
    val genreIds: List<Int>?,
    @ColumnInfo(name = "origin_country")
    val originCountry: List<String>?,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String?,
    @ColumnInfo(name = "original_name")
    val originalName: String?,
    val overview: String?,
    val popularity: Double?,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "first_air_date")
    val firstAirDate: String?,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double?,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int?
)