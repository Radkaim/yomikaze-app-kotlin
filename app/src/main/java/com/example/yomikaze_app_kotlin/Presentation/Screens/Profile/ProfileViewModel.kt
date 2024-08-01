package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.Auth.LogoutUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetUserInfoUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Profile.GetProfileUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    appPreference: AppPreference,
    private val logoutUC: LogoutUC,
    private val getUserInfoUC: GetUserInfoUC,
    private val getProfileUc: GetProfileUC
) : ViewModel() {

    private val appPreference = appPreference
    private var navController: NavController? = null

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> get() = _state
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    /**
     * Todo: Implement check user is login
     */
    fun checkUserIsLogin(): Boolean {
        return appPreference.isUserLoggedIn
    }

    fun onSettingCLicked() {
        navController?.navigate("setting_route")
    }

    fun onSignInButtonClicked() {
        navController?.navigate("login_route")
    }

//    init {
//        getUserInfo(true)
//    }

    fun getUserInfo(isNetwork: Boolean) {
        if (isNetwork) {
            viewModelScope.launch {
                val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
                val result = getUserInfoUC.getUserInfo(token)
                result.onSuccess { userInfoResponse ->
                    _state.value = _state.value.copy(isLoading = false)
                    _state.value = _state.value.copy(userInfo = userInfoResponse)
                 //   _state.value = _state.value.copy(userRole = userInfoResponse.claims.roles)
                    _state.value =
                        _state.value.copy(username = userInfoResponse.user.name)
                    Log.d("ProfileViewModel", "getUserInfo: $userInfoResponse")
                 //   Log.d("ProfileViewModel", "getUserInfo: ${userInfoResponse.claims.roles}")
                }.onFailure { error ->
                    Log.d("ProfileViewModel", "getUserInfo: $error")
                }
            }
        }
    }

    init {
        getProfile()
    }
    /**
     * Todo: Implement getProfile
     */
    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isGetProfileLoading = true)

            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {
                return@launch
            }
            val result =
                getProfileUc.getProfile(token)
            result.fold(
                onSuccess = { profileResponse ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        profileResponse = profileResponse
                    )
                    Log.d("CoinShopViewModel", "getProfile: $profileResponse")
                    _state.value = _state.value.copy(isGetProfileLoading = false)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("CoinShopViewModel", "getProfile: $exception")
                    _state.value = _state.value.copy(isGetProfileLoading = false)
                }
            )
        }
    }

}
