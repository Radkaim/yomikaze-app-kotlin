package com.example.yomikaze_app_kotlin.Domain.UseCases

import com.example.yomikaze_app_kotlin.Domain.Models.TokenResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import javax.inject.Inject

class LoginWithGoogleUC @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun loginWithGoogle(token: TokenResponse): Result<TokenResponse> {
        return authRepository.loginWithGoogle(token)
    }
}