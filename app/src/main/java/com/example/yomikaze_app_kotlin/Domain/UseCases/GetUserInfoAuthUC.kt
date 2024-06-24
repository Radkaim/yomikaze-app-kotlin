package com.example.yomikaze_app_kotlin.Domain.UseCases

import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import javax.inject.Inject

class GetUserInfoAuthUC @Inject constructor(
    private val authRepository: AuthRepository
) {

}