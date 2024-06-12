package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
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
    application: Application
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state
    private var navController: NavController? = null


    init {
//        _state.value = _state.value.copy(isNetworkAvailable = true)
//        networkMonitor.registerCallback(
//            onAvailable = {
                viewModelScope.launch {
                    fetchImages()
                    checkUserToken()
                }
//            },
//            onLost = {
                // Xử lý khi mất kết nối nếu cần
//                networkAvailable()
//            }
//        )
    }
//    fun networkAvailable(){
//        networkMonitor.registerCallback(
//            onAvailable = {
//                _state.value = _state.value.copy(isNetworkAvailable = true)
//            },
//            onLost = {
//                    _state.value = _state.value.copy(isNetworkAvailable = false)
//            }
//        )
//    }


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

    fun checkUserToken() {
        appPreference.authToken = "Hung is here" // test đã đăng nhập
        // appPreference.deleteUserToken() // chưa đăng nhập
        val token = appPreference.authToken

        // Log.d("HomeViewModel", "User token: $token")
        if (token != null) {
            _state.value = _state.value.copy(isUserLoggedIn = true)
        } else {
            _state.value = _state.value.copy(isUserLoggedIn = false)
        }
    }


    /**
     * Todo: Implement navigation functions
     */
    fun onViewMoreHistoryClicked() {
        navController?.navigate("bookcase_route/0")
    }

    fun onViewRankingMore() {
        navController?.navigate("ranking_route/0")
    }

}