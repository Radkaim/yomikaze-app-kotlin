package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library

import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryRepository
import retrofit2.Response
import javax.inject.Inject

class UnfollowComicFromLibraryUC @Inject constructor(
    private val libraryRepository: LibraryRepository
) {
    /**
     * TODO: Implement the function to remove a comic from library category
     * when they unfollow the comic or remove a comic from all category
     * and remove the comic from library
     * unFollowComic
     */
    suspend fun unfollowComicFromLibrary(
        token: String,
        comicId: Long
    ): Response<Unit> {
        return libraryRepository.unfollowComicFromLibrary(token, comicId)
    }
}