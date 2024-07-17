package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.ProfileResponse

interface ProfileRepository {

    suspend fun getProfile(
        token: String,
    ): Result<ProfileResponse>

//    suspend fun updateProfile(
//        token: String,
//        authorResponse: AuthorResponse
//    ): Result<AuthorResponse>
}