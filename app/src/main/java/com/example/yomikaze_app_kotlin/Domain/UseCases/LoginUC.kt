package com.example.yomikaze_app_kotlin.Domain.UseCases


import com.example.yomikaze_app_kotlin.Domain.Models.LoginRequest
import com.example.yomikaze_app_kotlin.Domain.Models.TokenResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import javax.inject.Inject

class LoginUC @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun login(loginRequest: LoginRequest): Result<TokenResponse> {
        return authRepository.login(loginRequest)
    }
}