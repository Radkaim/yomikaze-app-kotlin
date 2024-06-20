package com.example.yomikaze_app_kotlin.Data.Repository

import android.util.Log
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Data.DataSource.API.AuthApiService
import com.example.yomikaze_app_kotlin.Domain.Model.ChangePasswordRequest
import com.example.yomikaze_app_kotlin.Domain.Model.ForgotPasswordRequest
import com.example.yomikaze_app_kotlin.Domain.Model.LoginRequest
import com.example.yomikaze_app_kotlin.Domain.Model.RegisterRequest
import com.example.yomikaze_app_kotlin.Domain.Model.ResetPasswordRequest
import com.example.yomikaze_app_kotlin.Domain.Model.TokenResponse
import com.example.yomikaze_app_kotlin.Domain.Repository.AuthRepository
import javax.inject.Inject
import kotlin.Result.Companion.failure

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService,
    private val appPreference: AppPreference
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<TokenResponse> {
        val result = api.login(LoginRequest(username, password))
        Log.d("Login", result.toString())
        if (result.isSuccessful) {
            appPreference.authToken = result.body()?.token
            return Result.success(result.body()!!)
        }
        return failure(Exception("Login failed"))
    }

    override suspend fun register(
        email: String,
        username: String,
        dateOfBirth: String,
        password: String,
        confirmPassword: String
    ): Result<TokenResponse> {
        val result = api.register(RegisterRequest(email, username, dateOfBirth, password, confirmPassword))
        if (result.isSuccessful) {
            appPreference.authToken = result.body()?.token
            //return Result.success(result.body()!!)
        }
        return failure(Exception("Register failed"))
    }

    override suspend fun forgotPassword(email: String): Result<TokenResponse> {
        val result = api.forgotPassword(ForgotPasswordRequest(email))
        if (result.isSuccessful) {
            appPreference.authToken = result.body()?.token
          //  return Result.success(result.body()!!)
        }
        return failure(Exception("Forgot Password failed"))
    }

    override suspend fun resetPassword(password: String, confirmPassword: String): Result<TokenResponse> {
        val result = api.resetPassword(ResetPasswordRequest(password, confirmPassword))
        if (result.isSuccessful) {
            appPreference.authToken = result.body()?.token
            //  return Result.success(result.body()!!)
        }
        return failure(Exception("Reset Password failed"))
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String ,confirmPassword: String): Result<TokenResponse> {
        val result = api.changePassword(ChangePasswordRequest(oldPassword, newPassword, confirmPassword))
        if (result.isSuccessful) {
            appPreference.authToken = result.body()?.token
            //  return Result.success(result.body()!!)
        }
        return failure(Exception("Change Password failed"))
    }


}