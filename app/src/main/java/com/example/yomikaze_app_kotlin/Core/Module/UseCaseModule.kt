package com.example.yomikaze_app_kotlin.Core.Module

import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ImageRepository
import com.example.yomikaze_app_kotlin.Domain.UseCases.DownloadUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetComicByCommentsRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetComicByFollowRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetComicByRatingRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetComicByViewRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetComicDetailsFromApiUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetHotComicBannerUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetUserInfoUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.LoginUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.LogoutUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.RatingComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.SearchComicUC
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

    /**
     * Todo: Provide the GetUserInfoUseCase
     */
    @Provides
    @Singleton
    fun provideGetUserInfoUseCase(authRepository: AuthRepository): GetUserInfoUC {
        return GetUserInfoUC(authRepository)
    }

    /**
     * Todo: Provide the logoutUseCase
     */
    @Provides
    @Singleton
    fun provideLogoutUseCase(authRepository: AuthRepository): LogoutUC {
        return LogoutUC(authRepository)
    }

    /**
     * Todo: Provide the GetHotComicBannerUseCase
     */
    @Provides
    @Singleton
    fun provideGetHotComicBannerUseCase(comicRepository: ComicRepository): GetHotComicBannerUC {
        return GetHotComicBannerUC(comicRepository)
    }


    /**
     * Todo: Provide the SearchComicUseCase
     */
    @Provides
    @Singleton
    fun provideSearchComicUseCase(comicRepository: ComicRepository): SearchComicUC {
        return SearchComicUC(comicRepository)
    }

    /**
     * Todo: Provide the GetComicByViewRankingUseCase
     */
    @Provides
    @Singleton
    fun provideGetComicByViewRankingUseCase(comicRepository: ComicRepository): GetComicByViewRankingUC {
        return GetComicByViewRankingUC(comicRepository)
    }

    /**
     * Todo: Provide the GetComicByRatingRankingUseCase
     */
    @Provides
    @Singleton
    fun provideGetComicByRatingRankingUseCase(comicRepository: ComicRepository): GetComicByRatingRankingUC {
        return GetComicByRatingRankingUC(comicRepository)
    }

    /**
     * Todo: Provide the GetComicByCommentsRankingUseCase
     */
    @Provides
    @Singleton
    fun provideGetComicByCommentRankingUseCase(comicRepository: ComicRepository): GetComicByCommentsRankingUC {
        return GetComicByCommentsRankingUC(comicRepository)
    }

    /**
     * Todo: Provide the GetComicByFollowRankingUseCase
     */
    @Provides
    @Singleton
    fun provideGetComicByFollowRankingUseCase(comicRepository: ComicRepository): GetComicByFollowRankingUC {
        return GetComicByFollowRankingUC(comicRepository)
    }

    /**
     * Todo: Provide the GetComicDetailsFromApiUC
     */
    @Provides
    @Singleton
    fun provideGetComicDetailsFromApiUC(comicRepository: ComicRepository): GetComicDetailsFromApiUC {
        return GetComicDetailsFromApiUC(comicRepository)
    }


    /**
     * Todo: Provide the DownloadDBUC
     */
    @Provides
    @Singleton
    fun provideDownloadDBUC(
        comicRepository: ComicRepository,
        imageRepository: ImageRepository
    ): DownloadUC {
        return DownloadUC(comicRepository, imageRepository)
    }

    /**
     * Todo: Provide the RatingComicUC
     */
    @Provides
    @Singleton
    fun provideRatingComicUC(comicRepository: ComicRepository): RatingComicUC {
        return RatingComicUC(comicRepository)
    }
}