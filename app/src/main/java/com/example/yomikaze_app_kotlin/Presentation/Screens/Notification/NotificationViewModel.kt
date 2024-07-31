package com.example.yomikaze_app_kotlin.Presentation.Screens.Notification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetNotificationAPIUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val appPreference: AppPreference,
    private val getNotificationAPIUC: GetNotificationAPIUC
) : ViewModel() {

    //navController
    private var navController: NavController? = null

    private val _state = MutableStateFlow(NotificationState())
    val state: StateFlow<NotificationState> get() = _state


    //set navController
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

     init {
         getNotifications()
     }
    //get notification
    fun getNotifications() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isNotificationLoading = true)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!

//            val size = _state.value.size
//
//            val currentPage = _state.value.currentPage.value
//            val totalPages = _state.value.totalPages.value
//
//            if (currentPage >= totalPages && totalPages != 0) return@launch

            val result =
                getNotificationAPIUC.getNotification(token = token)
            result.fold(
                onSuccess = { results ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        listNotification = results
                    )
//                    _state.value.currentPage.value = baseResponse.currentPage
//                    _state.value.totalPages.value = baseResponse.totalPages
                    _state.value = _state.value.copy(isNotificationLoading = false)
                    Log.d("NotificationViewModel", "getNotification: $results")
//
                },
                onFailure = { exception ->
                    // Xử lý kết quả thất bại
                    _state.value = _state.value.copy(isNotificationLoading = false)
                    Log.e("NotificationViewModel", "getNotification: $exception")
                }
            )
        }
    }

}