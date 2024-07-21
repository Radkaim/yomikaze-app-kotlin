package com.example.yomikaze_app_kotlin.Domain.UseCases.Comic

import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import retrofit2.Response
import javax.inject.Inject

class UnlockAChapterUC @Inject constructor(
    private val chapterRepository: ChapterRepository
) {
    suspend fun unlockAChapter(token: String, comicId: Long, chapterNumber: Int): Response<Unit> {
        return chapterRepository.unlockAChapter(token, comicId, chapterNumber)
    }
}