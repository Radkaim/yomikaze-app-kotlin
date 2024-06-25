package com.example.yomikaze_app_kotlin.Domain.UseCases

import com.example.yomikaze_app_kotlin.Domain.Models.UserInfoResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import javax.inject.Inject

class GetUserInfoUC @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend fun getUserInfo(token: String): Result<UserInfoResponse> {
        return authRepository.getUserInfo(token)
    }
}