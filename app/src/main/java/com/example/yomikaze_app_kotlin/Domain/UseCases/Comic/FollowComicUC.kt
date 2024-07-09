package com.example.yomikaze_app_kotlin.Domain.UseCases.Comic

import com.example.yomikaze_app_kotlin.Domain.Models.FollowComicResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class FollowComicUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend fun followComic(token: String, comicId: Long): Result<FollowComicResponse> {
        return comicRepository.followComic(token, comicId)
    }
}