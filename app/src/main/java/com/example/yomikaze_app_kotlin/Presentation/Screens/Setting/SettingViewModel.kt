package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.Auth.LogoutUC
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainEvent
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.ui.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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


    fun onSettingItemCLicked(route: String, context: Context) {
        when (route) {
            "login_route" -> navController?.navigate("login_route")
            "edit_profile_route" -> if (appPreference.isUserLoggedIn) {
                navController?.navigate("edit_profile_route")
            } else {
                Toast.makeText(context, "Please login to use this feature", Toast.LENGTH_SHORT)
                    .show()
            }

            "change_password_route" ->
                if (appPreference.isUserLoggedIn) {
                    if (appPreference.isLoginWithGoogle){
                        Toast.makeText(context, "This feature is not available for Google Login yet", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }
                    navController?.navigate("change_password_route")
                } else {
                    Toast.makeText(context, "Please login to use this feature", Toast.LENGTH_SHORT)
                        .show()
                }
            "coins_shop_route" -> navController?.navigate("coins_shop_route")
            "about_us_route" -> navController?.navigate("about_us_route")
            "transaction_history_route" ->
                if (appPreference.isUserLoggedIn) {
                    navController?.navigate("transaction_history_route")
                } else {
                    Toast.makeText(context, "Please login to use this feature", Toast.LENGTH_SHORT)
                        .show()
                }

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
        viewModelScope.launch(Dispatchers.IO) {
//        val currentTheme = viewModel?.stateApp
//        val newTheme = when (currentTheme?.theme) {
//            AppTheme.DARK -> AppTheme.LIGHT
//            AppTheme.LIGHT -> AppTheme.DARK
//            AppTheme.DEFAULT -> AppTheme.DARK
//
//            else -> {
//                AppTheme.DEFAULT
//            }
//        }
//        AppThemeSate.resetTheme()
//        AppThemeSate.setTheme(newTheme)
//        viewModel?.onEvent(MainEvent.ThemeChange(newTheme))


            val currentTheme = appPreference.getTheme()
            val newTheme = when (currentTheme) {
                AppTheme.DARK -> AppTheme.LIGHT
                AppTheme.LIGHT -> AppTheme.DARK
                AppTheme.DEFAULT -> AppTheme.DARK
            }

            appPreference.setTheme(newTheme)
            viewModel?.onEvent(MainEvent.ThemeChange(newTheme))
        }

    }

}