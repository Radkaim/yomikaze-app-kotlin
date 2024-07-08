package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library

import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryCategoryRepository
import retrofit2.Response
import javax.inject.Inject

class DeleteCategoryUC @Inject constructor(
    private val libraryCategoryRepository: LibraryCategoryRepository
) {
    suspend fun deleteCategory(token: String, categoryId: Long): Response<Unit> {
        return libraryCategoryRepository.deleteCategory(token, categoryId)
    }

}