package com.example.yomikaze_app_kotlin.Core

import com.example.yomikaze_app_kotlin.ui.AppTheme
import io.paperdb.Paper

object AppPreferences {
    const val APP_THEME = "AppTheme"


    // use for save state theme
    fun setTheme(theme: AppTheme) {
        Paper.book().write(APP_THEME, theme)
    }

    fun getTheme(): AppTheme {
        return Paper.book().read(APP_THEME, AppTheme.Default) ?: AppTheme.Default
    }

}