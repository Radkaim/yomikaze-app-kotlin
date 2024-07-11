package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB

import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import javax.inject.Inject

class GetChapterByComicIdDBUC @Inject constructor(
    private val chapterRepository: ChapterRepository
) {
    suspend fun getChapterByComicIdDB(comicId: Long): List<Chapter> {
        return chapterRepository.getChapterByComicIdDB(comicId)
    }
}