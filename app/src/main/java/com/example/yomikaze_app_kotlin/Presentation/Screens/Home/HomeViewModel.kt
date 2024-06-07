package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import android.util.Log
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
    private val appPreference: AppPreference
) :ViewModel () {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state
    private var navController: NavController? = null


    init {

        fetchImages()
        checkUserToken()
    }

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun fetchImages() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = getHotComicBannerUseCase.getHotComicBannerImages()
            _state.value = _state.value.copy(isLoading = false)
            result.onSuccess { images ->
              //get list string from images
                val imageList = images.map {it.thumbnailUrl }
                Log.d("HomeViewModel", "fetchImages: ${imageList.take(5)}")
                //get 5 images api 5000 images testcase
                _state.value = _state.value.copy(images = imageList.take(5))
            }.onFailure {
                _state.value = _state.value.copy(error = it.message)
            }
        }
    }

    fun checkUserToken() {
       val token = appPreference.authToken
        Log.d("HomeViewModel", "User token: $token")
        if (token != null) {
            _state.value = _state.value.copy(isUserLoggedIn = true)
            Log.d("HomeViewModel", "User is logged in: true")
        } else {
            Log.d("HomeViewModel", "User is logged in: false")
        }
    }
    fun onViewMoreHistoryClicked(){
        //  navController?.navigate("Bookcase/history_route")
        navController?.navigate("bookcase_route/0")
    }
}