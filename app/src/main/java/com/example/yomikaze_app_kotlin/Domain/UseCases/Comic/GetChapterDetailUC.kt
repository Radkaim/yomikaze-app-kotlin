package com.example.yomikaze_app_kotlin.Domain.UseCases.Comic

import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import javax.inject.Inject

class GetChapterDetailUC @Inject constructor(
    private val chapterRepository: ChapterRepository
) {
    suspend fun getChapterDetail(
        token: String,
        comicId: Long,
        chapterNumber: Int
    ): Result<Chapter> {
        return chapterRepository.getChapterDetail(token, comicId, chapterNumber)
    }

}

