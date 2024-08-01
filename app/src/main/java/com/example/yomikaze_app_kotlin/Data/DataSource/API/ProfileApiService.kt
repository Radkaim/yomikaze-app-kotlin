package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.ChangePasswordRequest
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.PUT

interface ProfileApiService {

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") token: String,
    ): ProfileResponse

    //change password
    //change password
    @PUT("profile")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<Unit>

    //update profile
    @PATCH("profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body pathRequest: List<PathRequest>
    ): Response<Unit>

}