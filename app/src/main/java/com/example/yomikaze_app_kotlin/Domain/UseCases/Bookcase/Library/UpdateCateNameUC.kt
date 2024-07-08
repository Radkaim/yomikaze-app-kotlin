package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library

import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryCategoryRepository
import retrofit2.Response
import javax.inject.Inject

class UpdateCateNameUC @Inject constructor(
    private val libraryCategoryRepository: LibraryCategoryRepository
) {
    suspend fun updateCateName(
        token: String,
        categoryId: Long,
        pathRequest: List<PathRequest>
    ): Response<Unit> {
        return libraryCategoryRepository.updateCategoryName(token, categoryId, pathRequest)
    }
}