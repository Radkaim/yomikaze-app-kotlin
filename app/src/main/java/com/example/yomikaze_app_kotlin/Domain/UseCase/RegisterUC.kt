package com.example.yomikaze_app_kotlin.Domain.UseCase


import com.example.yomikaze_app_kotlin.Domain.Model.TokenResponse
import com.example.yomikaze_app_kotlin.Domain.Repository.AuthRepository
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
