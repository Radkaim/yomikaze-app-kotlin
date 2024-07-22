package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.util.Log
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ChapterApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ChapterDao
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import retrofit2.Response
import javax.inject.Inject

class ChapterRepositoryImpl @Inject constructor(
    private val api: ChapterApiService,
    private val chapterDao: ChapterDao
) : ChapterRepository {

    /**
     * TODO: get list of chapters by comic id
     */
    override suspend fun getListChaptersByComicId(
        token: String,comicId: Long): Result<BaseResponse<Chapter>> {
        return try {
            val response = api.getListChaptersByComicId("Bearer $token", comicId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: get chapter detail by comic id and chapter number
     */
    override suspend fun getChapterDetail(
        token: String,
        comicId: Long,
        chapterNumber: Int
    ): Result<Chapter> {
        return try {
            val response = api.getChapterDetail("Bearer $token", comicId, chapterNumber)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: insert chapter into database
     */
    override suspend fun insertChapter(chapter: Chapter) {
        chapterDao.insertChapter(chapter)
    }

    /**
     * TODO: Get chapter by Id Database
     */
    override suspend fun getChapterByIdDB(id: Long): Chapter {
        return chapterDao.getChapterById(id)
    }

    /**
     * TODO: Get chapter by comic id in database
     */
    override suspend fun getChaptersByComicIdDB(comicId: Long): List<Chapter> {
        return chapterDao.getListChaptersDownloadedByComicId(comicId)
    }

    /**
     * TODO: Get chapter by comic id and chapter number in database
     */
    override suspend fun getChapterByComicIdAndChapterNumberDB(
        comicId: Long,
        number: Int
    ): Chapter {
        return chapterDao.getChapterByComicIdAndChapterNumber(comicId, number)
    }

    /**
     * TODO: Delete chapter downloaded in database by chapter id
     */
    override suspend fun deleteChapterByChapterIdDB(chapterId: Long) {
        chapterDao.deleteChapterByChapterId(chapterId)
    }

    /**
     * TODO : unlockAChapter
     */
    override suspend fun unlockAChapter(
        token: String,
        comicId: Long,
        chapterNumber: Int
    ) :Response<Unit> {
        val response =  api.unlockAChapter("Bearer $token", comicId, chapterNumber)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("ChapterRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("ChapterRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                    Log.e("ChapterRepositoryImpl", "Failed to unlock chapter $httpCode")
                }
            }
            Result.failure(Exception("Failed to unlock chapter"))
        }
        return response
    }

    /**
     * TODO : unlockManyChapters
     */
    override suspend fun unlockManyChapters(
        token: String,
        comicId: Long,
        chapterNumbers: List<Int>
    ) :Response<Unit> {
        val response =  api.unlockManyChapters("Bearer $token", comicId, chapterNumbers)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("ChapterRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("ChapterRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                    Log.e("ChapterRepositoryImpl", "Failed to unlock chapters $httpCode")
                }
            }
            Result.failure(Exception("Failed to unlock chapters"))
        }
        return response
    }

}