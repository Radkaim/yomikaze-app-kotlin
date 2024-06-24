package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ForgotPassword

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.UseCases.ForgotPasswordUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUserCase
) : ViewModel() {
    private val _state = MutableStateFlow(ForgotPasswordState(""))
    private var navController: NavController? = null

    val state: StateFlow<ForgotPasswordState> get() = _state

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun navigateToForgotPassword() {
        navController?.navigate("forgot_password_route")
    }

    fun onForgotPassword() {
//        val currentState = _state.value
//        val email = currentState.email
//
//        var hasError = false
//
//        if (email.isBlank()) {
//            _state.value = _state.value.copy(emailError = "Invalid email.")
//            hasError = true
//        } else {
//            _state.value = _state.value.copy(emailError = null)
//        }
//
//        if (hasError) return
//
//        viewModelScope.launch {
//            _state.value = _state.value.copy(isLoading = true)
//            val result = forgotPasswordUseCase.forgotPassword(email)
//            _state.value = _state.value.copy(isLoading = false)
//            result.onSuccess {
//                // Handle success
//            }.onFailure { error ->
//                _state.value = _state.value.copy(error = error.message)
//            }
 //       }
    }
}