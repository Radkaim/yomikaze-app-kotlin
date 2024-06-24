package com.example.yomikaze_app_kotlin.Domain.UseCases


import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponseTest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class GetHotComicBannerUseCase @Inject constructor(
    private val comicRepository: ComicRepository
){
    suspend fun getHotComicBannerImages(): Result<List<ComicResponseTest>> {
        return comicRepository.getHotComicBannerImages()
    }
}