package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.Chapter

interface ChapterRepository {

    suspend fun getChaptersByComicId(comicId: Long): Result<List<Chapter>>
}