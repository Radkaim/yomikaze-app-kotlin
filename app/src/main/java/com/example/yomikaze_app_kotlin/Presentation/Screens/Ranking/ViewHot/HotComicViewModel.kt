package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewHot

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HotComicViewModel @Inject constructor(): ViewModel(){

    //navController
    private var navController: NavController? = null

    //set navController
    fun setNavController(navController: NavController){
        this.navController = navController
    }

    //navigate to comic detail
    fun navigateToComicDetail(comicId: Int){
        navController?.navigate("comic_detail_route/$comicId")
    }
}