package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.LogoutUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    appPreference: AppPreference,
    private val logoutUC: LogoutUC
) : ViewModel() {
    private val appPreference = appPreference

    private var navController: NavController? = null
    fun setNavController(navController: NavController) {
        this.navController = navController
    }


    fun onEditProfile() {
        navController?.navigate("edit_profile_route")
    }

    fun onSignInButtonClicked() {
        navController?.navigate("login_route")
    }

    fun onLogout() {
        viewModelScope.launch {
            logoutUC.logout()
            if (appPreference.authToken == null){
                navController?.navigate("home_route") {
                    popUpTo("profile_route") {
                        inclusive = true
                    }
                    Toast.makeText(navController?.context, "Logout successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}