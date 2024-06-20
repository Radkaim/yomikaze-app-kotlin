package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ResetPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.UseCase.ChangePasswordUseCase
import com.example.yomikaze_app_kotlin.Domain.UseCase.ResetPasswordUseCase
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ChangePassword.ChangePasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
): ViewModel(){
    private val _state = MutableStateFlow(ResetPasswordState())
    private var navController: NavController? = null
    val state: StateFlow<ResetPasswordState> get() = _state
    fun setNavController(navController: NavController) {
        this.navController = navController
    }


}