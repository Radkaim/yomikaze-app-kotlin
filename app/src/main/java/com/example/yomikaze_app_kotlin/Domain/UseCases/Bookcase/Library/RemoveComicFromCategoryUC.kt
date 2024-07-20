package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library

import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryRepository
import retrofit2.Response
import javax.inject.Inject

class RemoveComicFromCategoryUC @Inject constructor(
    private val libraryRepository: LibraryRepository
) {
    /**
     * TODO: Implement the function to remove a comic from library category
     * when they remove a comic from a category
     */
    suspend fun removeComicFromCategory(
        token: String,
        comicId: Long,
        categoriesId: List<Long>
    ): Response<Unit> {
        return libraryRepository.removeComicFromCategory(token, comicId, categoriesId)
    }
}