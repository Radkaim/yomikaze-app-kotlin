package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yomikaze_app_kotlin.Domain.UseCase.GetHotComicBannerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHotComicBannerUseCase: GetHotComicBannerUseCase

) :ViewModel () {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state

    init {
        fetchImages()
    }


    fun fetchImages() {
        viewModelScope.launch {
            _state.value = HomeState(isLoading = true)
            val result = getHotComicBannerUseCase.getHotComicBannerImages()
            _state.value = HomeState(isLoading = false)
            result.onSuccess { images ->
              //get list string from images
                val imageList = images.map {it.thumbnailUrl }
                Log.d("HomeViewModel", "fetchImages: ${imageList.take(5)}")
                //get 5 images api 5000 images testcase
                _state.value = HomeState(images = imageList.take(5))
            }.onFailure {
                _state.value = HomeState(error = it.message)
            }
        }
    }
}