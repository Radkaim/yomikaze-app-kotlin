package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.Comic
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetHotComicBannerUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHotComicBannerUseCase: GetHotComicBannerUC,
    private val appPreference: AppPreference,
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
            getComicSearchResult("")
        }
    }

    fun  updateSearchWidgetState(newState: SearchWidgetState) {
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

    fun getComicSearchResult(searchText: String) {
//        viewModelScope.launch {
//            val result = getHotComicBannerUseCase.getComicSearchResult(searchText)
//            result.onSuccess { comics ->
//                _state.value = _state.value.copy(searchResult = comics)
//            }.onFailure {
//                _state.value = _state.value.copy(error = it.message)
//            }
//        }
        val comics = listOf(
            Comic(
                comicId = 1,
                rankingNumber = 1,
                image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
                comicName = "Hunter X Hunter",
                status = "On Going",
                authorName = "Yoshihiro Togashi",
                publishedDate = "1998-03-03",
                ratingScore = 9.5f,
                follows = 100,
                views = 100,
                comments = 100
            ),
            Comic(
                comicId = 2,
                rankingNumber = 2,
                image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
                comicName = "Hunter X Hunter12323",
                status = "On Going",
                authorName = "Yoshihiro Togashi",
                publishedDate = "1998-03-03",
                ratingScore = 9.5f,
                follows = 100,
                views = 100,
                comments = 10
            ),
            Comic(
                comicId = 3,
                rankingNumber = 3,
                image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f",
                comicName = "Hunter X Hunter12323",
                status = "On Going",
                authorName = "Yoshihiro Togashi",
                publishedDate = "1998-03-03",
                ratingScore = 9.5f,
                follows = 100,
                views = 100,
                comments = 10
            )
        )
        _state.value = _state.value.copy(searchResult = comics)
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
}