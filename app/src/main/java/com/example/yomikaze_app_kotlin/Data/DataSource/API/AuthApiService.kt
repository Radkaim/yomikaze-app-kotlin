package com.example.yomikaze_app_kotlin.Data.DataSource.API

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


// Định nghĩa các request và response data classes
data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)


// Định nghĩa các API endpoints
interface AuthApiService {
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}



