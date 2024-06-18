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

}
