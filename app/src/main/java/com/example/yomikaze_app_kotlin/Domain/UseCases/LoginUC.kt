package com.example.yomikaze_app_kotlin.Domain.UseCases


import com.example.yomikaze_app_kotlin.Domain.Models.TokenResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import javax.inject.Inject

class LoginUC @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun login(username: String, password: String): Result<TokenResponse> {
        return authRepository.login(username, password)
    }
}