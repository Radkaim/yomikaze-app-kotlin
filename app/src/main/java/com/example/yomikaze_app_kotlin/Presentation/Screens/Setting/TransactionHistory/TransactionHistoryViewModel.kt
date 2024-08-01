package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.TransactionHistory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.CoinShop.GetTransactionHistoryUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionHistoryViewModel @Inject constructor(
    private val getTransactionHistoryUC: GetTransactionHistoryUC,
    private val appPreference: AppPreference
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionHistoryState())
    val state: StateFlow<TransactionHistoryState> get() = _state

    //navController
    private var navController: NavController? = null


    //set navController
    fun setNavController(navController: NavController) {
        this.navController = navController
    }


    // Reset state
    private fun resetState() {
        _state.value = TransactionHistoryState()
    }

    override fun onCleared() {
        super.onCleared()
        // Reset page and size if needed
        resetState()
    }



    fun getTransactionHistory(page: Int? = 1) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoadingTransactionHistory = true)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {
                _state.value = _state.value.copy(isLoadingTransactionHistory = false)
                return@launch
            }
            val size = _state.value.size

            val currentPage = _state.value.currentPage.value
            val totalPages = _state.value.totalPages.value

            if (currentPage >= totalPages && totalPages != 0) return@launch

            val result = getTransactionHistoryUC.getTransactionHistory(
                token = token,
                orderBy = "DateDesc",
                page = page,
                size = size
            )
            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        listTransactionHistory = state.value.listTransactionHistory + results,
                    )
                    _state.value.currentPage.value = baseResponse.currentPage
                    _state.value.totalPages.value = baseResponse.totalPages
                    delay(200)
                    _state.value = _state.value.copy(isLoadingTransactionHistory = false)
                    Log.d("TransactionHistoryViewModel", "result: $results")

                },
                onFailure = { exception ->
                    // Xử lý kết quả thất bại
                    _state.value = _state.value.copy(isLoadingTransactionHistory = false)
                    Log.e("TransactionHistoryViewModel", exception.message.toString())
                }
            )
        }
    }


}