package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ChangePassword

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.ChangePasswordRequest
import com.example.yomikaze_app_kotlin.Domain.Models.LoginRequest
import com.example.yomikaze_app_kotlin.Domain.UseCases.Auth.LoginUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Profile.ChangePasswordUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val appPreference: AppPreference,
    private val changePasswordUC: ChangePasswordUC,
    private val loginUC: LoginUC,
) : ViewModel() {


    private var navController: NavController? = null
    private val _state = MutableStateFlow(ChangePasswordState())
    val state: StateFlow<ChangePasswordState> get() = _state
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    init {
        appPreference.authToken?.let {
            if (it.isNotBlank()) {
                Log.d("ChangePassVM", "init: $it")
            }
        }
        Log.d("ChangePassVM", "onChangePass: ${appPreference.userName!!}")
    }

    @SuppressLint("SuspiciousIndentation")
    fun changePass(currentPassword: String, newPassword: String, context: Context) {
        if (currentPassword.isBlank() || newPassword.isBlank()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isBlank()) {
                return@launch
            }

            _state.value = _state.value.copy(isLoading = true)
            val changePasswordRequest = ChangePasswordRequest(currentPassword, newPassword)
            val result = changePasswordUC.changePassword(token, changePasswordRequest)

            if (result.isSuccessful) {
                appPreference.deleteUserToken()
                withContext(Dispatchers.IO) {

                    val loginRequest = LoginRequest(appPreference.userName!!, newPassword)
                    loginUC.login(loginRequest).onSuccess { tokenResponse ->
                        _state.value = _state.value.copy(isLoading = false)
                        _state.value = _state.value.copy(isChangePasswordSuccess = true)
                    }
                }

            } else {
                _state.value = _state.value.copy(isLoading = false)
                _state.value = _state.value.copy(isChangePasswordSuccess = false)
                result.errorBody()?.string()?.let { error ->
                    //compare contains
                    if (error != null) {
                        Log.d("ChangePassVM", "onChangePass2: $error")
                        if (error.contains("PasswordMismatch")) {
                            Toast.makeText(
                                context,
                                "Current password is incorrect",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        if (error.contains("PasswordTooShort")) {
                            Toast.makeText(
                                context,
                                "Password must be at least 6 characters",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (error.contains("PasswordTooShort")) {
                            Toast.makeText(
                                context,
                                "Password must be at least 6 characters",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (error.contains("PasswordRequiresNonAlphanumeric")) {
                            Toast.makeText(
                                context,
                                "Password must have at least one non-alphanumeric character",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        if (error.contains("PasswordRequiresUpper")) {
                            Toast.makeText(
                                context,
                                "Password must have at least one uppercase character",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }else {
                        Toast.makeText(context, "Change password failed, please try again!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


}
