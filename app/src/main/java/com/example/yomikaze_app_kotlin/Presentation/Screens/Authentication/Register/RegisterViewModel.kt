package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.UseCase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel(){
    private val _state = MutableStateFlow(RegisterState())
    private var navController: NavController? = null

    val state: StateFlow<RegisterState> get() = _state

    fun onEmailChange(newEmail: String){
        _state.value = _state.value.copy(email = newEmail, emailError = null)
    }
    fun onUsernameChange(newUsername: String) {
        _state.value = _state.value.copy(username = newUsername, usernameError = null)
    }
    fun ondateOfBirthChange(newDateOfBirth: String) {
        _state.value = _state.value.copy(dateOfBirth = newDateOfBirth, dateOfBirthError = null)
    }
    fun onPasswordChange(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword, passwordError = null)
    }
    fun onconfirmPasswordChange(newconfirmPassword: String) {
        _state.value = _state.value.copy(confirmpassword = newconfirmPassword, confirmpasswordError = null)
    }
    fun setNavController(navController: NavController) {
        this.navController = navController
    }
    fun navigateToRegister() {
        navController?.navigate("login_route")
    }
    fun onRegister(email: String, username: String, dateOfBirth: String, password: String, confimPassword: String){
        val currentState = _state.value
        val email = currentState.email
        val username = currentState.username
        val dateOfBirth = currentState.dateOfBirth
        val password = currentState.password
        val confirmPassword = currentState.confirmpassword

        var hasError = false

        if (email.isBlank()){
            _state.value = _state.value.copy(emailError = "Invalid email.")
            hasError = true
        } else {
            _state.value = _state.value.copy(emailError = null)
        }

        if (username.isBlank()) {
            _state.value = _state.value.copy(usernameError = "Invalid username.")
            hasError = true
        } else {
            _state.value = _state.value.copy(usernameError = null)
        }

        if (dateOfBirth.isBlank()) {
            _state.value = _state.value.copy(dateOfBirthError = "Invalid Date of Birth.")
            hasError = true
        } else {
            _state.value = _state.value.copy(dateOfBirthError = null)
        }

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
            val result = registerUseCase.register(email, username, dateOfBirth, password, confimPassword)
            _state.value = _state.value.copy(isLoading = false)
            result.onFailure { token ->
                //handle success
            }.onFailure { error ->
                _state.value = _state.value.copy(error = error.message)

            }
        }
    }
}