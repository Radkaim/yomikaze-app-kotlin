package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewRating

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetComicByRatingRankingUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingComicViewModel @Inject constructor(
    appPreference: AppPreference,
    private val getComicByRatingRankingUC: GetComicByRatingRankingUC

) :ViewModel() {
    private val _state = MutableStateFlow(RatingComicState())
    val state: StateFlow<RatingComicState> get() = _state

    //navController
    private var navController: NavController? = null
    private val appPreference = appPreference

    //set navController
    fun setNavController(navController: NavController){
        this.navController = navController
    }

    fun getComicByRatingRanking(){
        viewModelScope.launch {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = getComicByRatingRankingUC.getComicByRatingRanking(token)
            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(listComicByRatingRanking = results)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                }
            )
            Log.d("NotificationViewModel", "searchComic: $result")
        }
    }
    //navigate to comic detail
    fun navigateToComicDetail(comicId: Long){
        navController?.navigate("comic_detail_route/$comicId")
    }
}