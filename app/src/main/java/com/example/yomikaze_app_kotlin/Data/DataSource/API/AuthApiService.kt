package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Model.ForgotPasswordRequest
import com.example.yomikaze_app_kotlin.Domain.Model.ForgotPasswordResponse
import com.example.yomikaze_app_kotlin.Domain.Model.LoginRequest
import com.example.yomikaze_app_kotlin.Domain.Model.LoginResponse
import com.example.yomikaze_app_kotlin.Domain.Model.RegisterRequest
import com.example.yomikaze_app_kotlin.Domain.Model.ResetPasswordRequest
import com.example.yomikaze_app_kotlin.Domain.Model.ResetPasswordResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST



// Định nghĩa các API endpoints
interface AuthApiService {
    @POST("authentication/signin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/authentication/signup")
    suspend fun register(@Body loginRequest: RegisterRequest): Response<LoginResponse>


    @POST("/auth/forgotPassword")
    suspend fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @POST("/auth/resetPassword")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<ResetPasswordResponse>

}




