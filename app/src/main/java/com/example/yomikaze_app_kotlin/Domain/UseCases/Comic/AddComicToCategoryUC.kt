package com.example.yomikaze_app_kotlin.Domain.UseCases.Comic

import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryCategoryRepository
import retrofit2.Response
import javax.inject.Inject

class AddComicToCategoryUC @Inject constructor(
    private val libraryCategoryRepository: LibraryCategoryRepository
) {
    suspend fun addComicToCategory(
        token: String,
        libraryEntryId: Long,
        pathRequest: List<PathRequest>
    ): Response<Unit> {
       return  libraryCategoryRepository.addComicToCategory(token, libraryEntryId, pathRequest)
    }
}