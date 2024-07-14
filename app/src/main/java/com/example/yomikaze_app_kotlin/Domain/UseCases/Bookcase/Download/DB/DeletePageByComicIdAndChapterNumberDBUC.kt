package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB

import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import javax.inject.Inject

class DeletePageByComicIdAndChapterNumberDBUC @Inject constructor(
    private val pageRepository: PageRepository
) {
    suspend fun deletePageByComicIdAndChapterNumberDB(comicId: Long, number: Int) {
        pageRepository.deletePageByComicIdAndChapterNumberDB(comicId, number)
    }
}