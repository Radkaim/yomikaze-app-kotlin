package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.UseCase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    private var navController: NavController? = null
    val state: StateFlow<LoginState> get() = _state

    fun onUsernameChange(newUsername: String) {
        _state.value = _state.value.copy(username = newUsername, usernameError = null)
    }

    fun onPasswordChange(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword, passwordError = null)
    }
    fun setNavController(navController: NavController) {
        this.navController = navController
    }
    fun navigateToRegister() {
        navController?.navigate("register_route")
    }

    fun onLogin(username: String, password: String) {
        val currentState = _state.value
        val username = currentState.username
        val password = currentState.password

        var hasError = false
        if (username.isBlank()) {
            _state.value = _state.value.copy(usernameError = "Invalid username.")
            hasError = true
        } else {
            _state.value = _state.value.copy(usernameError = null)
        }

        if (password.isBlank()) {
            _state.value = _state.value.copy(passwordError = "Invalid password.")
            hasError = true
        } else {
            _state.value = _state.value.copy(passwordError = null)
        }

        if (hasError) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = loginUseCase.login(username, password)
            _state.value = _state.value.copy(isLoading = false)
            result.onSuccess { token ->
                // Handle success
            }.onFailure { error ->
                _state.value = _state.value.copy(error = error.message)
            }
        }
    }
}
