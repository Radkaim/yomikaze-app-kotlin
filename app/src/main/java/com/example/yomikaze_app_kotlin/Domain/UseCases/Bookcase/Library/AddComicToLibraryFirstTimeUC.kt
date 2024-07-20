package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library

import com.example.yomikaze_app_kotlin.Domain.Models.LibraryRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryRepository
import retrofit2.Response
import javax.inject.Inject

class AddComicToLibraryFirstTimeUC @Inject constructor(
    private val libraryRepository: LibraryRepository
) {

    /**
     * TODO: Implement the function to add a comic to library category
     * in the first time they follow the comic
     */
    suspend fun addComicToLibraryFirstTime(
        token: String,
        libraryRequest: LibraryRequest
    ):Response<Unit> {
        return libraryRepository.addComicToLibraryFirstTime(token, libraryRequest)
    }
}