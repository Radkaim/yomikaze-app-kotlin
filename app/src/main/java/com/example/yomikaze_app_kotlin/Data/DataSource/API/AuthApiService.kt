package com.example.yomikaze_app_kotlin.Data.DataSource.API

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


// Định nghĩa các request và response data classes
data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)
//email, username, dateOfBirth, password, confirmPassword
data class RegisterRequest(val email: String, val username: String, val dateOfBirth: String, val password: String, val confimPassword: String)
data class RegisterResponse(val token: String)

data class ForgotPasswordRequest(val email: String)
data class ForgotPasswordResponse(val token: String)

data class ResetPasswordRequest(val password: String, val confimPassword: String)
data class ResetPasswordResponse(val token: String)

data class ChangePasswordRequest(val oldPassword: String, val newPassword: String, val confimPassword: String)
data class ChangePasswordResponse(val token: String)



// Định nghĩa các API endpoints
interface AuthApiService {
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>


    @POST("/auth/forgotPassword")
    suspend fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @POST("/auth/resetPassword")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<ResetPasswordResponse>

    @POST("/auth/changePassword")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Response<ChangePasswordResponse>

}




