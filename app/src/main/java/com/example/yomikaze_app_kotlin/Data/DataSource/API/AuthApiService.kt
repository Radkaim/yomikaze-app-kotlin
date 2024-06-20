package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Model.LoginRequest
import com.example.yomikaze_app_kotlin.Domain.Model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST



//email, username, dateOfBirth, password, confirmPassword
data class RegisterRequest(val email: String, val username: String, val dateOfBirth: String, val password: String, val confimPassword: String)
data class RegisterResponse(val token: String)

data class ForgotPasswordRequest(val email: String)
data class ForgotPasswordResponse(val token: String)

data class ResetPasswordRequest(val password: String, val confimPassword: String)
data class ResetPasswordResponse(val token: String)

// Định nghĩa các API endpoints
interface AuthApiService {
    @POST("authentication/signin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/auth/register")
    suspend fun register(@Body loginRequest: RegisterRequest): Response<LoginResponse>


    @POST("/auth/forgotPassword")
    suspend fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @POST("/auth/resetPassword")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<ResetPasswordResponse>

}




