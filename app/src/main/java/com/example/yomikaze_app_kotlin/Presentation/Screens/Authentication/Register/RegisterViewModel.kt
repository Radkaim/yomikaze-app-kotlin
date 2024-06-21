package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.UseCase.RegisterUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUC: RegisterUC
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    private var navController: NavController? = null

    val state: StateFlow<RegisterState> get() = _state

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun navigateToRegister() {
        navController?.navigate("login_route")
    }

    fun onRegister(
        username: String,
        password: String,
        fullName: String,
        confirmPassword: String,
        email: String,
        birthday: String
    ) {
        val emailError = "Invalid email."
        val usernameError = "Invalid username."
        val dateOfBirthError = "Invalid Date of Birth."
        val passwordError = "Invalid password."
        val confirmPasswordError = "Passwords do not match."

        updateErrorState(email.isBlank(), emailError) {
            _state.value = _state.value.copy(emailError = it)
        }

        updateErrorState(username.isBlank(), usernameError) {
            _state.value = _state.value.copy(usernameError = it)
        }

        updateErrorState(birthday.isBlank(), dateOfBirthError) {
            _state.value = _state.value.copy(dateOfBirthError = it)
        }

        updateErrorState(password.isBlank(), passwordError) {
            _state.value = _state.value.copy(passwordError = it)
        }

        updateErrorState(confirmPassword.isBlank() || confirmPassword != password, confirmPasswordError) {
            _state.value = _state.value.copy(confirmPasswordError = it)
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result =
                registerUC.register(username, password, fullName, confirmPassword, email, birthday)
            Log.d("RegisterViewModel", "onRegister: $result")
            Log.d("RegisterViewModel", "onRegister: $username $password $fullName $confirmPassword $email $birthday")
            result.onSuccess { token ->
                //handle success
                _state.value = _state.value.copy(isLoading = false)
                navController?.navigate("home_route")
            }.onFailure { error ->
                Log.d("RegisterViewModel", "onRegister: ${error.message}")
                _state.value = _state.value.copy(isLoading = false)
                _state.value = _state.value.copy(error = error.message)
            }
        }
    }

    private fun updateErrorState(
        condition: Boolean,
        errorMessage: String?,
        updateState: (String?) -> Unit
    ) {
        if (condition) {
            updateState(errorMessage)
        } else {
            updateState(null)
        }
    }
}