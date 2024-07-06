package com.example.yomikaze_app_kotlin.Domain.UseCases

import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryRepository
import javax.inject.Inject

class SearchInLibraryUC @Inject constructor(
    private val libraryRepository: LibraryRepository
){

}