package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse


enum class SearchWidgetState {
    OPEN,
    CLOSE
}

data class HomeState(
    val isLoading: Boolean = true,
    val isLoadingRanking: Boolean = false,
    val images: List<String> = emptyList(),
    val error: String? = null,
  //  var searchResult: List<ComicResponse> = emptyList(), =>
    val searchResult: MutableState<List<ComicResponse>> = mutableStateOf(emptyList()),

    val listRankingComics: List<ComicResponse> = emptyList()
    //val listRankingComics: MutableState<List<ComicResponse>> = mutableStateOf(emptyList()),
)