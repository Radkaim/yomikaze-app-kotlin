package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetComicDetailsFromApiUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicDetailViewModel @Inject constructor(
    private val getComicDetailsFromApiUC: GetComicDetailsFromApiUC,
    appPreference: AppPreference
) : ViewModel() {
    //navController
    private var navController: NavController? = null
    private val appPreference = appPreference


    private val _state = MutableStateFlow(ComicDetailState())
    val state: StateFlow<ComicDetailState> get() = _state

    //set navController
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    //navigate to view chapter
    fun navigateToViewChapter(chapterId: Int) {
        navController?.navigate("view_chapter_route/$chapterId")
    }

    fun getComicDetailsFromApi(comicId: Long) {
        viewModelScope.launch {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = getComicDetailsFromApiUC.getComicDetailsFromApi(token, comicId)
            Log.d("ComicDetailViewModel", "getComicDetailsFromApi: $result")
            Log.d("ComicDetailsViewModel", "ComicDetailsView: $comicId")
            result.fold(
                onSuccess = { comicDetailResponse ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        comicResponse = comicDetailResponse,
                        listTagGenres = comicDetailResponse.tags,
                        isLoading = false
                    )
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.d("ComicDetailsViewModel", "ComicDetailsView: $comicId")
                    Log.e("ComicDetailsViewModel", "getComicDetailsFromApi: $exception")
                }
            )
        }
    }
}