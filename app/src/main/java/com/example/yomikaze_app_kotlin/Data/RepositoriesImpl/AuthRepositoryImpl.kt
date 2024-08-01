package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.util.Log
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Data.DataSource.API.AuthApiService
import com.example.yomikaze_app_kotlin.Domain.Models.ErrorResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LoginRequest
import com.example.yomikaze_app_kotlin.Domain.Models.RegisterRequest
import com.example.yomikaze_app_kotlin.Domain.Models.TokenResponse
import com.example.yomikaze_app_kotlin.Domain.Models.UserInfoResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.NotificationRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ProfileRepository
import com.google.gson.Gson
import javax.inject.Inject
import kotlin.Result.Companion.failure


class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService,
    private val appPreference: AppPreference,
    private val profileRepository: ProfileRepository,
    private val notificationRepository: NotificationRepository,
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Result<TokenResponse> {
        val result = api.login(LoginRequest(loginRequest.username, loginRequest.password))
        if (result.isSuccessful) {
            val tokenResponse = result.body()
            if (tokenResponse?.token != null) {
                appPreference.authToken = tokenResponse.token
                appPreference.isUserLoggedIn = true
                subscribeToNotification(tokenResponse.token, appPreference.fcmToken!!)
                val profileResponse = profileRepository.getProfile(tokenResponse.token)
                if (profileResponse.isSuccess) {
                    appPreference.userId = profileResponse.getOrNull()?.id!!
                    appPreference.userRoles = profileResponse.getOrNull()?.roles
                    appPreference.userAvatar = profileResponse.getOrNull()?.avatar
                    appPreference.userName = profileResponse.getOrNull()?.name
                    appPreference.userBalance = profileResponse.getOrNull()?.balance ?: 0
//                    Log.d("AuthRepositoryImpl", "login: ${appPreference.userId}")
                } else {
                    Log.d("AuthRepositoryImpl", "login: ${profileResponse.exceptionOrNull()}")
                }
                return Result.success(tokenResponse)
            }

        }

        val errorResponse = result.errorBody()?.string()
        if (errorResponse != null) {
            return try {
                val error = Gson().fromJson(errorResponse, ErrorResponse::class.java)
                // Log.d("AuthRepositoryImpl", "login: ${error.errors}")
                error.errors?.forEach { (field, messages) ->
                    messages.forEach { message ->
                        //    Log.d("AuthRepositoryImpl", "Field: $field, Error: $message")
                    }
                }
                return error.errors?.let { Result.failure(Exception(Gson().toJson(error))) }!!
            } catch (e: Exception) {
                Result.failure(Exception("Login failed"))
            }
        }

        return Result.failure(Exception("Login failed"))
    }


    override suspend fun loginWithGoogle(token: TokenResponse): Result<TokenResponse> {
        val result = api.loginWithGoogle(token)
        if (result.isSuccessful) {
            val tokenResponse = result.body()
            if (tokenResponse?.token != null) {
                appPreference.authToken = tokenResponse.token
                appPreference.isUserLoggedIn = true
                appPreference.isLoginWithGoogle = true
                subscribeToNotification(tokenResponse.token, appPreference.fcmToken!!)
                val profileResponse = profileRepository.getProfile(tokenResponse.token)
                if (profileResponse.isSuccess) {
                    appPreference.userId = profileResponse.getOrNull()?.id!!
                    appPreference.userRoles = profileResponse.getOrNull()?.roles
                    appPreference.userAvatar = profileResponse.getOrNull()?.avatar
                    appPreference.userName = profileResponse.getOrNull()?.name
                    appPreference.userBalance = profileResponse.getOrNull()?.balance ?: 0
                    Log.d("AuthRepositoryImpl", "loginWithGoogle: ${appPreference.userRoles}")
                } else {
                    Log.d(
                        "AuthRepositoryImpl",
                        "loginWithGoogle: ${profileResponse.exceptionOrNull()}"
                    )
                }

                return Result.success(tokenResponse)
            }
        }
        val errorResponse = result.errorBody()?.string()
        if (errorResponse != null) {
            return try {
                val error = Gson().fromJson(errorResponse, ErrorResponse::class.java)
                // Log.d("AuthRepositoryImpl", "login: ${error.errors}")
                error.errors?.forEach { (field, messages) ->
                    messages.forEach { message ->
                        //    Log.d("AuthRepositoryImpl", "Field: $field, Error: $message")
                    }
                }
                return error.errors?.let { Result.failure(Exception(Gson().toJson(error))) }!!
            } catch (e: Exception) {
                Result.failure(Exception("Login  failed"))
            }
        }

        return Result.failure(Exception("Login failed"))
    }

    override suspend fun register(
        username: String,
        password: String,
        fullName: String,
        confirmPassword: String,
        email: String,
        birthday: String
    ): Result<TokenResponse> {
        val result = api.register(
            RegisterRequest(
                username,
                password,
                fullName,
                confirmPassword,
                email,
                birthday
            )
        )
        if (result.isSuccessful) {
            appPreference.authToken = result.body()?.token
            appPreference.isUserLoggedIn = true
            subscribeToNotification(result.body()?.token!!, appPreference.fcmToken!!)
            val profileResponse = profileRepository.getProfile(result.body()?.token!!)
            if (profileResponse.isSuccess) {
                appPreference.userId = profileResponse.getOrNull()?.id!!
                appPreference.userRoles = profileResponse.getOrNull()?.roles
                appPreference.userAvatar = profileResponse.getOrNull()?.avatar
                appPreference.userName = profileResponse.getOrNull()?.name
                appPreference.userBalance = profileResponse.getOrNull()?.balance ?: 0
                Log.d("AuthRepositoryImpl", "loginWithGoogle: ${appPreference.userRoles}")
            } else {
                Log.d(
                    "AuthRepositoryImpl",
                    "loginWithGoogle: ${profileResponse.exceptionOrNull()}"
                )
            }

            return Result.success(result.body()!!)
        }


        val errorResponse = result.errorBody()?.string()
        if (errorResponse != null) {
            return try {
                val error = Gson().fromJson(errorResponse, ErrorResponse::class.java)
                // Log.d("AuthRepositoryImpl", "login: ${error.errors}")
                error.errors?.forEach { (field, messages) ->
                    messages.forEach { message ->
                        //    Log.d("AuthRepositoryImpl", "Field: $field, Error: $message")
                    }
                }
                return error.errors?.let { Result.failure(Exception(Gson().toJson(error))) }!!
            } catch (e: Exception) {
                Result.failure(Exception("Register failed"))
            }
        }

        Log.d("AuthRepositoryImpl", "register: ${result.errorBody()?.string()}")
        return failure(Exception("Register failed"))
    }

    override suspend fun getUserInfo(token: String): Result<UserInfoResponse> {
        val result = api.getUserInfo("Bearer $token")
        if (result.isSuccessful) {
            return Result.success(result.body()!!)
        }
        return failure(Exception("Get user info failed"))
    }

    override suspend fun logout() {
        val result = appPreference.authToken?.let {
            appPreference.deleteUserToken()
            appPreference.deleteIsUserLoggedIn()
            appPreference.deleteUserId()
            appPreference.deleteUserRole()
            appPreference.deleteUserAvatar()
            appPreference.deleteUserName()
            appPreference.deleteUserBalance()
            appPreference.deleteIsLoginWithGoogle()
            appPreference.deleteSearchHistory()
            unsubscribeToNotification(it, appPreference.fcmToken!!)
        }
        if (result != null) {
            Log.d("AuthRepositoryImpl", "logout: Successfully!!!")
        }
    }



    private suspend fun subscribeToNotification(token: String, fcmToken: String) {
        notificationRepository.subscribeToNotification(token, fcmToken)
    }

    private suspend fun unsubscribeToNotification(token: String, fcmToken: String) {
        notificationRepository.unsubscribeToNotification(token, fcmToken)
    }
}