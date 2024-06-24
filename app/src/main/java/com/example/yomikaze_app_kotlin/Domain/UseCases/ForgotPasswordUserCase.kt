package com.example.yomikaze_app_kotlin.Domain.UseCases


import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import javax.inject.Inject

class ForgotPasswordUserCase @Inject constructor(
    private val
    authRepository: AuthRepository
){
//    suspend fun forgotPassword(email: String): Result<TokenResponse>{
//        return authRepository.forgotPassword(email)
//    }
}