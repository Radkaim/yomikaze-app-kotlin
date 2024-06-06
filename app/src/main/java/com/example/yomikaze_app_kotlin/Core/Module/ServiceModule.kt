package com.example.yomikaze_app_kotlin.Core.Module

import com.example.yomikaze_app_kotlin.Data.DataSource.API.AuthApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicApiService
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
}