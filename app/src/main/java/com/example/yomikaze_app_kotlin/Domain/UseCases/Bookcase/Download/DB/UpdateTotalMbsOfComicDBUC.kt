package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB

import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class UpdateTotalMbsOfComicDBUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend fun updateTotalMbsOfComicDB(comicId: Long, totalMbs: Long) {
        comicRepository.updateTotalMbsOfComic(comicId, totalMbs)
    }
}