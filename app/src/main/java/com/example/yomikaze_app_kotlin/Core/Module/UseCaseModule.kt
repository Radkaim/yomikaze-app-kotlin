package com.example.yomikaze_app_kotlin.Core.Module

import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.HistoryRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ImageRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryCategoryRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import com.example.yomikaze_app_kotlin.Domain.UseCases.Auth.LoginUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Auth.LoginWithGoogleUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Auth.LogoutUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DownloadUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.DeleteAllHistoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.DeleteHistoryRecordUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.GetHistoriesUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.UpdateLastReadPageUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.CreateLibraryCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.DeleteCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetComicsInCateUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetLibraryCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.SearchInLibraryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.UpdateCateNameUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.AddComicToCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.FollowComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetComicDetailsFromApiUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetListChaptersByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.RatingComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.SearchComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetPagesByChapterNumberOfComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetUserInfoUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByCommentsRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByFollowRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByRatingRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByViewRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetHotComicBannerUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    /**
     *-------------------------------------------------------------------------
     * TODO Authentication Use Cases
     */

    /**
     * Todo: Provide the LoginUseCase
     */
    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUC {
        return LoginUC(authRepository)
    }

    /**
     * Todo: Provide the LoginWithGoogleUseCase
     */
    @Provides
    @Singleton
    fun provideLoginWithGoogleUseCase(authRepository: AuthRepository): LoginWithGoogleUC {
        return LoginWithGoogleUC(authRepository)
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
     *-------------------------------------------------------------------------
     * TODO Comic Use Cases
     */

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
     * Todo: Provide the RatingComicUC
     */
    @Provides
    @Singleton
    fun provideRatingComicUC(comicRepository: ComicRepository): RatingComicUC {
        return RatingComicUC(comicRepository)
    }

    /**
     * Todo: Provide the follow comic use case
     */
    @Provides
    @Singleton
    fun provideFollowComicUC(comicRepository: ComicRepository): FollowComicUC {
        return FollowComicUC(comicRepository)
    }


    /**
     *-------------------------------------------------------------------------
     * TODO Download Use Cases
     */

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
     *-------------------------------------------------------------------------
     * TODO LibraryCategory Use Cases
     */

    /**
     * Todo: Provide the AddComicToCategoryUC
     */
    @Provides
    @Singleton
    fun provideAddComicToCategoryUC(libraryCategoryRepository: LibraryCategoryRepository): AddComicToCategoryUC {
        return AddComicToCategoryUC(libraryCategoryRepository)
    }

    /**
     * Todo: Provide the create category in library
     */
    @Provides
    @Singleton
    fun provideCreateCategoryInLibraryUC(libraryCategoryRepository: LibraryCategoryRepository): CreateLibraryCategoryUC {
        return CreateLibraryCategoryUC(libraryCategoryRepository)
    }

    /**
     * Todo: Provide the get category in library
     */
    @Provides
    @Singleton
    fun provideGetCategoryInLibraryUC(libraryCategoryRepository: LibraryCategoryRepository): GetLibraryCategoryUC {
        return GetLibraryCategoryUC(libraryCategoryRepository)
    }

    /**
     * Todo: Provide the get comics in category
     */
    @Provides
    @Singleton
    fun provideGetComicsInCateUC(libraryCategoryRepository: LibraryCategoryRepository): GetComicsInCateUC {
        return GetComicsInCateUC(libraryCategoryRepository)
    }

    /**
     * Todo: Provide the delete category in library
     */
    @Provides
    @Singleton
    fun provideDeleteCategoryInLibraryUC(libraryCategoryRepository: LibraryCategoryRepository): DeleteCategoryUC {
        return DeleteCategoryUC(libraryCategoryRepository)
    }

    /**
     * Todo: Provide the update category name in library
     */
    @Provides
    @Singleton
    fun provideUpdateCategoryNameInLibraryUC(libraryCategoryRepository: LibraryCategoryRepository): UpdateCateNameUC {
        return UpdateCateNameUC(libraryCategoryRepository)
    }


    /**
     *-------------------------------------------------------------------------
     * TODO Chapter Use Cases
     */


    /**
     * Todo: Provide the GetListChaptersByComicIdUC
     */
    @Provides
    @Singleton
    fun provideGetListChaptersByComicIdUC(chapterRepository: ChapterRepository): GetListChaptersByComicIdUC {
        return GetListChaptersByComicIdUC(chapterRepository)
    }


    /**
     *-------------------------------------------------------------------------
     * TODO Page Use Cases
     */

    /**
     * Todo: Provide the GetPagesByChapterNumberOfComic
     */
    @Provides
    @Singleton
    fun provideGetPagesByChapterNumberOfComicUC(pageRepository: PageRepository): GetPagesByChapterNumberOfComicUC {
        return GetPagesByChapterNumberOfComicUC(pageRepository)
    }


    /**
     *-------------------------------------------------------------------------
     * TODO Library Use Cases
     */
    @Provides
    @Singleton
    fun provideSearchComicInLibraryUC(libraryRepository: LibraryRepository): SearchInLibraryUC {
        return SearchInLibraryUC(libraryRepository)
    }



    /**
     *-------------------------------------------------------------------------
     * TODO History Use Cases
     */


    /**
     * Todo: Provide the getHistoryUseCase
     */
    @Provides
    @Singleton
    fun provideGetHistoryUseCase(historyRepository: HistoryRepository): GetHistoriesUC {
        return GetHistoriesUC(historyRepository)
    }

    /**
     * Todo: Provide the updateLastReadPageUseCase
     */
    @Provides
    @Singleton
    fun provideUpdateLastReadPageUseCase(historyRepository: HistoryRepository): UpdateLastReadPageUC {
        return UpdateLastReadPageUC(historyRepository)
    }

    /**
     * Todo: Provide the delete all history use case
     */
    @Provides
    @Singleton
    fun provideDeleteAllHistoryUseCase(historyRepository: HistoryRepository): DeleteAllHistoryUC {
        return DeleteAllHistoryUC(historyRepository)
    }

    /**
     * Todo: Provide the delete history record use case
     */
    @Provides
    @Singleton
    fun provideDeleteHistoryRecordUseCase(historyRepository: HistoryRepository): DeleteHistoryRecordUC {
        return DeleteHistoryRecordUC(historyRepository)
    }

}