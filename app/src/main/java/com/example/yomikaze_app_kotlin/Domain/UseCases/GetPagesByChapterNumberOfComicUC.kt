package com.example.yomikaze_app_kotlin.Domain.UseCases

import com.example.yomikaze_app_kotlin.Domain.Models.Page
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import javax.inject.Inject

class GetPagesByChapterNumberOfComicUC @Inject constructor(
    private val pageRepository: PageRepository
) {
    suspend fun getPagesByChapterNumberOfComic(comicId: Long, chapterNumber: Int): Result<Page> {
        return pageRepository.getPagesByChapterNumberOfComic(comicId, chapterNumber)
    }

}