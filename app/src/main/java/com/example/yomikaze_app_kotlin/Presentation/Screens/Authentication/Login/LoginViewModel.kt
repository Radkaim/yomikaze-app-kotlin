package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.Models.LoginRequest
import com.example.yomikaze_app_kotlin.Domain.UseCases.LoginUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUC
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    private var navController: NavController? = null
    val state: StateFlow<LoginState> get() = _state

    fun setNavController(navController: NavController) {
        this.navController = navController
    }
    fun navigateToRegister() {
        navController?.navigate("register_route")
    }

  fun navigateToForgotPassword() {
        navController?.navigate("forgot_password_route")
    }

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
                _state.value = _state.value.copy(isLoading = false)
                _state.value = _state.value.copy(error = error.message)
                Log.d("LoginViewModel", "onLogin: $error")
            }
        }
    }
}
