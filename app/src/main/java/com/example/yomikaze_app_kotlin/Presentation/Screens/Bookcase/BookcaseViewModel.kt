package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BookcaseViewModel @Inject constructor() : ViewModel(){

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex

    fun setSelectedTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }
}