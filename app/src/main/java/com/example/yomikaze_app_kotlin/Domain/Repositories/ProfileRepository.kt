package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.ChangePasswordRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ProfileResponse
import retrofit2.Response

interface ProfileRepository {

    suspend fun getProfile(
        token: String,
    ): Result<ProfileResponse>

//    suspend fun updateProfile(
//        token: String,
//        authorResponse: AuthorResponse
//    ): Result<AuthorResponse>

    //change password
    suspend fun changePassword(
        token: String,
        changePasswordRequest: ChangePasswordRequest
    ): Response<Unit>

}