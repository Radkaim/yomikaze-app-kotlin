package com.example.yomikaze_app_kotlin.Presentation.Screens.Base

import kotlinx.coroutines.flow.StateFlow

// Tạo một interface để đảm bảo tất cả ViewModel đều có thuộc tính state
interface StatefulViewModel<T> {

    val state: StateFlow<T>
    fun update(key: Long, value: String)
    val isUpdateSuccess: Boolean?

    fun delete(key: Long, isDeleteAll: Boolean? = null)
    val isDeleteSuccess: Boolean?

}