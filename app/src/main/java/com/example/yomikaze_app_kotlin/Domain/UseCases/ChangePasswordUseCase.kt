package com.example.yomikaze_app_kotlin.Domain.UseCases


import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
//    suspend fun changePassword(
//        oldPassword: String,
//        newPassword: String,
//        confirmPassword: String
//    ): Result<TokenResponse> {
//        return authRepository.changePassword(oldPassword, newPassword, confirmPassword)
//    }
}

