package com.example.yomikaze_app_kotlin.Core.Module

import com.example.yomikaze_app_kotlin.Domain.Repository.AuthRepository
import com.example.yomikaze_app_kotlin.Domain.Repository.ComicRepository
import com.example.yomikaze_app_kotlin.Domain.UseCase.GetHotComicBannerUseCase
import com.example.yomikaze_app_kotlin.Domain.UseCase.LoginUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    /**
     * Todo: Provide the LoginUseCase
     */
    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUC {
        return LoginUC(authRepository)
    }

    @Provides
    @Singleton
    fun provideGetHotComicBannerUseCase(comicRepository: ComicRepository): GetHotComicBannerUseCase {
        return GetHotComicBannerUseCase(comicRepository)
    }
}