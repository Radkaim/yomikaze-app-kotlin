package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.CoinShop

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.PaymentSheetRequest
import com.example.yomikaze_app_kotlin.Domain.UseCases.CoinShop.GetCoinPricingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.CoinShop.GetPaymentSheetResponseUC
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
    private val getPaymentSheetResponseUC: GetPaymentSheetResponseUC
) : ViewModel() {
    private val _state = MutableStateFlow(CoinShopState())
    val state: StateFlow<CoinShopState> get() = _state

    private var navController: NavController? = null

    // for coinShop view
    fun setNavController(navController: NavController) {
        this.navController = navController
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
    fun getPaymentSheetResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result =
                getPaymentSheetResponseUC.getPaymentSheetResponse(token, PaymentSheetRequest("71323105397571584"))
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
}