package com.example.yomikaze_app_kotlin.Domain.UseCases

import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import javax.inject.Inject

class LogoutUC @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun logout() {
        return authRepository.logout()
    }
}