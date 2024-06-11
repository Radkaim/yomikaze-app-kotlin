package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Core.NetworkMonitor
import com.example.yomikaze_app_kotlin.Domain.UseCase.GetHotComicBannerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHotComicBannerUseCase: GetHotComicBannerUseCase,
    private val appPreference: AppPreference,
    private val application: Application
) :ViewModel () {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state
    private var navController: NavController? = null
    private val networkMonitor = NetworkMonitor(application)

    init {
        networkMonitor.registerCallback(
            onAvailable = {
                viewModelScope.launch {
                    _state.value = _state.value.copy(isNetworkAvailable = true)
                    fetchImages()
                    checkUserToken()
                }
            },
            onLost = {
                // Xử lý khi mất kết nối nếu cần
                _state.value = _state.value.copy(isNetworkAvailable = false)
            }
        )
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
                val imageList = images.map {it.thumbnailUrl }
                //get 5 images api 5000 images testcase
               _state.value = _state.value.copy(images = imageList.take(5),  isLoading = false)
            }.onFailure {
                _state.value = _state.value.copy(isLoading = true, error = it.message)
            }

        }
    }

    fun checkUserToken() {
        appPreference.authToken = "Hung is here"
        // appPreference.deleteUserToken()
        val token = appPreference.authToken

        Log.d("HomeViewModel", "User token: $token")
        if (token != null) {
            _state.value = _state.value.copy(isUserLoggedIn = true)
            Log.d("HomeViewModel", "User is logged in: true")
        } else {
            Log.d("HomeViewModel", "User is logged in: false")
        }
    }


    /**
     * Todo: Implement navigation functions

     */
    fun onViewMoreHistoryClicked(){
        navController?.navigate("bookcase_route/0")
    }

    fun onViewRankingMore(){
        navController?.navigate("ranking_route/0")
    }

}