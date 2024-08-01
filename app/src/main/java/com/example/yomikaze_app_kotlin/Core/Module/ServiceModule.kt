package com.example.yomikaze_app_kotlin.Core.Module

import com.example.yomikaze_app_kotlin.Data.DataSource.API.AuthApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ChapterApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ChapterCommentApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.CoinShopApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicCommentApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.HistoryApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ImageApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.LibraryApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.LibraryCategoryApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.NotificationApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.PageApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ProfileApiService
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
    fun provideAuthApiService(@MainRetrofit mainRetrofit: Retrofit): AuthApiService {
        return mainRetrofit.create(AuthApiService::class.java)
    }

    /**
     * Todo: Provide the ComicApiService
     */
    @Provides
    @Singleton
    fun provideComicApiService(@MainRetrofit mainRetrofit: Retrofit): ComicApiService {
        return mainRetrofit.create(ComicApiService::class.java)
    }

    /**
     * Todo: Provide the ChapterApiService
     */
    @Provides
    @Singleton
    fun provideChapterApiService(@MainRetrofit mainRetrofit: Retrofit): ChapterApiService {
        return mainRetrofit.create(ChapterApiService::class.java)
    }

    /**
     * Todo: Provide the PageApiService
     */
    @Provides
    @Singleton
    fun providePageApiService(@MainRetrofit mainRetrofit: Retrofit): PageApiService {
        return mainRetrofit.create(PageApiService::class.java)
    }


    /**
     * Todo: Provide the LibraryApiService
     */
    @Provides
    @Singleton
    fun provideLibraryApiService(@MainRetrofit mainRetrofit: Retrofit): LibraryApiService {
        return mainRetrofit.create(LibraryApiService::class.java)
    }

    /**
     * Todo: Provide the LibraryCategoryApiService
     */
    @Provides
    @Singleton
    fun provideLibraryCategoryApiService(@MainRetrofit mainRetrofit: Retrofit): LibraryCategoryApiService {
        return mainRetrofit.create(LibraryCategoryApiService::class.java)
    }

    /**
     * Todo: Provide the HistoryApiService
     */
    @Provides
    @Singleton
    fun provideHistoryApiService(@MainRetrofit mainRetrofit: Retrofit): HistoryApiService {
        return mainRetrofit.create(HistoryApiService::class.java)
    }

    /**
     * Todo: Provide the CoinSopApiService
     */
    @Provides
    @Singleton
    fun provideCoinSopApiService(@MainRetrofit mainRetrofit: Retrofit): CoinShopApiService {
        return mainRetrofit.create(CoinShopApiService::class.java)
    }

    /**
     * Todo: Provide the Comic Comment Api Service
     */
    @Provides
    @Singleton
    fun provideComicCommentApiService(@MainRetrofit mainRetrofit: Retrofit): ComicCommentApiService {
        return mainRetrofit.create(ComicCommentApiService::class.java)
    }

    /**
     * Todo: Provide the Chapter Comment Api Service
     */
    @Provides
    @Singleton
    fun provideChapterCommentApiService(@MainRetrofit mainRetrofit: Retrofit): ChapterCommentApiService {
        return mainRetrofit.create(ChapterCommentApiService::class.java)
    }


    /**
     * Todo: Provide the ProfileApiService
     */
    @Provides
    @Singleton
    fun provideProfileApiService(@MainRetrofit mainRetrofit: Retrofit): ProfileApiService {
        return mainRetrofit.create(ProfileApiService::class.java)
    }

    /**
     * TODO: Provide the NotificationApiService
     *
     */
    @Provides
    @Singleton
    fun provideNotificationApiService(@MainRetrofit mainRetrofit: Retrofit): NotificationApiService {
        return mainRetrofit.create(NotificationApiService::class.java)
    }

    //image retrofit
    //image api service
    @Provides
    @Singleton
    fun provideImageApiService(@ImageRetrofit imageRetrofit: Retrofit): ImageApiService {
        return imageRetrofit.create(ImageApiService::class.java)
    }

}