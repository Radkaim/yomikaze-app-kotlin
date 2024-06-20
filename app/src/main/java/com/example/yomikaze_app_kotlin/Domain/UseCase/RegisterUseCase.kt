package com.example.yomikaze_app_kotlin.Domain.UseCase


import com.example.yomikaze_app_kotlin.Domain.Model.RegisterResponse
import com.example.yomikaze_app_kotlin.Domain.Repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend fun register(email: String, username: String, dateOfBirth:String, password: String, confirmPassword: String): Result<RegisterResponse> {
        return authRepository.register(email, username, dateOfBirth, password, confirmPassword)
    }
}
