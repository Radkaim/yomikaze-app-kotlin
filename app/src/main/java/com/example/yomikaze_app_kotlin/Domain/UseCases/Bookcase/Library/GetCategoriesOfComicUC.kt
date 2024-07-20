package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library

import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryRepository
import javax.inject.Inject

class GetCategoriesOfComicUC @Inject constructor(
    private val libraryRepository: LibraryRepository
) {
    /**
     * TODO: Implement the function to get
     * list category which comic is in
     */
    suspend fun getCategoriesOfComic(
        token: String,
        comicId: Long
    ): Result<LibraryEntry> {
        return libraryRepository.getCategoriesOfComic(token, comicId)
    }
}