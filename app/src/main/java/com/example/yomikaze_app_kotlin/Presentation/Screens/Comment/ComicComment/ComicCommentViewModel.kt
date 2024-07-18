package com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.ComicComment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.GetAllComicCommentByComicIdUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicCommentViewModel @Inject constructor(
    private val appPreference: AppPreference,
    private val getAllComicCommentByComicIdUC: GetAllComicCommentByComicIdUC
) : ViewModel() {
    //navController
    private var navController: NavController? = null

    private val _state = MutableStateFlow(ComicCommentState())
    val state: StateFlow<ComicCommentState> get() = _state

    //set navController
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    // reset state
    fun resetState() {
        _state.value = ComicCommentState()
    }
    /**
     * Todo: Implement get all comment of comic by comicId
     */
    fun getAllComicCommentByComicId(
        comicId: Long,
        page: Int? = 1,
        orderBy: String? = "CreationTimeDesc",
//        isRefresh: Boolean?,
    ) {

        viewModelScope.launch(Dispatchers.IO) {
//            if (isRefresh) {
//                _state.value = ComicCommentState()
//            }
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val size = _state.value.size

            val currentPage = _state.value.currentPage.value
            val totalPages = _state.value.totalPages.value

            if (currentPage >= totalPages && totalPages != 0) return@launch

            val result = getAllComicCommentByComicIdUC.getAllComicCommentByComicId(
                token = token,
                comicId = comicId,
                orderBy = orderBy,
                page = page,
                size = size
            )
            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        listComicComment = state.value.listComicComment + results,
                    )
                    _state.value.currentPage.value = baseResponse.currentPage
                    _state.value.totalPages.value = baseResponse.totalPages
                    _state.value.totalCommentResults.value = baseResponse.totals

                    Log.d("HotComicViewModel", "currentPage1: ${baseResponse.currentPage}")
                },

                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("HotComicViewModel", "getAllComicCommentByComicId: $exception")
                }
            )
        }
    }


}