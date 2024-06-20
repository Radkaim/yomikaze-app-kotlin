package com.example.yomikaze_app_kotlin.Domain.UseCase


import com.example.yomikaze_app_kotlin.Domain.Model.TokenResponse
import com.example.yomikaze_app_kotlin.Domain.Repository.AuthRepository
import javax.inject.Inject

class LoginUC @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun login(username: String, password: String): Result<TokenResponse> {
        return authRepository.login(username, password)
    }
}