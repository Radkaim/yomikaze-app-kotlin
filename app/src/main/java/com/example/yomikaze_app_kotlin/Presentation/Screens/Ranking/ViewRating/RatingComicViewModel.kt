package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewRating

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByRatingRankingUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingComicViewModel @Inject constructor(
    private val appPreference: AppPreference,
    private val getComicByRatingRankingUC: GetComicByRatingRankingUC

) : ViewModel() {
    private val _state = MutableStateFlow(RatingComicState())
    val state: StateFlow<RatingComicState> get() = _state

    //navController
    private var navController: NavController? = null

    //set navController
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    //navigate to comic detail
    fun navigateToComicDetail(comicId: Long) {
        navController?.navigate("comic_detail_route/$comicId")
    }

    // Reset state
    private fun resetState() {
        _state.value = RatingComicState()
    }


    override fun onCleared() {
        super.onCleared()
        // Reset page and size if needed
        resetState()
    }


    fun getComicByRatingRanking(page: Int? = 1) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!

            val size = _state.value.size

            val currentPage = _state.value.currentPage.value
            val totalPages = _state.value.totalPages.value

            if (currentPage >= totalPages && totalPages != 0) return@launch

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
                        listComicByRatingRanking = state.value.listComicByRatingRanking + results,
                    )
                    _state.value.currentPage.value = baseResponse.currentPage
                    _state.value.totalPages.value = baseResponse.totalPages
                    _state.value = _state.value.copy(isLoading = false)
                },
                onFailure = { exception ->
                    // Xử lý kết quả thất bại
                    _state.value = _state.value.copy(isLoading = false)
                    Log.e("RatingComicViewModel", exception.message.toString())
                }
            )
        }
    }

}