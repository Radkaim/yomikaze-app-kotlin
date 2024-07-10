package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.History

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.HistoryResponse

data class HistoryState(
    val listHistoryRecords: List<HistoryResponse> = emptyList(),
    val isHistoryListLoading: Boolean = true,
    val isDeleteHistoryRecordSuccess: Boolean = true,
    val currentPage: MutableState<Int> = mutableStateOf(0),
    val totalPages: MutableState<Int> = mutableStateOf(0),
    val page: Int = 1,
    val size: Int = 7,
)