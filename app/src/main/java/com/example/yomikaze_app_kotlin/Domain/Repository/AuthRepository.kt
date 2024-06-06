package com.example.yomikaze_app_kotlin.Domain.Repository

import com.example.yomikaze_app_kotlin.Data.DataSource.API.LoginResponse

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<LoginResponse>
}