package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    appPreference: AppPreference
): ViewModel() {
    private val appPreference = appPreference

    private var navController: NavController? = null
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun onLogout() {
        appPreference.deleteUserToken()
        if(appPreference.authToken == null) {
         navController?.navigate("home_route")
        }
    }
}