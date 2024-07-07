package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library

import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryRequest
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryCategoryRepository
import javax.inject.Inject

class CreateLibraryCategoryUC @Inject constructor(
    private val libraryCategoryRepository: LibraryCategoryRepository
) {
    suspend fun createLibraryCategory(
        token: String,
        libraryCategoryRequest: LibraryCategoryRequest
    ): Result<LibraryCategoryResponse> {
        return libraryCategoryRepository.createCategory(token, libraryCategoryRequest)
    }
}