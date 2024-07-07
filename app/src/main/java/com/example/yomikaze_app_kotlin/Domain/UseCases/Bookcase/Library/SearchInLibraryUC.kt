package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryRepository
import javax.inject.Inject

class SearchInLibraryUC @Inject constructor(
    private val libraryRepository: LibraryRepository
) {
    suspend fun searchComic(
        token: String,
        comicNameQuery: String
    ): Result<BaseResponse<LibraryEntry>> {
        return libraryRepository.searchComicInLibraries(token, comicNameQuery)
    }

}