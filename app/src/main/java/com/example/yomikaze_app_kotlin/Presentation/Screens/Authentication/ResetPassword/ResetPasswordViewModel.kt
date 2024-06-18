package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ResetPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.UseCase.ResetPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
): ViewModel(){
    private val _state = MutableStateFlow(ResetPasswordState())
    private var navController: NavController? = null

    val state: StateFlow<ResetPasswordState> get() = _state

    fun onPasswordChange(newPassword: String, newConfirmPassword: String) {
        _state.value = _state.value.copy(password = newPassword,  passwordError = null)
    }
    fun onconfirmPasswordChange(newconfirmPassword: String) {
        _state.value = _state.value.copy(confirmpassword = newconfirmPassword, confirmpasswordError = null)
    }
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun onReset(password: String, confirmPassword: String){
        val currentState = _state.value
        val password = currentState.password
        val confirmPassword = currentState.confirmpassword

        var hasError = false

        if (password.isBlank()) {
            _state.value = _state.value.copy(passwordError = "Invalid password.")
            hasError = true
        } else {
            _state.value = _state.value.copy(passwordError = null)
        }

        if (confirmPassword.isBlank() || confirmPassword != password) {
            _state.value = _state.value.copy(confirmpasswordError = "Passwords do not match.")
            hasError = true
        } else {
            _state.value = _state.value.copy(confirmpasswordError = null)
        }

        if (hasError) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = resetPasswordUseCase.resetPassword(password, confirmPassword)
            _state.value = _state.value.copy(isLoading = false)
            result.onSuccess {
                // Handle success
            }.onFailure { error ->
                _state.value = _state.value.copy(error = error.message)
            }
        }
    }
}