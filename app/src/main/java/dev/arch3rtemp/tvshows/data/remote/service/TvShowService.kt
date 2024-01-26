package dev.arch3rtemp.tvshows.data.remote.service

import dev.arch3rtemp.tvshows.data.remote.response.TvShowResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowService {

    @GET("tv/popular")
    suspend fun fetchPopularTvShows(@Query("page") page: Int): TvShowResponse

    @GET("search/tv")
    suspend fun searchTvShows(@Query("query") query: String, @Query("page") page: Int): TvShowResponse
}