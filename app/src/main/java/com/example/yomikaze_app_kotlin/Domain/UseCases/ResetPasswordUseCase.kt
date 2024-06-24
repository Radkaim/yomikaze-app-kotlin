package com.example.yomikaze_app_kotlin.Domain.UseCases



import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
//    suspend fun resetPassword(password: String, confirmPassword: String): Result<TokenResponse>{
//        return authRepository.resetPassword(password, confirmPassword)
//    }
}