package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ChangePassword

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.UseCases.ChangePasswordUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUC
): ViewModel(){
    private val _state = MutableStateFlow(ChangePasswordState("","","","","",""))
    private var navController: NavController? = null
    val state: StateFlow<ChangePasswordState> get() = _state
    fun setNavController(navController: NavController) {
        this.navController = navController
    }


}
