package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library

import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryRepository
import retrofit2.Response
import javax.inject.Inject

class AddComicToLibrarySecondTimeUC @Inject constructor(
    private val libraryRepository: LibraryRepository
) {
    /**
     * TODO: Implement the function to add a comic to library category
     * in the second time they have followed the comic and want to add
     * into another category
     */
    suspend fun addComicToLibrarySecondTime(
        token: String,
        comicId: Long,
        categoriesId: List<Long>
    ):Response<Unit> {
        return libraryRepository.addComicToLibrarySecondTime(token, comicId, categoriesId)
    }
}