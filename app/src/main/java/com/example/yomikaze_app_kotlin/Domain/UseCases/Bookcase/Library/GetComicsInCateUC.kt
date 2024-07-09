package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryCategoryRepository
import javax.inject.Inject

class GetComicsInCateUC @Inject constructor(
    private val libraryCategoryRepository: LibraryCategoryRepository
) {
    suspend fun getComicsInCate(
        token: String,
        categoryName: String,
        orderBy: String?,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<LibraryEntry>> {
        return libraryCategoryRepository.getComicsInCategory(token, categoryName, orderBy, page, size)
    }

}