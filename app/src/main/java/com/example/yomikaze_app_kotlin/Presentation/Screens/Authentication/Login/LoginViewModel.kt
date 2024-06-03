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

    fun onLogin(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = loginUseCase(email, password)
            _state.value = _state.value.copy(isLoading = false)
            result.onSuccess { token ->
                // Handle success
            }.onFailure { error ->
                _state.value = _state.value.copy(error = error.message)
            }
        }
    }
}