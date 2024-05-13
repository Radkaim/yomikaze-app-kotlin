package com.example.yomikaze_app_kotlin.Presentation.Home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.yomikaze_app_kotlin.Core.AppPreferences
import com.example.yomikaze_app_kotlin.ui.AppTheme

class MainViewModel: ViewModel() {

    var stateApp by mutableStateOf(MainState())

    fun onEvent(event: MainEvent) {
        when(event) {
            is MainEvent.ThemeChange -> {
                stateApp = stateApp.copy(theme = event.theme)
            }
        }
    }

}

sealed class MainEvent {
    data class ThemeChange(val theme: AppTheme): MainEvent()
}

data class MainState(
    val theme: AppTheme = AppPreferences.getTheme(),
)