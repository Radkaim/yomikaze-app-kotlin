package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val state: StateFlow<LoginState> get() = _state

    fun onUsernameChange(newUsername: String) {
        _state.value = _state.value.copy(username = newUsername, usernameError = null)
    }

    fun onPasswordChange(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword, passwordError = null)
    }

    fun onLogin(username: String, password: String) {
        val currentState = _state.value
        val username = currentState.username
        val password = currentState.password

        if (validateCredentials(username, password)) {
            viewModelScope.launch {
                _state.value = currentState.copy(isLoading = true)
                val result = loginUseCase.login(username, password)
                _state.value = currentState.copy(isLoading = false)
                result.onSuccess { token ->
                    // Handle success
                }.onFailure { error ->
                    _state.value = currentState.copy(error = error.message)
                }
            }
        }
    }
    private fun validateCredentials(username: String, password: String): Boolean {
        var isValid = true
        var usernameError: String? = null
        var passwordError: String? = null

        if (username.isBlank()) {
            usernameError = "Username cannot be empty"
            isValid = false
        }

        if (password.isBlank()) {
            passwordError = "Password cannot be empty"
            isValid = false
        } else if (password.length < 6) {
            passwordError = "Password must be at least 6 characters long"
            isValid = false
        }

        _state.value = _state.value.copy(usernameError = usernameError, passwordError = passwordError)
        return isValid
    }
}