package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor() : ViewModel() {

    private var navController: NavController? = null
    fun setNavController(navController: NavController) {
        this.navController = navController
    }
}