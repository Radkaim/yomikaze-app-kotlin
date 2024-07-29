package com.example.yomikaze_app_kotlin.Domain.UseCases.Auth

import com.example.yomikaze_app_kotlin.Domain.Models.ChangePasswordRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class ChangePasswordUC @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun changePassword(
        token: String,
        changePasswordRequest: ChangePasswordRequest
    ): Response<Unit> {
        return authRepository.changePassword(token, changePasswordRequest)
    }

}