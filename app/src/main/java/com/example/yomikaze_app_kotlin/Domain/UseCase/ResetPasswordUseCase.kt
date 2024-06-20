package com.example.yomikaze_app_kotlin.Domain.UseCase


import com.example.yomikaze_app_kotlin.Domain.Model.ResetPasswordResponse
import com.example.yomikaze_app_kotlin.Domain.Repository.AuthRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend fun resetPassword(password: String, confirmPassword: String): Result<ResetPasswordResponse>{
        return authRepository.resetPassword(password, confirmPassword)
    }
}