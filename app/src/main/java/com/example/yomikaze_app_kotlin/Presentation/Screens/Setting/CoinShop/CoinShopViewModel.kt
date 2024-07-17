package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.CoinShop

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.PaymentSheetRequest
import com.example.yomikaze_app_kotlin.Domain.UseCases.CoinShop.GetCoinPricingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.CoinShop.GetPaymentSheetResponseUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Profile.GetProfileUc
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinShopViewModel @Inject constructor(
    private val appPreference: AppPreference,
    private val getCoinPricingUC: GetCoinPricingUC,
    private val getPaymentSheetResponseUC: GetPaymentSheetResponseUC,
    private val getProfileUc: GetProfileUc
) : ViewModel() {
    private val _state = MutableStateFlow(CoinShopState())
    val state: StateFlow<CoinShopState> get() = _state

    private var navController: NavController? = null

    // for coinShop view
    fun setNavController(navController: NavController) {
        this.navController = navController
    }
    var coinPricingId: MutableState<Long?> = mutableStateOf(null)
    var isPaymentSheetVisible: MutableState<Boolean> = mutableStateOf(false)
    var isPaymentSuccess: MutableState<Boolean> = mutableStateOf(false)

//    init {
//        viewModelScope.launch {
//            checkUserIsLogin()
//        }
//    }
    /**
     * Todo: Implement check user is login
     */
    fun checkUserIsLogin(): Boolean {
        return appPreference.isUserLoggedIn
    }


    /**
     * Todo: Implement getCoinPricing
     */
    fun getCoinPricing(page: Int, size: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isGetCoinPricingLoading = true)

            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result =
                getCoinPricingUC.getCoinPricing(token, page, size)
            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        coinPricingList = results.asReversed(),

                        )
                    delay(1000)
                    _state.value = _state.value.copy(isGetCoinPricingLoading = false)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("CoinShopViewModel", "getCoinPricing: $exception")
                    _state.value = _state.value.copy(isGetCoinPricingLoading = false)
                }
            )

        }
    }

    /**
     * Todo: Implement getPaymentSheetResponse
     */
    fun getPaymentSheetResponse(priceId: Long) {
        _state.value = _state.value.copy(paymentSheetResponse = null)
        _state.value = _state.value.copy(isGetPaymentSheetResponseSuccess = false)
        viewModelScope.launch(Dispatchers.IO) {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result =
                getPaymentSheetResponseUC.getPaymentSheetResponse(
                    token,
                    PaymentSheetRequest(priceId)
                )
            result.fold(
                onSuccess = { paymentSheetResponse ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        paymentSheetResponse = paymentSheetResponse
                    )
                    Log.d("CoinShopViewModel", "getPaymentSheetResponse: $paymentSheetResponse")
                    _state.value = _state.value.copy(isGetPaymentSheetResponseSuccess = true)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("CoinShopViewModel", "getPaymentSheetResponse: $exception")
                    _state.value = _state.value.copy(isGetPaymentSheetResponseSuccess = false)
                }
            )
        }
    }



    /**
     * Todo: Implement getProfile
     */
    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isGetProfileLoading = true)

            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
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