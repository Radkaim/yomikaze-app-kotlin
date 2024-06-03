package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.yomikaze_app_kotlin.Core.AppThemeSate
import com.example.yomikaze_app_kotlin.ui.AppTheme

class MainViewModel: ViewModel() {
//    private val _stateApp = MutableStateFlow(MainState())
//    val stateApp: StateFlow<MainState> get() = _stateApp.asStateFlow()

    var stateApp by mutableStateOf(MainState())
        private set

    fun onEvent(event: MainEvent) {
        when(event) {
            is MainEvent.ThemeChange -> {
              //  _stateApp.value = _stateApp.value.copy(theme = event.theme)
                stateApp = stateApp.copy(theme = event.theme)
            }
        }
    }

}

sealed class MainEvent {
    data class ThemeChange(val theme: AppTheme): MainEvent()
}

data class MainState(
    val theme: AppTheme = AppThemeSate.getTheme(),
)