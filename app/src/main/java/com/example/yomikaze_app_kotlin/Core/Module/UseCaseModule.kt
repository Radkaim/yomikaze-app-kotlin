package com.example.yomikaze_app_kotlin.Core.Module

import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.CoinShopRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicCommentRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.DownloadPageRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.HistoryRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ImageRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryCategoryRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ProfileRepository
import com.example.yomikaze_app_kotlin.Domain.UseCases.AdvancedSearchComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Auth.LoginUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Auth.LoginWithGoogleUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Auth.LogoutUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.DeleteChapterByChapterIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.DeletePageByComicIdAndChapterNumberDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.DownloadPagesOfChapterUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChapterByComicIdAndChapterNumberDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChapterByIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChaptersByComicIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetComicByIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetPageByComicIdAndChapterNumberDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.InsertChapterToDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.UpdateTotalMbsOfComicDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DeleteComicByIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DownloadComicDetailUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.GetAllComicInDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.DeleteAllHistoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.DeleteHistoryRecordUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.GetContinueReadingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.GetHistoriesUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.UpdateLastReadPageUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.AddComicToLibraryFirstTimeUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.AddComicToLibrarySecondTimeUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.CreateLibraryCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.DeleteCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetCategoriesOfComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetComicsInCateUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetDefaultComicsInLibraryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetLibraryCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.RemoveComicFromCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.SearchInLibraryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.UnfollowComicFromLibraryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.UpdateCateNameUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.CoinShop.GetCoinPricingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.CoinShop.GetPaymentSheetResponseUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.CoinShop.GetTransactionHistoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetChapterDetailUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetComicDetailsFromApiUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetListChaptersByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.RatingComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.SearchComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.UnlockAChapterUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.UnlockManyChaptersUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.DeleteComicCommentByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.GetAllComicCommentByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.GetAllReplyCommentByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.GetMainCommentByCommentIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.PostComicCommentByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.PostReplyCommentByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ReactComicCommentByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.UpdateComicCommentByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetPagesByChapterNumberOfComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetTagsUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetUserInfoUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Profile.GetProfileUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByCommentsRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByFollowRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByRatingRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByViewRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicWeeklyUC
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
     * TODO: get tags
     */
    @Provides
    @Singleton
    fun provideGetTagsUC(comicRepository: ComicRepository): GetTagsUC {
        return GetTagsUC(comicRepository)
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
     * TODO: Provide the advanced search comic use case
     */
    @Provides
    @Singleton
    fun provideAdvancedSearchComicUseCase(comicRepository: ComicRepository): AdvancedSearchComicUC {
        return AdvancedSearchComicUC(comicRepository)
    }

    /**
     * TODO: use for get comic in ranking weekly
     */
    @Provides
    @Singleton
    fun provideGetComicWeeklyUseCase(comicRepository: ComicRepository): GetComicWeeklyUC {
        return GetComicWeeklyUC(comicRepository)
    }

    /**
     * TODO  get comic by ID from API
     */
    @Provides
    @Singleton
    fun provideGetComicDetailsFromApiUseCase(comicRepository: ComicRepository): GetComicDetailsFromApiUC {
        return GetComicDetailsFromApiUC(comicRepository)
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
     * Todo: Provide the RatingComicUC
     */
    @Provides
    @Singleton
    fun provideRatingComicUC(comicRepository: ComicRepository): RatingComicUC {
        return RatingComicUC(comicRepository)
    }

    /**
     *---------------------------------------------------------------------------------------------
     * TODO Comic In DATABASE (Download) Use Cases
     */

    /**
     * Todo: Provide the get comic by ID from database
     */
    @Provides
    @Singleton
    fun provideGetComicByIdDBUseCase(comicRepository: ComicRepository): GetComicByIdDBUC {
        return GetComicByIdDBUC(comicRepository)
    }

    /**
     * Todo: Provide the download comic detail use case
     */
    @Provides
    @Singleton
    fun provideDownloadComicDetailDBlUC(
        comicRepository: ComicRepository,
        imageRepository: ImageRepository
    ): DownloadComicDetailUC {
        return DownloadComicDetailUC(comicRepository, imageRepository)
    }

    /**
     * TODO get all comic in database
     */
    @Provides
    @Singleton
    fun provideGetAllComicInDBUC(comicRepository: ComicRepository): GetAllComicInDBUC {
        return GetAllComicInDBUC(comicRepository)
    }

    /*
    * Todo: Provide the DeleteComicByIdDBUC
     */
    @Provides
    @Singleton
    fun provideDeleteComicByIdDBUC(comicRepository: ComicRepository): DeleteComicByIdDBUC {
        return DeleteComicByIdDBUC(comicRepository)
    }

    /**
     * TODO: Implement for insert chapter to database
     */
    @Provides
    @Singleton
    fun provideInsertChapterToDBUC(chapterRepository: ChapterRepository): InsertChapterToDBUC {
        return InsertChapterToDBUC(chapterRepository)
    }

    /**
     * TODO  Get chapter by id database
     */
    @Provides
    @Singleton
    fun provideGetChapterByIdDBUC(chapterRepository: ChapterRepository): GetChapterByIdDBUC {
        return GetChapterByIdDBUC(chapterRepository)
    }

    /**
     * TODO download pages of chapter
     */
    @Provides
    @Singleton
    fun provideDownloadPagesOfChapterUC(downloadPageRepository: DownloadPageRepository): DownloadPagesOfChapterUC {
        return DownloadPagesOfChapterUC(downloadPageRepository)
    }


    /**
     * TODo: Provide the get List chapter downloaded by comic ID from database
     */
    @Provides
    @Singleton
    fun provideGetChaptersByComicIdDBUC(chapterRepository: ChapterRepository): GetChaptersByComicIdDBUC {
        return GetChaptersByComicIdDBUC(chapterRepository)
    }

    /**
     * TODO: Provide the get chapter by comic id and chapter number in database
     */
    @Provides
    @Singleton
    fun provideGetChapterByComicIdAndChapterNumberDBUC(chapterRepository: ChapterRepository): GetChapterByComicIdAndChapterNumberDBUC {
        return GetChapterByComicIdAndChapterNumberDBUC(chapterRepository)
    }

    /**
     * TODO delete chapter by chapter id in database
     */
    @Provides
    @Singleton
    fun provideDeleteChapterByChapterIdDBUC(chapterRepository: ChapterRepository): DeleteChapterByChapterIdDBUC {
        return DeleteChapterByChapterIdDBUC(chapterRepository)
    }

    /**
     * TODO get page by comic id and chapter number in database
     */
    @Provides
    @Singleton
    fun provideGetPagesByChapterNumberOfComicDBUC(pageRepository: PageRepository): GetPageByComicIdAndChapterNumberDBUC {
        return GetPageByComicIdAndChapterNumberDBUC(pageRepository)
    }

    /**
     * TODO delete page by comicID and chapterNumber in database
     */
    @Provides
    @Singleton
    fun provideDeletePageByComicIdAndChapterNumberDBUC(pageRepository: PageRepository): DeletePageByComicIdAndChapterNumberDBUC {
        return DeletePageByComicIdAndChapterNumberDBUC(pageRepository)
    }

    /**
     * TODO: Update total Mbs of comic in database
     */
    @Provides
    @Singleton
    fun provideUpdateTotalMbsOfComicDBUC(comicRepository: ComicRepository): UpdateTotalMbsOfComicDBUC {
        return UpdateTotalMbsOfComicDBUC(comicRepository)
    }


    /**
     *-------------------------------------------------------------------------
     * TODO LibraryCategory Use Cases
     */


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
    fun provideGetComicsInCateUC(libraryRepository: LibraryRepository): GetComicsInCateUC {
        return GetComicsInCateUC(libraryRepository)
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
     * TODO get chapter detail
     */
    @Provides
    @Singleton
    fun provideGetChapterDetailUC(chapterRepository: ChapterRepository): GetChapterDetailUC {
        return GetChapterDetailUC(chapterRepository)
    }

    /**
     * TODO: provide UnlockAChapterUC
     */
    @Provides
    @Singleton
    fun provideUnlockAChapterUC(chapterRepository: ChapterRepository): UnlockAChapterUC {
        return UnlockAChapterUC(chapterRepository)
    }

    /**
     * TODO: provide unlock many chapters
     */
    @Provides
    @Singleton
    fun provideUnlockManyChaptersUC(chapterRepository: ChapterRepository): UnlockManyChaptersUC {
        return UnlockManyChaptersUC(chapterRepository)
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
     * Todo: Provide the AddComicToLibraryFirstTimeUC
     */
    @Provides
    @Singleton
    fun provideAddComicToLibraryFirstTimeUC(libraryRepository: LibraryRepository): AddComicToLibraryFirstTimeUC {
        return AddComicToLibraryFirstTimeUC(libraryRepository)
    }

    /**
     * Todo: Provide the AddComicToLibrarySecondTimeUC
     */
    @Provides
    @Singleton
    fun provideAddComicToLibrarySecondTimeUC(libraryRepository: LibraryRepository): AddComicToLibrarySecondTimeUC {
        return AddComicToLibrarySecondTimeUC(libraryRepository)
    }

    /**
     * Todo: Provide the RemoveComicFromCategoryUC
     */
    @Provides
    @Singleton
    fun provideRemoveComicFromCategoryUC(libraryRepository: LibraryRepository): RemoveComicFromCategoryUC {
        return RemoveComicFromCategoryUC(libraryRepository)
    }

    /**
     * TODO: Provide the UnfollowComicFromLibraryUC
     */
    @Provides
    @Singleton
    fun provideUnfollowComicFromLibraryUC(libraryRepository: LibraryRepository): UnfollowComicFromLibraryUC {
        return UnfollowComicFromLibraryUC(libraryRepository)
    }

    /**
     * TODO: Provide the GetCategoriesOfComicUC
     */
    @Provides
    @Singleton
    fun provideGetCategoriesOfComicUC(libraryRepository: LibraryRepository): GetCategoriesOfComicUC {
        return GetCategoriesOfComicUC(libraryRepository)
    }


    /**
     * TODO: Implement the function to get default comic that not in which category
     */
    @Provides
    @Singleton
    fun provideGetDefaultComicsInLibraryUC(libraryRepository: LibraryRepository): GetDefaultComicsInLibraryUC {
        return GetDefaultComicsInLibraryUC(libraryRepository)
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
     * TODO: Provide continue reading UC
     */
    @Provides
    @Singleton
    fun provideContinueReadingUC(historyRepository: HistoryRepository): GetContinueReadingUC {
        return GetContinueReadingUC(historyRepository)
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


    /**
     *-------------------------------------------------------------------------
     * TODO Coin Shop Use Cases
     */

    /**
     * TODO: Provide the get coin pricing use case
     */
    @Provides
    @Singleton
    fun provideGetCoinPricingUseCase(
        coinShopRepository: CoinShopRepository
    ): GetCoinPricingUC {
        return GetCoinPricingUC(coinShopRepository)
    }

    /**
     * Todo: get payment sheet response use case
     */
    @Provides
    @Singleton
    fun provideGetPaymentSheetResponseUC(
        coinShopRepository: CoinShopRepository
    ): GetPaymentSheetResponseUC {
        return GetPaymentSheetResponseUC(coinShopRepository)
    }

    /**
     * TODO: Provide the get transaction history use case
     */
    @Provides
    @Singleton
    fun provideGetTransactionHistoryUseCase(
        coinShopRepository: CoinShopRepository
    ): GetTransactionHistoryUC {
        return GetTransactionHistoryUC(coinShopRepository)
    }


    /**
     *-------------------------------------------------------------------------
     * TODO Comic Comment Use Cases
     */

    /**
     * TODO: Provide the get all comic comment by comic id use case
     */
    @Provides
    @Singleton
    fun provideGetAllComicCommentByComicIdUC(
        comicCommentRepository: ComicCommentRepository
    ): GetAllComicCommentByComicIdUC {
        return GetAllComicCommentByComicIdUC(comicCommentRepository)
    }

    /**
     * TODO: Provide the get main comment by comment id use case
     */
    @Provides
    @Singleton
    fun provideGetMainCommentByCommentIdUC(
        comicCommentRepository: ComicCommentRepository
    ): GetMainCommentByCommentIdUC {
        return GetMainCommentByCommentIdUC(comicCommentRepository)
    }

    /**
     * TODO: Provide the post comic comment by comic id use case
     */
    @Provides
    @Singleton
    fun providePostComicCommentByComicIdUC(
        comicCommentRepository: ComicCommentRepository
    ): PostComicCommentByComicIdUC {
        return PostComicCommentByComicIdUC(comicCommentRepository)
    }

    /**
     * TODO: Provide the post reply comment by comicId and commentId use case
     */
    @Provides
    @Singleton
    fun providePostReplyCommentByComicIdUC(
        comicCommentRepository: ComicCommentRepository
    ): PostReplyCommentByComicIdUC {
        return PostReplyCommentByComicIdUC(comicCommentRepository)
    }

    /**
     * TODO: Provide the get all reply comment by comicId and commentId use case
     */
    @Provides
    @Singleton
    fun provideGetAllReplyCommentByComicIdUC(
        comicCommentRepository: ComicCommentRepository
    ): GetAllReplyCommentByComicIdUC {
        return GetAllReplyCommentByComicIdUC(comicCommentRepository)
    }

    /**
     * TODO: use for delete comic comment by comicId and commentId
     */
    @Provides
    @Singleton
    fun provideDeleteComicCommentByComicIdUC(
        comicCommentRepository: ComicCommentRepository
    ): DeleteComicCommentByComicIdUC {
        return DeleteComicCommentByComicIdUC(comicCommentRepository)
    }

    /**
     * TODO: Provide the update comic comment by comicId and commentId use case
     */
    @Provides
    @Singleton
    fun provideUpdateComicCommentByComicIdUC(
        comicCommentRepository: ComicCommentRepository
    ): UpdateComicCommentByComicIdUC {
        return UpdateComicCommentByComicIdUC(comicCommentRepository)
    }


    /**
     * TODO: provide ReactComicCommentByComicIdUC
     */
    @Provides
    @Singleton
    fun provideReactComicCommentByComicIdUC(
        comicCommentRepository: ComicCommentRepository
    ): ReactComicCommentByComicIdUC {
        return ReactComicCommentByComicIdUC(comicCommentRepository)
    }


    /**
     *-------------------------------------------------------------------------
     * TODO Profile Use Case
     */

    /**
     * Todo: Provide the getProfileUseCase
     */
    @Provides
    @Singleton
    fun provideGetProfileUseCase(
        profileRepository: ProfileRepository
    ): GetProfileUC {
        return GetProfileUC(profileRepository)
    }
}