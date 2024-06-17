package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.History

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor() : ViewModel() {

    private var navController: NavController? = null

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun onHistoryComicClicked(chapterId: Int) {
        navController?.navigate("view_chapter_route/$chapterId")
    }
}