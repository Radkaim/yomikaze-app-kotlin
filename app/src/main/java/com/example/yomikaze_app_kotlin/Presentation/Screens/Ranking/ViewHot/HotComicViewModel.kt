package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewHot

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByViewRankingUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotComicViewModel @Inject constructor(
    private val getComicByViewRankingUC: GetComicByViewRankingUC,
    private val appPreference: AppPreference
) : ViewModel() {

    private val _state = MutableStateFlow(HotComicState())
    val state: StateFlow<HotComicState> get() = _state

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
        _state.value = HotComicState()
    }

    override fun onCleared() {
        super.onCleared()
        // Reset page and size if needed
        resetState()
    }


    fun getComicByViewRanking(page: Int? = 1) {
        viewModelScope.launch {

            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!

            val size = _state.value.size

            val currentPage = _state.value.currentPage.value
            val totalPages = _state.value.totalPages.value

            if (currentPage >= totalPages && totalPages != 0) return@launch

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
                        listComicByViewRanking = state.value.listComicByViewRanking + results,
                    )
                    _state.value.currentPage.value = baseResponse.currentPage
                    _state.value.totalPages.value = baseResponse.totalPages
                    delay(200)
                    _state.value = _state.value.copy(isLoadingHot = false)
//                    Log.d("HotComicViewModel", "result: $results")

                },
                onFailure = { exception ->
                    // Xử lý kết quả thất bại
                    _state.value = _state.value.copy(isLoadingHot = false)
                    Log.e("HotComicViewModel", exception.message.toString())
                }
            )
        }
    }
}