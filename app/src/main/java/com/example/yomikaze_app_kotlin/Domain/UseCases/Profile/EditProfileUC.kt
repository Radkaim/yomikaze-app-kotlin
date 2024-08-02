package com.example.yomikaze_app_kotlin.Domain.UseCases.Profile

import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ProfileRepository
import retrofit2.Response
import javax.inject.Inject

class EditProfileUC @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun updateProfile(
        token: String,
        pathRequest: List<PathRequest>
    ): Response<Unit> {
        return profileRepository.updateProfile(token, pathRequest)
    }
}