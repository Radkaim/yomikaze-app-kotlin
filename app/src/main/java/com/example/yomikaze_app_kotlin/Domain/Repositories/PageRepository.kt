package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.Page

interface PageRepository {

    suspend fun getPagesByChapterNumberOfComic(
        token: String,
        comicId: Long,
        chapterNumber: Int
    ): Result<Page>

    suspend fun getImagesByComicIdAndChapterNumberDB(comicId: Long, number: Int): List<Page>
}