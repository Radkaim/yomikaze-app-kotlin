package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryCategoryRepository
import javax.inject.Inject

class GetLibraryCategoryUC @Inject constructor(
    private val libraryCategoryRepository: LibraryCategoryRepository
) {
    suspend fun getLibraryCategory(
        token: String,
        page: Int? = null,
        size: Int? = null
        ): Result<BaseResponse<LibraryCategoryResponse>> {
        return libraryCategoryRepository.getCategories(token, page, size)
    }
}