package com.example.yomikaze_app_kotlin.Core

import android.content.Context
import android.content.SharedPreferences

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