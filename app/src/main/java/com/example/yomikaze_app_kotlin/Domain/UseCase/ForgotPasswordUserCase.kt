package com.example.yomikaze_app_kotlin.Domain.UseCase

import com.example.yomikaze_app_kotlin.Data.DataSource.API.ForgotPasswordResponse
import com.example.yomikaze_app_kotlin.Domain.Repository.AuthRepository
import javax.inject.Inject

class ForgotPasswordUserCase @Inject constructor(
    private val
    authRepository: AuthRepository
){
    suspend fun forgotPassword(email: String): Result<ForgotPasswordResponse>{
        return authRepository.forgotPassword(email)
    }
}