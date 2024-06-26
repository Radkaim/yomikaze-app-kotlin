package com.example.yomikaze_app_kotlin.Presentation.Screens.Notification

import androidx.lifecycle.ViewModel
import com.example.yomikaze_app_kotlin.Core.AppPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    appPreference: AppPreference
) : ViewModel() {

}