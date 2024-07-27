package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.TransactionHistory

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.TransactionHistoryResponse

data class TransactionHistoryState(
    val error: String? = null,
    val listTransactionHistory: List<TransactionHistoryResponse> = emptyList(),
    val isLoadingTransactionHistory : Boolean = true,
    val currentPage: MutableState<Int> = mutableStateOf(0),
    val totalPages: MutableState<Int> = mutableStateOf(0),
    val page: Int = 1,
    val size: Int = 9,
)