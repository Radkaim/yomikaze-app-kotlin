package com.example.yomikaze_app_kotlin.Core

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class AppPreference(context : Context) {
    // Tạo MasterKey
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    // Tạo EncryptedSharedPreferences
    private val encryptedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "encrypted_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // save user token
    var authToken: String?
        get() = encryptedPreferences.getString("auth_token", null)
        set(value) = encryptedPreferences.edit().putString("auth_token", value).apply()

    // delete user token
    fun deleteUserToken() {
        encryptedPreferences.edit().remove("auth_token").apply()
    }

    // save user id
    var userId: String?
        get() = encryptedPreferences.getString("user_id", null)
        set(value) = encryptedPreferences.edit().putString("user_id", value).apply()

    // delete user id
    fun deleteUserId() {
        encryptedPreferences.edit().remove("user_id").apply()
    }

    // save isUserLoggedIn
    var isUserLoggedIn: Boolean
        get() = encryptedPreferences.getBoolean("is_user_logged_in", false)
        set(value) = encryptedPreferences.edit().putBoolean("is_user_logged_in", value).apply()

    // delete isUserLoggedIn
    fun deleteIsUserLoggedIn() {
        encryptedPreferences.edit().remove("is_user_logged_in").apply()
    }
    // save isScrollMode
    var isScrollMode: Boolean
        get() = encryptedPreferences.getBoolean("is_scroll_mode", true)
        set(value) = encryptedPreferences.edit().putBoolean("is_scroll_mode", value).apply()

    // save orientation (true for Vertical, false for Horizontal)
    var orientation: Boolean
        get() = encryptedPreferences.getBoolean("orientation", true)
        set(value) = encryptedPreferences.edit().putBoolean("orientation", value).apply()

    var autoScrollChecked: Boolean
        get() = encryptedPreferences.getBoolean("auto_scroll_checked", false)
        set(value) = encryptedPreferences.edit().putBoolean("auto_scroll_checked", value).apply()
}
