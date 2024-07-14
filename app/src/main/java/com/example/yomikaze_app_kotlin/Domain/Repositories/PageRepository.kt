package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.Page

interface PageRepository {

    suspend fun getPagesByChapterNumberOfComic(
        token: String,
        comicId: Long,
        chapterNumber: Int
    ): Result<Page>

    suspend fun getPageByComicIdAndChapterNumberDB(comicId: Long, number: Int): Page

    /**
     * delete image of page by comicID and chapterNumber
     */
    suspend fun deletePageByComicIdAndChapterNumberDB(comicId: Long, number: Int)
}