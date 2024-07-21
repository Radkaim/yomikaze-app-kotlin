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
    var userId: Long
        get() = encryptedPreferences.getLong("user_id", 0)
        set(value) = encryptedPreferences.edit().putLong("user_id", value).apply()

    // delete user id
    fun deleteUserId() {
        encryptedPreferences.edit().remove("user_id").apply()
    }

    //save userRole
    var userRoles: List<String>?
        get() = encryptedPreferences.getStringSet("user_role", null)?.toList()
        set(value) = encryptedPreferences.edit().putStringSet("user_role", value?.toSet()).apply()

    //delete userRole
    fun deleteUserRole() {
        encryptedPreferences.edit().remove("user_role").apply()
    }

    // save user avatar
    var userAvatar: String?
        get() = encryptedPreferences.getString("user_avatar", null)
        set(value) = encryptedPreferences.edit().putString("user_avatar", value).apply()

    // delete user avatar
    fun deleteUserAvatar() {
        encryptedPreferences.edit().remove("user_avatar").apply()
    }

    //save user name
    var userName: String?
        get() = encryptedPreferences.getString("user_name", null)
        set(value) = encryptedPreferences.edit().putString("user_name", value).apply()

    // delete user name
    fun deleteUserName() {
        encryptedPreferences.edit().remove("user_name").apply()
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
