package com.example.yomikaze_app_kotlin.Core.Module

import android.content.Context
import androidx.room.Room
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ComicDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.YomikazeDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "yomikaze_db_version_1"
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): YomikazeDB {
        return Room.databaseBuilder(
            appContext,
            YomikazeDB::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideComicDAO(db: YomikazeDB): ComicDao {
        return db.comicDao()
    }

}