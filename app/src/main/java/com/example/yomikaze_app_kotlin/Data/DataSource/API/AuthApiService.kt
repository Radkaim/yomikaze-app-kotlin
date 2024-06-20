package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Model.LoginRequest
import com.example.yomikaze_app_kotlin.Domain.Model.RegisterRequest
import com.example.yomikaze_app_kotlin.Domain.Model.TokenResponse
import com.example.yomikaze_app_kotlin.Domain.Model.UserInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


/**
 * AuthApiService is an interface that defines the API endpoints for the authentication service.
 * The interface is used by Retrofit to generate the implementation of the API endpoints.
 */
interface AuthApiService {
    @POST("authentication/signin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<TokenResponse>

    @POST("authentication/signup")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<TokenResponse>

//    @POST("authentication/forgotPassword")
//    suspend fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): Response<TokenResponse>
//
//    @POST("authentication/resetPassword")
//    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<TokenResponse>
//
//    @POST("authentication/changePassword")
//    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Response<TokenResponse>

    @GET("authentication/info")
    suspend fun getUserInfo(@Header("Authorization") token: String): Response<UserInfoResponse>
}




