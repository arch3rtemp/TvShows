package dev.arch3rtemp.tvshows.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class TvShowResponse(
    val page : Int? = null,
    val total_pages : Int? = null,
    val total_results : Int? = null,
    val results : List<TvShowDto> = listOf()
)
