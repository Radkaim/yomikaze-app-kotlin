package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.Models.ErrorResponse
import com.example.yomikaze_app_kotlin.Domain.UseCases.Auth.RegisterUC
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
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

    fun navigateToLogin() {
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
        val fullNameError = "Invalid full name."
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
            _state.value = _state.value.copy(birthdayError = it)
        }

        updateErrorState(password.isBlank(), passwordError) {
            _state.value = _state.value.copy(passwordError = it)
        }

        updateErrorState(
            confirmPassword.isBlank() || confirmPassword != password,
            confirmPasswordError
        ) {
            _state.value = _state.value.copy(confirmPasswordError = it)
        }

        updateErrorState(fullName.isBlank(), fullNameError) {
            _state.value = _state.value.copy(fullNameError = it)
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result =
                registerUC.register(username, password, fullName, confirmPassword, email, birthday)
//            Log.d("RegisterViewModel", "onRegister: $result")
//            Log.d(
//                "RegisterViewModel",
//                "onRegister: $username $password $fullName $confirmPassword $email $birthday"
//            )
            _state.value = _state.value.copy(isLoading = false)
            result.onSuccess { token ->
                //handle success
                _state.value = _state.value.copy(isLoading = false)
                navController?.navigate("home_route")
            }.onFailure { error ->
                Log.d("RegisterViewModel", "onRegister: ${error.message.toString()}")

                if (error is Exception) {
                    try {


                        val errorResponse =
                            Gson().fromJson(error.message, ErrorResponse::class.java)
                        errorResponse.errors?.forEach { (field, messages) ->
                            val formattedMessages = messages.joinToString("\n")
                            when (field) {
                                "Email" -> {
                                    _state.value =
                                        _state.value.copy(emailError = (formattedMessages))
                                }

                                "Username" -> {
                                    _state.value =
                                        _state.value.copy(usernameError = (formattedMessages))
                                }

                                "FullName" -> {
                                    _state.value =
                                        _state.value.copy(fullNameError = (formattedMessages))
                                }
//                                "irthday" -> {
//                                    _state.value = _state.value.copy(birthdayError = listOf(formattedMessages))
//                                }
                                "Password" -> {
                                    _state.value =
                                        _state.value.copy(passwordError = (formattedMessages))
                                }

                                "ConfirmPassword" -> {
                                    _state.value =
                                        _state.value.copy(confirmPasswordError = formattedMessages)

                                }
                            }
                        }
                    }catch (e: JsonSyntaxException) {
                        Log.d("RegisterViewModel", "onRegister:  ${e.localizedMessage}")
                    }
                } else {
//                    _state.value = _state.value.copy(error = "Login failed")
                }
//                _state.value = _state.value.copy(isLoading = false)
//                _state.value = _state.value.copy(error = error.message)
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