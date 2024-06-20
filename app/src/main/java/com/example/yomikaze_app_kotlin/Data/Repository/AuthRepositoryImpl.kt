package com.example.yomikaze_app_kotlin.Data.Repository

import android.util.Log
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Data.DataSource.API.AuthApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ChangePasswordRequest
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ChangePasswordResponse
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ForgotPasswordRequest
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ForgotPasswordResponse

import com.example.yomikaze_app_kotlin.Data.DataSource.API.RegisterRequest
import com.example.yomikaze_app_kotlin.Data.DataSource.API.RegisterResponse
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ResetPasswordRequest
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ResetPasswordResponse
import com.example.yomikaze_app_kotlin.Domain.Model.LoginRequest
import com.example.yomikaze_app_kotlin.Domain.Model.LoginResponse
import com.example.yomikaze_app_kotlin.Domain.Repository.AuthRepository
import javax.inject.Inject
import kotlin.Result.Companion.failure

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService,
    private val appPreference: AppPreference
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<LoginResponse> {
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
    ): Result<RegisterResponse> {
        val result = api.register(RegisterRequest(email, username, dateOfBirth, password, confirmPassword))
        if (result.isSuccessful) {
            appPreference.authToken = result.body()?.token
            //return Result.success(result.body()!!)
        }
        return failure(Exception("Register failed"))
    }

    override suspend fun forgotPassword(email: String): Result<ForgotPasswordResponse> {
        val result = api.forgotPassword(ForgotPasswordRequest(email))
        if (result.isSuccessful) {
            appPreference.authToken = result.body()?.token
          //  return Result.success(result.body()!!)
        }
        return failure(Exception("Forgot Password failed"))
    }

    override suspend fun resetPassword(password: String, confirmPassword: String): Result<ResetPasswordResponse> {
        val result = api.resetPassword(ResetPasswordRequest(password, confirmPassword))
        if (result.isSuccessful) {
            appPreference.authToken = result.body()?.token
            //  return Result.success(result.body()!!)
        }
        return failure(Exception("Reset Password failed"))
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String ,confirmPassword: String): Result<ChangePasswordResponse> {
        val result = api.changePassword(ChangePasswordRequest(oldPassword, newPassword, confirmPassword))
        if (result.isSuccessful) {
            appPreference.authToken = result.body()?.token
            //  return Result.success(result.body()!!)
        }
        return failure(Exception("Change Password failed"))
    }


}