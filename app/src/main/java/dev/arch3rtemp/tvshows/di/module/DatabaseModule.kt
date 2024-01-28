package dev.arch3rtemp.tvshows.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.arch3rtemp.feature.tvshow.data.local.dao.TvShowDao
import dev.arch3rtemp.tvshows.storage.converter.RoomJsonConverter
import dev.arch3rtemp.tvshows.storage.db.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context, roomJsonConverter: RoomJsonConverter) = Room
        .databaseBuilder(appContext, AppDatabase::class.java,"db")
        .fallbackToDestructiveMigration()
        .addTypeConverter(roomJsonConverter)
        .build()

    @Singleton
    @Provides
    fun providePokeDao(database: AppDatabase): TvShowDao {
        return database.tvShowDao()
    }
}