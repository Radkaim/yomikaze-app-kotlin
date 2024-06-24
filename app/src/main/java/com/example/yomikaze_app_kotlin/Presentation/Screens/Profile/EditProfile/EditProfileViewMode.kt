package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.EditProfile

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
   // private val editProfileUseCase: ChangePasswordUseCase
): ViewModel(){
    private val _state = MutableStateFlow(EditProfileState("", "","","","","","",""))
    private var navController: NavController? = null

    val state: StateFlow<EditProfileState> get() = _state
    fun setNavController(navController: NavController) {
        this.navController = navController
    }
}