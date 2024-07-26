package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Core.AppThemeSate
import com.example.yomikaze_app_kotlin.Domain.UseCases.Auth.LogoutUC
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainEvent
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.ui.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUC: LogoutUC,
    private val appPreference: AppPreference,
) : ViewModel() {
    private val _state = MutableStateFlow(SettingState())
    private var navController: NavController? = null
    private var viewModel: MainViewModel? = null
    val state: StateFlow<SettingState> get() = _state
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun setMainViewModel(viewModel: MainViewModel) {
        this.viewModel = viewModel
    }
    /**
     * Todo: Implement check user is login
     */
    fun checkUserIsLogin(): Boolean {
        return appPreference.isUserLoggedIn
    }


    fun onSettingItemCLicked(route: String) {
        when (route) {
            "edit_profile_route" -> navController?.navigate("edit_profile_route")
            "reset_password_route" -> navController?.navigate("change_password_route")
            "coins_shop_route" -> navController?.navigate("coins_shop_route")
            "about_us_route" -> navController?.navigate("about_us_route")
            "transaction_history_route" -> navController?.navigate("transaction_history_route")
            "advance_search_route" -> navController?.navigate("advance_search_route/null-null-null")
        }
    }


    fun onLogout() {
        viewModelScope.launch {
            logoutUC.logout()
            if (appPreference.authToken == null) {
                navController?.navigate("home_route") {
                    popUpTo("profile_route") {
                        inclusive = true
                    }
                    Toast.makeText(
                        navController?.context,
                        "Logout successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun changeTheme() {
        val currentTheme = viewModel?.stateApp
        val newTheme = when (currentTheme?.theme) {
            AppTheme.DARK -> AppTheme.LIGHT
            AppTheme.LIGHT -> AppTheme.DARK
            AppTheme.DEFAULT -> AppTheme.DARK

            else -> {
                AppTheme.DEFAULT
            }
        }
        AppThemeSate.resetTheme()
        AppThemeSate.setTheme(newTheme)
        viewModel?.onEvent(MainEvent.ThemeChange(newTheme))
    }

}