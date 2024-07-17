package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.History

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.DeleteAllHistoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.DeleteHistoryRecordUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.GetHistoriesUC
import com.example.yomikaze_app_kotlin.Presentation.Screens.BaseModel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val appPreference: AppPreference,
    private val getHistoriesUC: GetHistoriesUC,
    private val deleteAllHistoryUC: DeleteAllHistoryUC,
    private val deleteHistoryRecordUC: DeleteHistoryRecordUC
) : ViewModel(), StatefulViewModel<HistoryState> {

    private var navController: NavController? = null

    private val _state = MutableStateFlow(HistoryState())

    //for StatefulViewModel
    override val state: StateFlow<HistoryState> get() = _state

    override val isUpdateSuccess: Boolean? = null
    override val isDeleteSuccess: Boolean = _state.value.isDeleteHistoryRecordSuccess
    //for StatefulViewModel
    override fun update(key: Long, value: String) {}

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun onHistoryComicClicked(chapterId: Long) {
        navController?.navigate("view_chapter_route/$chapterId")
    }


    // Reset state
    private fun resetState() {
        _state.value = HistoryState()
    }

    override fun onCleared() {
        super.onCleared()
        // Reset page and size if needed
        resetState()
    }

    /**
     * Todo: Implement check user is login
     */
    fun checkUserIsLogin(): Boolean {
        return appPreference.isUserLoggedIn
    }
    fun onHistoryComicClicked(comicId: Long, chapterNumber: Int) {
        navController?.navigate("view_chapter_route/$comicId/$chapterNumber")
    }


    /**
     * Delete a history record
     */
    override fun delete(key: Long, isDeleteAll: Boolean?) {
        if (isDeleteAll == true) {
            deleteAllHistoryRecords()
            return
        } else {
            deleteHistoryRecord(key)
        }
    }


    /**
     * Get all history records
     */
    fun getHistories(page: Int? = 1) {
        _state.value = _state.value.copy(isHistoryListLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            Log.d("HistoryViewModel", "getHistories:")
            val size = _state.value.size

            val currentPage = _state.value.currentPage.value
            val totalPages = _state.value.totalPages.value

            if (currentPage > totalPages && totalPages != 0) return@launch

            val result = getHistoriesUC.getHistories(token, page, size)
            result.fold(
                onSuccess = { baseResponse ->
                    // Xử lý kết quả thành công
                    val results = baseResponse.results
                    _state.value = _state.value.copy(
                        listHistoryRecords = results,
                        isHistoryListLoading = false
                    )
                    //  comic = comicDetailResponse
                    _state.value.currentPage.value = baseResponse.currentPage
                    _state.value.totalPages.value = baseResponse.totalPages
                    Log.d("HistoryViewModel", "getHistories: $results")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isHistoryListLoading = true)
                    Log.e("HistoryViewModel", "getHistories: $exception")
                }
            )
        }
    }

    /**
     * Delete all history records
     */
   private fun deleteAllHistoryRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val response = deleteAllHistoryUC.deleteAllHistories(token)
            if (response.code() == 204) {
                _state.value = _state.value.copy(
                    listHistoryRecords = emptyList()
                )
            }
        }
    }


    //delete history record
    private fun deleteHistoryRecord(historyRecordId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val response = deleteHistoryRecordUC.deleteHistoryRecord(token, historyRecordId)
            if (response.code() == 204) {
                val listHistoryRecords = _state.value.listHistoryRecords.toMutableList()
                val index = listHistoryRecords.indexOfFirst { it.id == historyRecordId }
                if (index != -1) {
                    listHistoryRecords.removeAt(index)
                    _state.value = _state.value.copy(
                        listHistoryRecords = listHistoryRecords
                    )
                }
            } else {
                Log.e("HistoryViewModel", "deleteHistoryRecord: ${response.errorBody()}")
            }
        }
    }
}