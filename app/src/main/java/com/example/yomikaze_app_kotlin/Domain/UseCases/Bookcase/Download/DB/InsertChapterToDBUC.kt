package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB

import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import javax.inject.Inject

class InsertChapterToDBUC @Inject constructor(
    private val chapterRepository: ChapterRepository
) {
    suspend fun insertChapterDB(chapter: Chapter) {
        return chapterRepository.insertChapter(chapter)
    }
}