package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryRepository
import javax.inject.Inject

class GetComicsInCateUC @Inject constructor(
    private val libraryRepository: LibraryRepository
) {
    suspend fun getComicsInCate(
        token: String,
        categoryId: Long,
        orderBy: String? = null,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<LibraryEntry>> {
        return libraryRepository.getComicsByCategoryId(token, categoryId, orderBy, page, size)
    }
}