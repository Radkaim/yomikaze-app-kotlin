package com.example.yomikaze_app_kotlin.Core.Module

import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Data.DataSource.API.AuthApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ChapterApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ChapterCommentApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.CoinShopApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicCommentApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.HistoryApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.LibraryApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.LibraryCategoryApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.NotificationApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.PageApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ProfileApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ChapterDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ComicDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.PageDao
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.AuthRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.ChapterCommentRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.ChapterRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.CoinShopRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.ComicCommentRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.ComicRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.DownloadPageRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.HistoryRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.ImageRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.LibraryCategoryRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.LibraryRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.NotificationRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.PageRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.ProfileRepositoryImpl
import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterCommentRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.CoinShopRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicCommentRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.DownloadPageRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.HistoryRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ImageRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryCategoryRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.NotificationRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ProfileRepository
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
        appPreference: AppPreference,
        profileRepository: ProfileRepository
    ): AuthRepository {
        return AuthRepositoryImpl(api, appPreference, profileRepository)
    }


    /**
     * Todo: Provide the ComicRepository
     */
    @Provides
    @Singleton
    fun provideComicRepository(
        api: ComicApiService,
        comicDao: ComicDao,
        chapterDao: ChapterDao,
        imageRepository: ImageRepository,
        pageRepository: PageRepository
    ): ComicRepository {
        return ComicRepositoryImpl(api, comicDao, chapterDao, imageRepository, pageRepository)
    }

    /**
     * Todo: Provide the ChapterRepository
     */
    @Provides
    @Singleton
    fun provideChapterRepository(
        chapterApiService: ChapterApiService,
        chapterDao: ChapterDao
    ): ChapterRepository {
        return ChapterRepositoryImpl(
            chapterApiService,
            chapterDao
        )
    }

    /**
     * Todo: Provide the PageRepository
     */
    @Provides
    @Singleton
    fun providePageRepository(
        chapterDao: ChapterDao,
        pageDao: PageDao,
        imageRepository: ImageRepository,
        //  pageRepository: PageRepository,
        pageApiService: PageApiService
    ): PageRepository {
        return PageRepositoryImpl(
            chapterDao,
            pageDao,
            imageRepository,
            //     pageRepository,
            pageApiService
        )
    }

    /**
     ** Todo: Provide the DownloadPageRepository
     */
    @Provides
    @Singleton
    fun provideDownloadPageRepository(
        chapterDao: ChapterDao,
        pageDao: PageDao,
        imageRepository: ImageRepository,
        pageRepository: PageRepository
    ): DownloadPageRepository {
        return DownloadPageRepositoryImpl(
            chapterDao,
            pageDao,
            imageRepository,
            pageRepository
        )
    }


    /**
     * Todo: Provide the ImageRepository
     */
    @Provides
    @Singleton
    fun provideImageRepository(): ImageRepository {
        return ImageRepositoryImpl()
    }

    /**
     * Todo: Provide the LibraryRepository
     */
    @Provides
    @Singleton
    fun provideLibraryRepository(
        libraryApiService: LibraryApiService
    ): LibraryRepository {
        return LibraryRepositoryImpl(libraryApiService)
    }

    /**
     * Todo: Provide the LibraryCategoryRepository
     */
    @Provides
    @Singleton
    fun provideLibraryCategoryRepository(
        libraryCategoryApiService: LibraryCategoryApiService
    ): LibraryCategoryRepository {
        return LibraryCategoryRepositoryImpl(libraryCategoryApiService)
    }

    /**
     * Todo: Provide the HistoryRepository
     */
    @Provides
    @Singleton
    fun provideHistoryRepository(
        historyApiService: HistoryApiService
    ): HistoryRepository {
        return HistoryRepositoryImpl(historyApiService)
    }

    /**
     * Todo: provide the coinShopRepository
     */
    @Provides
    @Singleton
    fun provideCoinShopRepository(
        coinShopApiService: CoinShopApiService
    ): CoinShopRepository {
        return CoinShopRepositoryImpl(coinShopApiService)
    }

    /**
     * Todo: Provide the Comic Comment Repository
     */
    @Provides
    @Singleton
    fun provideComicCommentRepository(
        comicCommentApiService: ComicCommentApiService
    ): ComicCommentRepository {
        return ComicCommentRepositoryImpl(comicCommentApiService)
    }

    /**
     * TODO: Provide chapter comment repository
     */
    @Provides
    @Singleton
    fun provideChapterCommentRepository(
        chapterCommentApiService: ChapterCommentApiService
    ): ChapterCommentRepository {
        return ChapterCommentRepositoryImpl(chapterCommentApiService)
    }

    /**
     * --------------------------------------------------------------------------
     * Todo: Provide the the Profile Repository
     */
    @Provides
    @Singleton
    fun provideProfileRepository(
        profileApiService: ProfileApiService
    ): ProfileRepository {
        return ProfileRepositoryImpl(profileApiService)
    }

    /**
     * --------------------------------------------------------------------------
     * Todo: Provide the the Notification Repository
     */
    @Provides
    @Singleton
    fun provideNotificationRepository(
        notificationApiService: NotificationApiService
    ): NotificationRepository {
        return NotificationRepositoryImpl(notificationApiService)
    }
}