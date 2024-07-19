package com.example.yomikaze_app_kotlin.Presentation.Screens.BaseModel

import kotlinx.coroutines.flow.StateFlow

// Tạo một interface để đảm bảo tất cả ViewModel đều có thuộc tính state
interface StatefulViewModel<T> {

    val state: StateFlow<T>
    fun update(key: Long, key2: Long?, value: String)
    val isUpdateSuccess: Boolean?

    fun delete(key: Long, key2: Long? = null, isDeleteAll: Boolean? = null)
    val isDeleteSuccess: Boolean?

}