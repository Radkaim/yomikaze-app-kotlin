package com.example.yomikaze_app_kotlin.Domain.UseCases.Comic

import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import retrofit2.Response
import javax.inject.Inject

class UnlockManyChaptersUC @Inject constructor(
    private val chapterRepository: ChapterRepository
) {
    suspend fun unlockManyChapters(
        token: String,
        comicId: Long,
        chapterNumbers: List<Int>
    ): Response<Unit> {
        return chapterRepository.unlockManyChapters(token, comicId, chapterNumbers)
    }
}