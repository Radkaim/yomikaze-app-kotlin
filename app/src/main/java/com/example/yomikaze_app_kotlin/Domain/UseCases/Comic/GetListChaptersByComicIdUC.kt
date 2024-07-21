package com.example.yomikaze_app_kotlin.Domain.UseCases.Comic

import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import javax.inject.Inject

class GetListChaptersByComicIdUC @Inject constructor(
    private val chapterRepository: ChapterRepository
) {
    suspend fun getListChapters(token: String, comicId: Long): Result<List<Chapter>> {
        return chapterRepository.getListChaptersByComicId(token, comicId)
    }
}