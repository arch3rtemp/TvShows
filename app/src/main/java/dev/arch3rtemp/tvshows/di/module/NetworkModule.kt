package dev.arch3rtemp.tvshows.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.arch3rtemp.tvshows.BuildConfig
import dev.arch3rtemp.tvshows.storage.converter.RoomJsonConverter
import dev.arch3rtemp.feature.tvshow.data.remote.service.TvShowService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val CONNECTION_TIMEOUT = 15000L
    private const val DEFAULT_LANG = "en-EN"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRoomJsonConverter(moshi: Moshi): RoomJsonConverter {
        return RoomJsonConverter(moshi)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor { chain ->

                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("language", DEFAULT_LANG)
                    .build()
                val requestBuilder = original.newBuilder()
                    .url(url)
                    .addHeader("Authorization", "Bearer ${BuildConfig.BEARER_TOKEN}")
                    .build()

                chain.proceed(requestBuilder)
            }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideTvShowApi(retrofit: Retrofit): TvShowService = retrofit.create(TvShowService::class.java)
}