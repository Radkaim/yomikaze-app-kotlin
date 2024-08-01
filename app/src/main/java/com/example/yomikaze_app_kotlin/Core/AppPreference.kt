package com.example.yomikaze_app_kotlin.Core

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.yomikaze_app_kotlin.ui.AppTheme

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

    //fcm token
    var fcmToken: String?
        get() = encryptedPreferences.getString("fcm_token", null)
        set(value) = encryptedPreferences.edit().putString("fcm_token", value).apply()

    //delete fcm token
    fun deleteFcmToken() {
        encryptedPreferences.edit().remove("fcm_token").apply()
    }

    // save user id
    var userId: Long
        get() = encryptedPreferences.getLong("user_id", 0)
        set(value) = encryptedPreferences.edit().putLong("user_id", value).apply()

    // delete user id
    fun deleteUserId() {
        encryptedPreferences.edit().remove("user_id").apply()
    }

    //list user email
    var userEmail: List<String>?
        get() = encryptedPreferences.getStringSet("user_email", null)?.toList()
        set(value) = encryptedPreferences.edit().putStringSet("user_email", value?.toSet()).apply()

    //delete user email
    fun deleteUserEmail() {
        encryptedPreferences.edit().remove("user_email").apply()
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

    //save user balance
    var userBalance: Long
        get() = encryptedPreferences.getLong("user_balance", 0)
        set(value) = encryptedPreferences.edit().putLong("user_balance", value).apply()

    // delete user balance
    fun deleteUserBalance() {
        encryptedPreferences.edit().remove("user_balance").apply()
    }

    //isLoginWithGoogle
    var isLoginWithGoogle: Boolean
        get() = encryptedPreferences.getBoolean("is_login_with_google", false)
        set(value) = encryptedPreferences.edit().putBoolean("is_login_with_google", value).apply()

    //delete isLoginWithGoogle
    fun deleteIsLoginWithGoogle() {
        encryptedPreferences.edit().remove("is_login_with_google").apply()
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

    // save comment id when it is removed
    var mainReplyCommentIdDeleted: Long
        get() = encryptedPreferences.getLong("comment_id", 0)
        set(value) = encryptedPreferences.edit().putLong("comment_id", value).apply()

    // delete comment id
    fun deleteMainReplyCommentIdDeleted() {
        encryptedPreferences.edit().remove("comment_id").apply()
    }


    // Theme preferences
    companion object {
        private const val APP_THEME_KEY = "AppTheme"
    }

    // Save theme
    fun setTheme(theme: AppTheme) {
        encryptedPreferences.edit().putString(APP_THEME_KEY, theme.name).apply()
    }

    // Get theme
    fun getTheme(): AppTheme {
        val themeName = encryptedPreferences.getString(APP_THEME_KEY, AppTheme.LIGHT.name)
        return AppTheme.valueOf(themeName ?: AppTheme.LIGHT.name)
    }

    // Reset theme
    fun resetTheme() {
        encryptedPreferences.edit().remove(APP_THEME_KEY).apply()
    }

    //data search history, list of search history
    var searchHistory: List<String>
        get() = encryptedPreferences.getStringSet("search_history", null)?.toList() ?: emptyList()
        set(value) = encryptedPreferences.edit().putStringSet("search_history", value.toSet()).apply()

    //delete search history
    fun deleteSearchHistory() {
        encryptedPreferences.edit().remove("search_history").apply()
    }

}
