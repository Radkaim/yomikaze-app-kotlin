package com.example.yomikaze_app_kotlin.Core

import android.content.Context
import android.content.SharedPreferences
import com.example.yomikaze_app_kotlin.ui.AppTheme
import io.paperdb.Paper

object AppThemeSate {
    const val APP_THEME = "AppTheme"


    // use for save state theme
    fun setTheme(theme: AppTheme) {
        Paper.book().write(APP_THEME, theme)
    }

    fun getTheme(): AppTheme {
        return Paper.book().read(APP_THEME, AppTheme.Default) ?: AppTheme.Default
    }

}
class AppPreferences(context : Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    // save user token
    var userToken: String?
        get() = preferences.getString("user_token", null)
        set(value) = preferences.edit().putString("user_token", value).apply()

    // delete user token
    fun deleteUserToken() {
        preferences.edit().remove("user_token").apply()
    }

    // save app theme
    var appTheme: String?
        get() = preferences.getString("app_theme", null)
        set(value) = preferences.edit().putString("app_theme", value).apply()

    // delete app theme
    fun deleteAppTheme() {
        preferences.edit().remove("app_theme").apply()
    }
}