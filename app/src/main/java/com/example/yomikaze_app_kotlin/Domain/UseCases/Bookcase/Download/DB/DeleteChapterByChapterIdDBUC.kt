package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB

import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import javax.inject.Inject

class DeleteChapterByChapterIdDBUC @Inject constructor(
    private val chapterRepository: ChapterRepository
) {
    suspend fun deleteChapterByChapterIdDB(chapterId: Long) {
        chapterRepository.deleteChapterByChapterIdDB(chapterId)
    }
}