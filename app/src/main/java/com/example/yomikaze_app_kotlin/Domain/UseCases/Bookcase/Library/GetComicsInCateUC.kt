package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryCategoryRepository
import javax.inject.Inject

class GetComicsInCateUC @Inject constructor(
    private val libraryCategoryRepository: LibraryCategoryRepository
) {
    suspend fun getComicsInCate(
        token: String,
        categoryName: String
    ): Result<BaseResponse<ComicResponse>> {
        return libraryCategoryRepository.getComicsInCategory(token, categoryName)
    }

}