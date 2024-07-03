package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import com.example.yomikaze_app_kotlin.Data.DataSource.API.ChapterApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ChapterDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.PageDao
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ImageRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import javax.inject.Inject

class ChapterRepositoryImpl @Inject constructor(

    private val api: ChapterApiService
) : ChapterRepository {

    override suspend fun getChaptersByComicId(comicId: Long): Result<List<Chapter>> {
        return try {
            val response = api.getListChaptersByComicId(comicId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}