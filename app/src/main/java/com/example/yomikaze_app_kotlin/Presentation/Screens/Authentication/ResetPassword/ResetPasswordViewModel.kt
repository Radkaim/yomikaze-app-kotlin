package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ResetPassword

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.UseCases.Auth.ChangePasswordUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUC
): ViewModel(){
    private val _state = MutableStateFlow(ResetPasswordState())
    private var navController: NavController? = null
    val state: StateFlow<ResetPasswordState> get() = _state
    fun setNavController(navController: NavController) {
        this.navController = navController
    }


}