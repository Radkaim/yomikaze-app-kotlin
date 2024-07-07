package com.example.yomikaze_app_kotlin.Domain.UseCases.Comic

import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import javax.inject.Inject

class GetListChaptersByComicIdUC @Inject constructor(
    private val chapterRepository: ChapterRepository
) {
    suspend fun getListChapters(comicId: Long): Result<List<Chapter>> {
        return chapterRepository.getChaptersByComicId(comicId)
    }
}