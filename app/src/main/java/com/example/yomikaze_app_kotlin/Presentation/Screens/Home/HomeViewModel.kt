package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.GetHistoriesUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.SearchComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByCommentsRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByFollowRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByRatingRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByViewRankingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicWeeklyUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appPreference: AppPreference,
    private val searchComicUC: SearchComicUC,
    private val getComicByRatingRankingUC: GetComicByRatingRankingUC,
    private val getComicByCommentsRankingUC: GetComicByCommentsRankingUC,
    private val getComicByFollowRankingUC: GetComicByFollowRankingUC,
    private val getComicByViewRankingUC: GetComicByViewRankingUC,
    private val getHistoriesUC: GetHistoriesUC,
    private val getComicWeeklyUC: GetComicWeeklyUC
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


    fun updateSearchWidgetState(newState: SearchWidgetState) {
        _searchWidgetState.value = newState
    }

    fun updateSearchText(newValue: String) {
        _searchTextState.value = newValue
    }

    override fun onCleared() {
        Log.d("HomeViewModel", "onCleared: ")
        super.onCleared()
    }

    init {
        getHistories()
        getComicByViewRanking(1, 5)
        getComicWeekly()
    }

    // for HomeView use
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun updateSearchResult(newSearchResult: List<ComicResponse>) {
        _state.value = _state.value.copy(searchResult = mutableStateOf(newSearchResult))
    }

    fun updateTotalResults(newValue: Int) {
        _state.value = _state.value.copy(totalResults = newValue)
    }


    /**
     * Get all history records
     */
    fun getHistories(page: Int? = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isHistoryListLoading = true)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val orderBy = "LastModifiedDesc"
            val result = getHistoriesUC.getHistories(token, orderBy, page, 3)
            result.fold(
                onSuccess = { baseResponse ->
                    // Xử lý kết quả thành công
                    val results = baseResponse.results
                    _state.value = _state.value.copy(
                        listHistoryRecords = results,
                        isHistoryListLoading = false
                    )
//                    Log.d("HomeViewModel", "getHistories: $results")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(
                        isHistoryListLoading = true
                    )
                    Log.e("HomeViewModel", "getHistories: $exception")
                }
            )
        }
    }


    /**
     * Todo: Implement getComicByFollowRanking
     */
    fun getComicByFollowRanking(page: Int, size: Int) {
        _state.value = _state.value.copy(listRankingComics = emptyList())
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoadingRanking = true)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result =
                getComicByFollowRankingUC.getComicByFollowRanking(
                    token = token,
                    orderByTotalFollows = "TotalFollowsDesc",
                    page = page,
                    size = size
                )
            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        listRankingComics = results,
                        isLoadingRanking = false
                    )
//                    Log.d("HomeViewModel", "getComicByFollowRanking: $results")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("HomeViewModel", "getComicByFollowRanking: $exception")
                    _state.value = _state.value.copy(isLoadingRanking = true)
                }
            )

        }
    }

    /**
     * Todo: Implement get Comic for Comment Ranking Tab
     */
    fun getComicByCommentRanking(page: Int, size: Int) {
        _state.value = _state.value.copy(listRankingComics = emptyList())
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoadingRanking = true)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = getComicByCommentsRankingUC.getComicByCommentsRanking(
                token = token,
                orderByTotalComments = "TotalCommentsDesc",
                page = page,
                size = size
            )
            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        listRankingComics = results,
                        isLoadingRanking = false
                    )

                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isLoadingRanking = true)
                }
            )
        }
    }

    /**
     * Todo: Implement get Comic for View Ranking Tab
     */
    fun getComicByViewRanking(page: Int, size: Int) {
        _state.value = _state.value.copy(listRankingComics = emptyList())
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(
                isLoadingRanking = true,
                isCoverCarouselLoading = true
            )
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result =
                getComicByViewRankingUC.getComicByViewRanking(
                    token = token,
                    orderByTotalViews = "TotalViewsDesc",
                    page = page,
                    size = size
                )

            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        listRankingComics = results.take(3),
                        listComicCarousel = results,
                        isCoverCarouselLoading = false,
                        isLoadingRanking = false
                    )
                    Log.d("HomeViewModel", "getComicByRatingRanking: $results")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(
                        isLoadingRanking = true,
                        isCoverCarouselLoading = true
                    )
                }
            )
        }
    }

    /**
     * Todo: Implement get Comic for Rating Ranking Tab
     */
    fun getComicByRatingRanking(page: Int, size: Int) {
        _state.value = _state.value.copy(listRankingComics = emptyList())
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoadingRanking = true)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result =
                getComicByRatingRankingUC.getComicByRatingRanking(
                    token = token,
                    orderByAverageRatings = "AverageRatingDesc",
                    page = page,
                    size = size
                )
            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        listRankingComics = results,
                        isLoadingRanking = false
                    )
                    Log.d("HomeViewModel", "getComicByRatingRanking: $results")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("HomeViewModel", "getComicByRatingRanking: $exception")
                    _state.value = _state.value.copy(isLoadingRanking = true)
                }
            )
        }
    }


    /**
     * Todo: Implement searchComic
     */
    @SuppressLint("SuspiciousIndentation")
    fun searchComic(comicNameQuery: String) {
        _state.value.searchResult.value = emptyList() // for clear search result for search again
        _state.value = _state.value.copy(isSearchLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val size = 4
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = searchComicUC.searchComic(token, comicNameQuery, size)

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
                    Log.e("HomeViewModel", "searchComic: $exception")
                    _state.value = _state.value.copy(isSearchLoading = true)
                }
            )

        }
    }

    /**
     * TODO: use for get comic in ranking weekly
     */
    fun getComicWeekly() {
        _state.value = _state.value.copy(listComicWeekly = emptyList())
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoadingComicWeekly = true)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = getComicWeeklyUC.getComicWeekly(token)
            result.fold(
                onSuccess = { listComicWeekly ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        listComicWeekly = listComicWeekly,
                        isLoadingComicWeekly = false
                    )
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isLoadingComicWeekly = true)
                }
            )
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

    fun onViewRankingMore(tabIndex: Int) {
        navController?.navigate("ranking_route/$tabIndex")
    }

    fun onHistoryComicClicked(comicId: Long, chapterNumber: Int, lastReadPageNumber: Int? = 0) {
        navController?.navigate("view_chapter_route/$comicId/$chapterNumber/$lastReadPageNumber")
    }

    fun onNavigateComicDetail(comicId: Long) {
            navController?.navigate("comic_detail_route/$comicId")
//        navController?.navigate("comic_detail_route/$comicId")
    }

    fun onComicSearchClicked(comicId: Long) {
        navController?.navigate("comic_detail_route/$comicId")
    }

    fun onAdvanceSearchClicked(searchText: String) {
        //  Log.d("HomeViewModel", "onAdvanceSearchClicked: $searchText")
        navController?.navigate("advance_search_route/$searchText")
    }
}