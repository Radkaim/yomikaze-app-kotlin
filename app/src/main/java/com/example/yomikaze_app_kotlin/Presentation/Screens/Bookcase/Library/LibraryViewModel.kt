package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Presentation.Screens.Home.SearchWidgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(LibraryState())
    val state: StateFlow<LibraryState> get() = _state

    private var navController: NavController? = null

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

    fun updateSearchResult(newSearchResult: List<ComicResponse>) {
        _state.value = _state.value.copy(searchResult = mutableStateOf(newSearchResult))
    }

    fun updateSearchWidgetState(newState: SearchWidgetState) {
        _searchWidgetState.value = newState
    }

    fun updateSearchText(newValue: String) {
        _searchTextState.value = newValue
    }

//    @SuppressLint("SuspiciousIndentation")
//    fun searchComic(comicNameQuery: String) {
//        viewModelScope.launch {
//            val token =
//                if (appPreference.authToken == null) "" else appPreference.authToken!!
//            val result = searchComicUC.searchComic(token, comicNameQuery)
//
//            result.fold(
//                onSuccess = { baseResponse ->
//                    val results = baseResponse.results
//                    // Xử lý kết quả thành công
//                    _state.value.searchResult.value = results
//
//                },
//                onFailure = { exception ->
//                    // Xử lý lỗi
//                }
//            )
//            Log.d("NotificationViewModel", "searchComic: $result")
//        }
//    }


}