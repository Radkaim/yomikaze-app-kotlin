package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download

import android.content.Context
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.DownloadResult
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ImageRepository
import javax.inject.Inject

class DownloadComicDetailUC @Inject constructor(
    private val comicRepository: ComicRepository,
    private val imageRepository: ImageRepository
) {
    suspend fun insertComicDB(comic: ComicResponse, context: Context) {
        return try {
            comicRepository.insertComicDB(comic, context)
           // Result.success(true)
        } catch (e: Exception) {
           throw e
        }
    }

    suspend fun getAllComicsDownloadedDB(): Result<List<ComicResponse>> {
        return comicRepository.getAllComicsDownloadedDB()
    }

    suspend fun testDownloadComic(url: String): DownloadResult {
        return imageRepository.downloadImageFromApi(url)
    }
    suspend fun testReturnImageLocalPath(image: ByteArray, context: Context): String {
        return imageRepository.returnImageLocalPath(image, context)
    }
}