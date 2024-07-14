package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB

import com.example.yomikaze_app_kotlin.Domain.Models.Page
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import javax.inject.Inject

class GetPageByComicIdAndChapterNumberDBUC @Inject constructor(
    private val pageRepository: PageRepository
) {
    suspend fun getPageByComicIdAndChapterNumberDB(comicId: Long, number: Int): Page {
        return pageRepository.getPageByComicIdAndChapterNumberDB(comicId, number)
    }
}