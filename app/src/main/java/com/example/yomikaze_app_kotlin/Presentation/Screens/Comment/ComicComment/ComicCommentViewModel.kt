package com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.ComicComment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.CommentRequest
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.GetAllComicCommentByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.PostComicCommentByComicIdUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicCommentViewModel @Inject constructor(
    private val appPreference: AppPreference,
    private val getAllComicCommentByComicIdUC: GetAllComicCommentByComicIdUC,
    private val postComicCommentByComicIdU: PostComicCommentByComicIdUC,
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
        _state.value = _state.value.copy(
            listComicComment = emptyList(),
            isListComicCommentLoading = true,
        )
        _state.value.currentPage.value = 0
        _state.value.totalPages.value = 0
        _state.value.totalCommentResults.value = 0
    }

    // check is that own user comment for set edit and delete button
    fun checkCanModifyComment(userId: Long): Boolean {
        val ownUserId = appPreference.userId
        val userRoles = appPreference.userRoles
        return ownUserId == userId || userRoles?.contains("Super") == true || userRoles?.contains("Administrator") == true
    }

    /**
     * Todo: Implement get all comment of comic by comicId
     */
    fun getAllComicCommentByComicId(
        comicId: Long,
        page: Int? = 1,
        orderBy: String? = "CreationTime",

        ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isListComicCommentLoading = true)
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
                    delay(1000)
                    _state.value = _state.value.copy(isListComicCommentLoading = false)

                    Log.d("HotComicViewModel", "currentPage1: ${baseResponse.currentPage}")
                },

                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isListComicCommentLoading = false)
                    Log.e("ComicCommentViewModel", "getAllComicCommentByComicId: $exception")
                }
            )
        }
    }


    /**
     * Todo: Implement post comment of comic by comicId
     */
    fun postComicCommentByComicId(
        comicId: Long,
        content: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isPostComicCommentSuccess = false)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = postComicCommentByComicIdU.postComicCommentByComicId(
                token = token,
                comicId = comicId,
                content = CommentRequest(content = content)
            )
            result.fold(
                onSuccess = { baseResponse ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        isPostComicCommentSuccess = true
                    )
                },

                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isPostComicCommentSuccess = false)
                    Log.e("ComicCommentViewModel", "postComicCommentByComicId: $exception")
                }
            )
        }
    }



}