package com.example.yomikaze_app_kotlin.Domain.UseCase

import com.example.yomikaze_app_kotlin.Data.DataSource.API.LoginResponse
import com.example.yomikaze_app_kotlin.Domain.Repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return authRepository.login(email, password)
    }
}