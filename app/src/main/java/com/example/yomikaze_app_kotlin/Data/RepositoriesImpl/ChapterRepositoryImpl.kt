package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import com.example.yomikaze_app_kotlin.Data.DataSource.API.ChapterApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ChapterDao
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import javax.inject.Inject

class ChapterRepositoryImpl @Inject constructor(
    private val api: ChapterApiService,
    private val chapterDao: ChapterDao
) : ChapterRepository {

    /**
     * TODO: get list of chapters by comic id
     */
    override suspend fun getListChaptersByComicId(comicId: Long): Result<List<Chapter>> {
        return try {
            val response = api.getListChaptersByComicId(comicId)
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
            val response = api.getChapterDetail(token, comicId, chapterNumber)
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

}