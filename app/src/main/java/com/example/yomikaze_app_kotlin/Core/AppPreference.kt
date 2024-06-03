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

    fun resetTheme() {
        Paper.book().delete(APP_THEME)
    }

    fun getTheme(): AppTheme {
        return Paper.book().read(APP_THEME, AppTheme.DEFAULT) !!
    }

}

class AppPreference(context : Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    // save user token
    var authToken: String?
        get() = preferences.getString("auth_token", null)
        set(value) = preferences.edit().putString("auth_token", value).apply()

    // delete user token
    fun deleteUserToken() {
        preferences.edit().remove("auth_token").apply()
    }

}