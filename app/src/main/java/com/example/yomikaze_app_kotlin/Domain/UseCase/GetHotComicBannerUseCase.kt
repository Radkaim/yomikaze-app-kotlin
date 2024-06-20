package com.example.yomikaze_app_kotlin.Domain.UseCase


import com.example.yomikaze_app_kotlin.Domain.Model.ComicResponseTest
import com.example.yomikaze_app_kotlin.Domain.Repository.ComicRepository
import javax.inject.Inject

class GetHotComicBannerUseCase @Inject constructor(
    private val comicRepository: ComicRepository
){
    suspend fun getHotComicBannerImages(): Result<List<ComicResponseTest>> {
        return comicRepository.getHotComicBannerImages()
    }
}