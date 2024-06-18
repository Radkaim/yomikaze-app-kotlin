package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ComicDetailViewModel @Inject constructor() : ViewModel() {
    //navController
    private var navController: NavController? = null

    //set navController
    fun setNavController(navController: NavController){
        this.navController = navController
    }


    //navigate to view chapter
    fun navigateToViewChapter(chapterId: Int){
        navController?.navigate("view_chapter_route/$chapterId")
    }

}