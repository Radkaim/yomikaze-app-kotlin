package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewComment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking.GetComicByCommentsRankingUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentComicViewModel @Inject constructor(
    private val getComicByCommentsRankingUC: GetComicByCommentsRankingUC,
    appPreference: AppPreference
) : ViewModel() {
    private val _state = MutableStateFlow(CommentComicState())
    val state: StateFlow<CommentComicState> get() = _state

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
    private fun resetState() {
        _state.value = CommentComicState()
    }

    override fun onCleared() {
        super.onCleared()
        // Reset page and size if needed
        resetState()
    }

    fun getComicByCommentRanking(page: Int? = 1) {
        viewModelScope.launch {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!

            val size = _state.value.size

            val currentPage = _state.value.currentPage.value
            val totalPages = _state.value.totalPages.value

            if (currentPage >= totalPages && totalPages != 0) return@launch

            val result =
                getComicByCommentsRankingUC.getComicByCommentsRanking(
                    token = token,
                    orderByTotalComments = "TotalViewsDesc",
                    page = page,
                    size = size
                )
            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        listComicByCommentRanking = state.value.listComicByCommentRanking + results,
                    )
                    _state.value.currentPage.value = baseResponse.currentPage
                    _state.value.totalPages.value = baseResponse.totalPages

                },
                onFailure = { exception ->
                    // Xử lý kết quả thất bại
                    Log.e("CommentComicViewModel", exception.message.toString())
                }
            )
        }
    }
}