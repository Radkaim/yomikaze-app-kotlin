package com.example.yomikaze_app_kotlin.Core.Module

import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Data.DataSource.API.AuthApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ChapterApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.HistoryApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.LibraryApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.LibraryCategoryApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.PageApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ChapterDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ComicDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.PageDao
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.AuthRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.ChapterRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.ComicRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.HistoryRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.ImageRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.LibraryCategoryRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.LibraryRepositoryImpl
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.PageRepositoryImpl
import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.HistoryRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ImageRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryCategoryRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
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
        comicDao: ComicDao,
        imageRepository: ImageRepository
    ): ComicRepository {
        return ComicRepositoryImpl(api, comicDao, imageRepository)
    }

    /**
     * Todo: Provide the ChapterRepository
     */
    @Provides
    @Singleton
    fun provideChapterRepository(
        chapterApiService: ChapterApiService
    ): ChapterRepository {
        return ChapterRepositoryImpl(
            chapterApiService
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
//        pageRepository: PageRepository,
        pageApiService: PageApiService
    ): PageRepository {
        return PageRepositoryImpl(
            chapterDao,
            pageDao,
            imageRepository,
//            pageRepository,
            pageApiService
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

}