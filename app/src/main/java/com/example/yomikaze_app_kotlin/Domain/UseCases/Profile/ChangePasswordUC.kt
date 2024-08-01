package com.example.yomikaze_app_kotlin.Domain.UseCases.Profile

import com.example.yomikaze_app_kotlin.Domain.Models.ChangePasswordRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ProfileRepository
import retrofit2.Response
import javax.inject.Inject

class ChangePasswordUC @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun changePassword(
        token: String,
        changePasswordRequest: ChangePasswordRequest
    ): Response<Unit> {
        return profileRepository.changePassword(token, changePasswordRequest)
    }

}