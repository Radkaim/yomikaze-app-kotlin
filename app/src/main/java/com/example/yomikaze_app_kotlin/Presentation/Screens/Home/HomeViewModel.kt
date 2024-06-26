package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetHotComicBannerUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.SearchComicUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHotComicBannerUseCase: GetHotComicBannerUC,
    private val appPreference: AppPreference,
    private val searchComicUC: SearchComicUC,
    application: Application
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state

    private var navController: NavController? = null


    //for search widget
    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSE)
    val searchWidgetState: MutableState<SearchWidgetState> get() = _searchWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: MutableState<String> get() = _searchTextState


    init {
        viewModelScope.launch {
            fetchImages()
            checkUserIsLogin()
        }
    }

    fun updateSearchWidgetState(newState: SearchWidgetState) {
        _searchWidgetState.value = newState
    }

    fun updateSearchText(newValue: String) {
        _searchTextState.value = newValue
    }


    // for HomeView use
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun fetchImages() {
        viewModelScope.launch {
            val result = getHotComicBannerUseCase.getHotComicBannerImages()
            result.onSuccess { images ->
                //get list string from images
                val imageList = images.map { it.thumbnailUrl }
                //get 5 images api 5000 images testcase
                _state.value = _state.value.copy(images = imageList.take(5), isLoading = false)
            }.onFailure {
                _state.value = _state.value.copy(isLoading = true, error = it.message)
            }

        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun searchComic(comicNameQuery: String) {
        viewModelScope.launch {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            Log.d("NotificationViewModel", "searchComic: $token")
            val result = searchComicUC.searchComic(token, comicNameQuery)

            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(searchResult = results)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                }
            )
            Log.d("NotificationViewModel", "searchComic: $result")
        }
    }

    /**
     * Todo: Implement check user is login
     */
    fun checkUserIsLogin(): Boolean {
        return appPreference.isUserLoggedIn
    }

    /**
     * Todo: Implement navigation functions
     */
    fun onViewMoreHistoryClicked() {
        // remove current screen from backstack

        navController?.navigate("bookcase_route/1")
    }

    fun onViewRankingMore() {
        navController?.navigate("ranking_route/0")
    }

    fun onHistoryComicClicked(chapterId: Int) {
        navController?.navigate("view_chapter_route/$chapterId")
    }

    fun onComicRankingClicked(comicId: Long) {
        navController?.navigate("comic_detail_route/$comicId")
    }

    fun onComicSearchClicked(comicId: Long) {
        navController?.navigate("comic_detail_route/$comicId")
    }
}