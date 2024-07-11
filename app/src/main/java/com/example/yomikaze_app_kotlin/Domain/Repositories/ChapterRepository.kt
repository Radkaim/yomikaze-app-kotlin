package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.Chapter

interface ChapterRepository {

    /**
     * TODO: Get list of chapters by comic id
     */
    suspend fun getListChaptersByComicId(comicId: Long): Result<List<Chapter>>

    /**
     * TODO: Get chapter detail by comic id and chapter number
     */
    suspend fun getChapterDetail(
        token: String,
        comicId: Long,
        chapterNumber: Int
    ): Result<Chapter>

    /**
     * TODO: Insert chapter into database
     */
    suspend fun insertChapter(chapter: Chapter)

    /**
     * TODO: Get chapter by Id Database
     */
    suspend fun getChapterByIdDB(id: Long): Chapter

    /**
     * TODO: Get chapter by comic id in database
     */
    suspend fun getChapterByComicIdDB(comicId: Long): List<Chapter>



}