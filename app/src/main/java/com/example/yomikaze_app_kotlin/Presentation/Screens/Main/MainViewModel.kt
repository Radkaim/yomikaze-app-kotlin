package com.example.yomikaze_app_kotlin.Presentation.Screens.Main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.ui.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appPreference: AppPreference
): ViewModel() {

//    var stateApp by mutableStateOf(MainState())
//        private set
//
//    fun onEvent(event: MainEvent) {
//        when(event) {
//            is MainEvent.ThemeChange -> {
//              //  _stateApp.value = _stateApp.value.copy(theme = event.theme)
//                stateApp = stateApp.copy(theme = event.theme)
//            }
//        }
//    }

    var stateApp by mutableStateOf(MainState(theme = appPreference.getTheme()))
        private set

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ThemeChange -> {
                // Update the theme in state and save it to preferences
                stateApp = stateApp.copy(theme = event.theme)
                appPreference.setTheme(event.theme)
            }
        }
    }
}

sealed class MainEvent {
    data class ThemeChange(val theme: AppTheme) : MainEvent()
}

data class MainState(
    val theme: AppTheme = AppTheme.LIGHT
)

//sealed class MainEvent {
//    data class ThemeChange(val theme: AppTheme): MainEvent()
//}
//
//data class MainState(
//    val theme: AppTheme = AppThemeSate.getTheme(),
//)