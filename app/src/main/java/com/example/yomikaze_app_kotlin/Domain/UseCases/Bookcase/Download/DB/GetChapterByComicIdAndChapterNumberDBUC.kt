package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB

import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import javax.inject.Inject

class GetChapterByComicIdAndChapterNumberDBUC @Inject constructor(
    private val chapterRepository: ChapterRepository
) {
    suspend fun getChapterByComicIdAndChapterNumberDB(comicId: Long, number: Int): Chapter {
        return chapterRepository.getChapterByComicIdAndChapterNumberDB(comicId, number)
    }

}