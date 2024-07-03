package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.Models.ErrorResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LoginRequest
import com.example.yomikaze_app_kotlin.Domain.Models.TokenResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import com.example.yomikaze_app_kotlin.Domain.UseCases.LoginUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.LoginWithGoogleUC
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUC,
    private val loginWithGoogleUC: LoginWithGoogleUC,
    private val repository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> get() = _state


    private var navController: NavController? = null

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun navigateToRegister() {
        navController?.navigate("register_route")
    }

    fun navigateToForgotPassword() {
        navController?.navigate("forgot_password_route")
    }

    @SuppressLint("SuspiciousIndentation")
    fun onLogin(username: String, password: String) {
        _state.value = _state.value.copy(isLoading = true)
        if (username.isBlank()) {
            _state.value = _state.value.copy(usernameError = "Invalid username.")
        } else {
            _state.value = _state.value.copy(usernameError = null)
        }
        if (password.isBlank()) {
            _state.value = _state.value.copy(passwordError = "Invalid password.")
        } else {
            _state.value = _state.value.copy(passwordError = null)
        }
        val loginRequest = LoginRequest(username, password)
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = loginUseCase.login(loginRequest)
            Log.d("LoginViewModel", "onLogin: $username, $password")
            _state.value = _state.value.copy(isLoading = false)
            result.onSuccess { token ->
                // Handle success
                _state.value = _state.value.copy(isLoading = false)
                navController?.navigate("home_route")
            }.onFailure { error ->
                if (error is Exception) {
                    val errorResponse = Gson().fromJson(error.message, ErrorResponse::class.java)
                    errorResponse.errors?.forEach { (field, messages) ->
                        messages.forEach { message ->
                            // _state.value = _state.value.copy(error = message)
                            if (field == "Username") {
                                _state.value = _state.value.copy(usernameError = message)
                            }
                            if (field == "Password") {
                                _state.value = _state.value.copy(passwordError = message)
                            }


                        }
                    }

                } else {
                    _state.value = _state.value.copy(error = "Login failed")
                }
            }
        }
    }

    fun onGoogleLogin(token: String) {
        viewModelScope.launch {
            Log.d("LoginViewModel", "onGoogleLogin1: $token")
            val result = loginWithGoogleUC.loginWithGoogle(TokenResponse(token))
            result.onSuccess { token ->
                // Handle success
                navController?.navigate("home_route")
            }.onFailure { error ->
                Log.d("LoginViewModel", "onGoogleLogin: $error")
                _state.value = _state.value.copy(error = "Login with Google failed")
            }
        }
    }
}
