package com.example.yomikaze_app_kotlin.Domain.UseCases.Profile

import com.example.yomikaze_app_kotlin.Domain.Models.ProfileResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ProfileRepository
import javax.inject.Inject

class GetProfileUC @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun getProfile(token: String): Result<ProfileResponse> {
        return profileRepository.getProfile(token)
    }
}