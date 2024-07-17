package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.HistoryResponse


enum class SearchWidgetState {
    OPEN,
    CLOSE
}

data class HomeState(

    val isLoadingRanking: Boolean = false,
    val images: List<String> = emptyList(),
    val error: String? = null,
    //  var searchResult: List<ComicResponse> = emptyList(), =>
    val searchResult: MutableState<List<ComicResponse>> = mutableStateOf(emptyList()),
    val isSearchLoading : Boolean = false,

    val totalResults: Int? = 0,
    val listRankingComics: List<ComicResponse> = emptyList(),

    val listComicCarousel: List<ComicResponse> = emptyList(),
    val isCoverCarouselLoading: Boolean = false,

    val isLoading: Boolean = true,


    val listHistoryRecords: List<HistoryResponse> = emptyList(),
    val isHistoryListLoading: Boolean = true,

)