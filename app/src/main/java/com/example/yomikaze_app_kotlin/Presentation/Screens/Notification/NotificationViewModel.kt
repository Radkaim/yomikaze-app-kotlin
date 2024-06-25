package com.example.yomikaze_app_kotlin.Presentation.Screens.Notification

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.SearchComicUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val searchComicUC: SearchComicUC,
    appPreference: AppPreference
) : ViewModel() {

    private val appPreference = appPreference

    init {
        searchComic("Hunter")
    }

    @SuppressLint("SuspiciousIndentation")
    fun searchComic(comicNameQuery: String) {
        viewModelScope.launch {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
                Log.d("NotificationViewModel", "searchComic: $token")
            val result = searchComicUC.searchComic(token, comicNameQuery)

            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    Log.d("NotificationViewModel", "searchComic: $results")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                }
            )
            Log.d("NotificationViewModel", "searchComic: $result")
        }
    }
}