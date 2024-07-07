package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry
import com.example.yomikaze_app_kotlin.Domain.UseCases.SearchInLibraryUC
import com.example.yomikaze_app_kotlin.Presentation.Screens.Home.SearchWidgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val searchInLibraryUC: SearchInLibraryUC,
    appPreference: AppPreference,
) : ViewModel() {

    private val _state = MutableStateFlow(LibraryState())
    val state: StateFlow<LibraryState> get() = _state

    private var navController: NavController? = null
    private val appPreference = appPreference

    //for search widget
    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSE)
    val searchWidgetState: MutableState<SearchWidgetState> get() = _searchWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: MutableState<String> get() = _searchTextState

    // for LibraryView use
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun updateSearchResult(newSearchResult: List<LibraryEntry>) {
        _state.value = _state.value.copy(searchResult = mutableStateOf(newSearchResult))
    }

    fun updateSearchWidgetState(newState: SearchWidgetState) {
        _searchWidgetState.value = newState
    }

    fun updateSearchText(newValue: String) {
        _searchTextState.value = newValue
    }

    @SuppressLint("SuspiciousIndentation")
    fun searchComic(comicNameQuery: String) {
        _state.value.searchResult.value = emptyList() // for clear search result for search again
        _state.value = _state.value.copy(isSearchLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!

            val result = searchInLibraryUC.searchComic(token, comicNameQuery)

            result.fold(
                onSuccess = { baseResponse ->

                    val results = baseResponse.results

                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(totalResults = baseResponse.totals)
                    _state.value.searchResult.value = results
                    _state.value = _state.value.copy(isSearchLoading = false)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isSearchLoading = false)
                    Log.d("LibraryViewModel", "searchComic: $exception")
                }
            )
        }
    }

    fun onNavigateComicDetail(comicId: Long) {
        navController?.navigate("comic_detail_route/$comicId")
    }

}