package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class GetAllComicInDBUC @Inject constructor(
    private val comicRepository: ComicRepository
){
    suspend fun getAllComicsDownloadedDB(): Result<List<ComicResponse>> {
        return comicRepository.getAllComicsDownloadedDB()
    }
}