package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewFollow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetComicByFollowRankingUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowComicViewModel @Inject constructor(
    private val getComicByFollowRankingUC: GetComicByFollowRankingUC,
    appPreference: AppPreference
):ViewModel() {
    private val _state = MutableStateFlow(FollowComicState())
    val state: StateFlow<FollowComicState> get() = _state

    //navController
    private var navController: NavController? = null
    private val appPreference = appPreference

    //set navController
    fun setNavController(navController: NavController){
        this.navController = navController
    }

    //navigate to comic detail
    fun navigateToComicDetail(comicId: Long){
        navController?.navigate("comic_detail_route/$comicId")
    }

    fun getComicByFollowRanking(){
        viewModelScope.launch {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = getComicByFollowRankingUC.getComicByFollowRanking(token)
            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(listComicByFollowRanking = results)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                }
            )
            Log.d("NotificationViewModel", "searchComic: $result")
        }
    }

}