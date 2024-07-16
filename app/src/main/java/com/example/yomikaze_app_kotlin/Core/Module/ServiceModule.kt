package com.example.yomikaze_app_kotlin.Core.Module

import com.example.yomikaze_app_kotlin.Data.DataSource.API.AuthApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ChapterApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.CoinShopApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicCommentApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.HistoryApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.LibraryApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.LibraryCategoryApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.PageApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    /**
     * Todo: Provide the AuthApiService
     */
    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    /**
     * Todo: Provide the ComicApiService
     */
    @Provides
    @Singleton
    fun provideComicApiService(retrofit: Retrofit): ComicApiService {
        return retrofit.create(ComicApiService::class.java)
    }

    /**
     * Todo: Provide the ChapterApiService
     */
    @Provides
    @Singleton
    fun provideChapterApiService(retrofit: Retrofit): ChapterApiService {
        return retrofit.create(ChapterApiService::class.java)
    }

    /**
     * Todo: Provide the PageApiService
     */
    @Provides
    @Singleton
    fun providePageApiService(retrofit: Retrofit): PageApiService {
        return retrofit.create(PageApiService::class.java)
    }

    /**
     * Todo: Provide the LibraryApiService
     */
    @Provides
    @Singleton
    fun provideLibraryApiService(retrofit: Retrofit): LibraryApiService {
        return retrofit.create(LibraryApiService::class.java)
    }

    /**
     * Todo: Provide the LibraryCategoryApiService
     */
    @Provides
    @Singleton
    fun provideLibraryCategoryApiService(retrofit: Retrofit): LibraryCategoryApiService {
        return retrofit.create(LibraryCategoryApiService::class.java)
    }

    /**
     * Todo: Provide the HistoryApiService
     */
    @Provides
    @Singleton
    fun provideHistoryApiService(retrofit: Retrofit): HistoryApiService {
        return retrofit.create(HistoryApiService::class.java)
    }

    /**
     * Todo: Provide the CoinSopApiService
     */
    @Provides
    @Singleton
    fun provideCoinSopApiService(retrofit: Retrofit): CoinShopApiService {
        return retrofit.create(CoinShopApiService::class.java)
    }

    /**
     * Todo: Provide the Comic Comment Api Service
     */
    @Provides
    @Singleton
    fun provideComicCommentApiService(retrofit: Retrofit): ComicCommentApiService {
        return retrofit.create(ComicCommentApiService::class.java)
    }

}