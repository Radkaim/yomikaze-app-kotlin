package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ChangePassword

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.ChangePasswordRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ErrorResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LoginRequest
import com.example.yomikaze_app_kotlin.Domain.UseCases.Auth.LoginUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Profile.ChangePasswordUC
import com.google.gson.Gson
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
    fun changePass(currentPassword: String, newpassword: String) {
        _state.value = _state.value.copy(isLoading = true)
        if (currentPassword.isBlank()) {
            _state.value = _state.value.copy(currentPasswordError = "Invalid password.")
        } else {
            _state.value = _state.value.copy(currentPasswordError = null)
        }
        if (newpassword.isBlank()) {
            _state.value = _state.value.copy(newPasswordError = "Invalid password.")
        } else {
            _state.value = _state.value.copy(newPasswordError = null)
        }

        viewModelScope.launch {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isBlank()) {
                return@launch
            }

            _state.value = _state.value.copy(isLoading = true)
            val changePasswordRequest = ChangePasswordRequest(currentPassword, newpassword)
            val result = changePasswordUC.changePassword(token, changePasswordRequest)

            if (result.isSuccessful) {
                appPreference.deleteUserToken()
                withContext(Dispatchers.IO){
                    Log.d("ChangePassVM", "onChangePass: ${appPreference.userName!!}, $newpassword")
                    Log.d("ChangePassVM", "onChangePass: ${appPreference.userName!!}, $newpassword")


                    if (appPreference.userName == null) {
                        return@withContext
                    }

                    val loginRequest = LoginRequest(appPreference.userName!!, newpassword)
                    loginUC.login(loginRequest).onSuccess { tokenResponse ->
                        _state.value = _state.value.copy(isLoading = false)
                        _state.value = _state.value.copy(isChangePasswordSuccess = true)
                    }
                }

            } else {
                _state.value = _state.value.copy(isLoading = false)
                _state.value = _state.value.copy(isChangePasswordSuccess = false)
                Log.d("ChangePassVM", "onChangePass: ${result.errorBody()?.string()}")
                val errorResponse = Gson().fromJson(result.errorBody()?.string(), ErrorResponse::class.java)

            }
        }
    }


}
