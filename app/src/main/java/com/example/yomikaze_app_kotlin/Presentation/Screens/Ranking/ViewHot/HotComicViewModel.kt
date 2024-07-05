package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewHot

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetComicByViewRankingUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotComicViewModel @Inject constructor(
    private val getComicByViewRankingUC: GetComicByViewRankingUC,
    appPreference: AppPreference
) : ViewModel() {

    private val _state = MutableStateFlow(HotComicState())
    val state: StateFlow<HotComicState> get() = _state

    //navController
    private var navController: NavController? = null
    private val appPreference = appPreference

    //set navController
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    //navigate to comic detail
    fun navigateToComicDetail(comicId: Long) {
        navController?.navigate("comic_detail_route/$comicId")
    }


    // Reset state
    fun resetState() {
        _state.value = HotComicState()
    }


    fun getComicByViewRanking(page: Int? = null) {
        var finalPage = page ?: 1

        viewModelScope.launch {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            // val page = _state.value.page

            val size = _state.value.size

            val currentPage = _state.value.currentPage.value
            val totalPages = _state.value.totalPages.value
            Log.d("HotComicViewModel", "current page1: $currentPage")
            Log.d("HotComicViewModel", "total page1: $totalPages")
            if (currentPage >= totalPages && totalPages != 0) return@launch


            val result =
                getComicByViewRankingUC.getComicByViewRanking(token, "TotalViewsDesc", finalPage, size)
            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công

                    _state.value = _state.value.copy(
                        listComicByViewRanking = state.value.listComicByViewRanking + results,
//                        currentPage = baseResponse.currentPage,
//                       totalPages = baseResponse.totalPages
                    )
                    _state.value.currentPage.value = baseResponse.currentPage
                    _state.value.totalPages.value = baseResponse.totalPages
                    Log.d("HotComicViewModel", "current page2: ${state.value.currentPage}")
                    Log.d("HotComicViewModel", "total page2: ${state.value.totalPages}")


                    Log.d("HotComicViewModel", "list comic: ${_state.value.listComicByViewRanking.size}")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Reset page and size if needed
        _state.value = HotComicState()
    }

}