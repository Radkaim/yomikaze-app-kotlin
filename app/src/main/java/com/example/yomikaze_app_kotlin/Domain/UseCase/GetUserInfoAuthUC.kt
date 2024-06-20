package com.example.yomikaze_app_kotlin.Domain.UseCase

import com.example.yomikaze_app_kotlin.Domain.Repository.AuthRepository
import javax.inject.Inject

class GetUserInfoAuthUC @Inject constructor(
    private val authRepository: AuthRepository
) {

}