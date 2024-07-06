package com.example.yomikaze_app_kotlin.Domain.UseCases.Auth


import com.example.yomikaze_app_kotlin.Domain.Models.TokenResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import javax.inject.Inject

class RegisterUC @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun register(
        username: String,
        password: String,
        fullName: String,
        confirmPassword: String,
        email: String,
        birthday: String
    ): Result<TokenResponse> {
        return authRepository.register( username, password,fullName, confirmPassword, email, birthday)
    }
}
