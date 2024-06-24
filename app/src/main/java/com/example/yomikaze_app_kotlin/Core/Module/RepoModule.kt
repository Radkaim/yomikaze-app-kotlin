package com.example.yomikaze_app_kotlin.Core.Module

import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Data.DataSource.API.AuthApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicApiService
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.AuthRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.ComicRepositoryImpl
import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    /**
     * Todo: Provide the AuthRepository
     */
    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApiService,
        appPreference: AppPreference
    ): AuthRepository {
        return AuthRepositoryImpl(api, appPreference)
    }

    /**
     * Todo: Provide the ComicRepository
     */
    @Provides
    @Singleton
    fun provideComicRepository(
        api: ComicApiService,
    ): ComicRepository {
        return ComicRepositoryImpl(api)
    }

}