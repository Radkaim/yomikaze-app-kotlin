package com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.RelyCommentDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.CommentRequest
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ProfileResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ReactionRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ReportRequest
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment.DeleteChapterCommentUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment.GetAllReplyChapterCommentUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment.GetMainChapterCommentUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment.PostReplyChapterCommentUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment.ReactChapterCommentUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment.UpdateChapterCommentUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.Common.GetCommonCommentReportReasonsUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.Common.ReportCommentUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.DeleteComicCommentByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.GetAllReplyCommentByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.GetMainCommentByCommentIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.PostReplyCommentByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ReactComicCommentByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.UpdateComicCommentByComicIdUC
import com.example.yomikaze_app_kotlin.Presentation.Screens.BaseModel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReplyCommentDetailViewModel @Inject constructor(
    private val appPreference: AppPreference,

    private val getAllReplyCommentByComicIdUC: GetAllReplyCommentByComicIdUC,
    private val getAllReplyChapterCommentUC: GetAllReplyChapterCommentUC,

    private val postReplyCommentByComicIdUC: PostReplyCommentByComicIdUC,
    private val postReplyChapterCommentUC: PostReplyChapterCommentUC,

    private val deleteComicCommentByComicIdUC: DeleteComicCommentByComicIdUC,
    private val updateComicCommentByComicIdUC: UpdateComicCommentByComicIdUC,
    private val deleteChapterCommentUC: DeleteChapterCommentUC,
    private val updateChapterCommentUC: UpdateChapterCommentUC,

    private val provideGetMainCommentByCommentIdUC: GetMainCommentByCommentIdUC,
    private val getMainChapterComment: GetMainChapterCommentUC,

    private val reactComicCommentByComicIdUC: ReactComicCommentByComicIdUC,
    private val reactChapterCommentUC: ReactChapterCommentUC,

    private val reportCommentUC: ReportCommentUC,
    private val getCommonCommentReportReasonsUC: GetCommonCommentReportReasonsUC
) : ViewModel(), StatefulViewModel<ReplyCommentDetailState> {
    //navController
    private var navController: NavController? = null

    private val _state = MutableStateFlow(ReplyCommentDetailState())
    override val state: StateFlow<ReplyCommentDetailState> get() = _state

    override val isUpdateSuccess: Boolean = _state.value.isUpdateCommentSuccess
    override val isDeleteSuccess: Boolean = _state.value.isDeleteCommentSuccess
    override fun update(key: Long, key2: Long?, key3: Int?, value: String) {
        if (state.value.isChapterComment) {
            updateChapterComment(
                comicId = key,
                commentId = key2!!,
                chapterNumber = key3!!,
                content = value
            )
        } else {
            updateComicCommentByComicId(
                comicId = key,
                commentId = key2!!,
                content = value
            )
        }
    }

    override fun delete(key: Long, key2: Long?, key3: Int?, isDeleteAll: Boolean?) {
        if (state.value.isChapterComment) {
//            Log.d("ReplyCommentDetailViewModel", "delete: $key, $key2, $key3")
//            Log.d("ReplyCommentDetailViewModel", "delete: ${state.value.isChapterComment}")
            deleteChapterComment(
                comicId = key,
                commentId = key2!!,
                chapterNumber = key3!!
            )
        } else {
//            Log.d("ReplyCommentDetailViewModel", "delete: ${state.value.isChapterComment}")
            deleteComicCommentByComicId(
                comicId = key,
                commentId = key2!!
            )
        }
    }


    //set navController
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    /**
     * Todo: Implement check user is login
     */
    fun checkUserIsLogin(): Boolean {
        return appPreference.isUserLoggedIn
    }

    // update isChapterComment
    fun updateIsChapterComment(isChapterComment: Boolean) {
        _state.value = _state.value.copy(isChapterComment = isChapterComment)
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
    fun checkIsOwnComment(userId: Long): Boolean {
        val ownUserId = appPreference.userId
        return ownUserId == userId
    }

    fun checkIsAdmin(): Boolean {
        val userRoles = appPreference.userRoles
        return userRoles?.contains("Super") == true || userRoles?.contains("Administrator") == true
    }

    fun resetState1() {
        _state.value = ReplyCommentDetailState()
    }


    /**
     * Todo: Implement get all comment of comic by comicId
     */
    fun getAllReplyCommentByComicId(
        comicId: Long,
        commentId: Long,
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

            val result = getAllReplyCommentByComicIdUC.getAllReplyCommentByComicId(
                token = token,
                comicId = comicId,
                commentId = commentId,
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
//                    delay(1000)
                    _state.value = _state.value.copy(isListComicCommentLoading = false)

//                    Log.d(
//                        "ReplyCommentDetailViewModel",
//                        "listComicComment: ${state.value.listComicComment}"
//                    )
                },

                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isListComicCommentLoading = false)
                    Log.e("ReplyCommentDetailViewModel", "getAllReplyCommentByComicId: $exception")
                }
            )
        }
    }

    /**
     * Todo: Implement get all replies comment of chapter
     */
    fun getAllReplyChapterComment(
        comicId: Long,
        chapterNumber: Int,
        commentId: Long,
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

            val result = getAllReplyChapterCommentUC.getAllReplyChapterComment(
                token = token,
                comicId = comicId,
                chapterNumber = chapterNumber,
                commentId = commentId,
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
                    _state.value = _state.value.copy(isListComicCommentLoading = false)
                },
                onFailure = { exception ->
                    _state.value = _state.value.copy(isListComicCommentLoading = false)
                    Log.e("ReplyCommentDetailViewModel", "getAllReplyChapterComment: $exception")
                }

            )
        }
    }

    /**
     * Todo: Implement post comment of comic by comicId
     */
    fun postReplyComicCommentByComicId(
        comicId: Long,
        commentId: Long,
        content: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isPostComicCommentSuccess = false)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = postReplyCommentByComicIdUC.postReply(
                token = token,
                comicId = comicId,
                commentId = commentId,
                content = CommentRequest(content = content)
            )
            if (result.code() == 201) {
                _state.value = _state.value.copy(isPostComicCommentSuccess = true)
                // add new item to list
                val newComment = result.body() ?: return@launch
//                Log.d("ReplyCommentDetailViewModel", "postComicCommentByComicId: $newComment")
                var newCommentResponse = newComment
                newCommentResponse.author = ProfileResponse(
                    id = appPreference.userId,
                    name = appPreference.userName!!,
                    avatar = appPreference.userAvatar,
                    balance = 0,
                    roles = appPreference.userRoles
                )
//                Log.d(
//                    "ReplyCommentDetailViewModel",
//                    "postComicCommentByComicId: $newCommentResponse"
//                )
                val list = _state.value.listComicComment.toMutableList()
                if (!list.any { it.id == newComment.id }) {
                    list.add(newCommentResponse)
                    _state.value = _state.value.copy(
                        listComicComment = list,
                        isPostComicCommentSuccess = true,
                    )
                    _state.value.totalCommentResults.value =
                        _state.value.totalCommentResults.value + 1
                }

            } else {
                _state.value = _state.value.copy(isPostComicCommentSuccess = false)
                Log.e("ReplyCommentDetailViewModel", "postComicCommentByComicId: $result")
            }
        }
    }

    /**
     * Todo: Implement post comment of comic by comicId
     */
    fun postChapterComment(
        comicId: Long,
        commentId: Long,
        chapterNumber: Int,
        content: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isPostComicCommentSuccess = false)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = postReplyChapterCommentUC.postReplyChapterComment(
                token = token,
                comicId = comicId,
                commentId = commentId,
                chapterNumber = chapterNumber,
                content = CommentRequest(content = content)
            )
            if (result.code() == 201) {
                _state.value = _state.value.copy(isPostComicCommentSuccess = true)
                // add new item to list
                val newComment = result.body() ?: return@launch
//                Log.d("ChapterCommentViewModel", "postComicCommentByComicId: $newComment")
                var newCommentResponse = newComment
                newCommentResponse.author = ProfileResponse(
                    id = appPreference.userId,
                    name = appPreference.userName!!,
                    avatar = appPreference.userAvatar,
                    balance = 0,
                    roles = appPreference.userRoles
                )
                Log.d("ChapterCommentViewModel", "postComicCommentByComicId: $newCommentResponse")
                val list = _state.value.listComicComment.toMutableList()
                if (!list.any { it.id == newComment.id }) {
                    list.add(newCommentResponse)
                    _state.value = _state.value.copy(
                        // sort by creation time
                        listComicComment = list.sortedByDescending { it.creationTime },
                        isPostComicCommentSuccess = true,
                    )
                    _state.value.totalCommentResults.value =
                        _state.value.totalCommentResults.value + 1
                }

            } else {
                _state.value = _state.value.copy(isPostComicCommentSuccess = false)
                Log.e("ChapterCommentViewModel", "postComicCommentByComicId: $result")
            }

        }
    }

    fun navigateBack() {
        navController?.popBackStack()
    }

    /**
     * Todo: Implement delete comic comment by comicId and commentId
     */
    fun deleteComicCommentByComicId(
        comicId: Long,
        commentId: Long
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isDeleteCommentSuccess = false)
            _state.value = _state.value.copy(isDeleteMainCommentSuccess = false)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = deleteComicCommentByComicIdUC.deleteComicCommentByComicId(
                token = token,
                comicId = comicId,
                commentId = commentId
            )
            if (result.code() == 204) {
                _state.value = _state.value.copy(isDeleteCommentSuccess = true)
                //remove item from list
                if (commentId != _state.value.mainComment?.id) {
                    val list = _state.value.listComicComment.toMutableList()
                    list.removeIf { it.id == commentId }
                    _state.value = _state.value.copy(listComicComment = list)
                    _state.value.totalCommentResults.value =
                        _state.value.totalCommentResults.value - 1
                } else {
                    _state.value = _state.value.copy(isDeleteMainCommentSuccess = true)
                    appPreference.mainReplyCommentIdDeleted = _state.value.mainComment!!.id
                    withContext(Dispatchers.Main) {
                        navigateBack()
                    }
                }

            } else {
                _state.value = _state.value.copy(isDeleteCommentSuccess = false)
                _state.value = _state.value.copy(isDeleteMainCommentSuccess = false)
                Log.e("ReplyCommentDetailViewModel", "deleteComicCommentByComicId: $result")
            }
        }
    }

    /**
     * Todo: Implement update comic comment by comicId and commentId
     */
    fun updateComicCommentByComicId(
        comicId: Long,
        commentId: Long,
        content: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isUpdateCommentSuccess = false)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            //log content
//            Log.d("ReplyCommentDetailViewModel", "updateComicCommentByComicId: $content")
            val listPathRequest = listOf(
                PathRequest(content, "/content", "replace")
            )
            val result = updateComicCommentByComicIdUC.updateComicComment(
                token = token,
                comicId = comicId,
                commentId = commentId,
                pathRequest = listPathRequest
            )
            if (result.code() == 204) {
//                Log.d("ReplyCommentDetailViewModel", "updateComicCommentByComicId: $result")
                _state.value = _state.value.copy(isUpdateCommentSuccess = true)
                // update it content
                val list = _state.value.listComicComment.toMutableList()
                list.forEach {
                    if (it.id == commentId) {
                        it.content = content
                    }
                }
                _state.value = _state.value.copy(listComicComment = list)
            } else {
                _state.value = _state.value.copy(isUpdateCommentSuccess = false)
                Log.e("ReplyCommentDetailViewModel", "updateComicCommentByComicId: $result")
            }
        }
    }

    /**
     * Todo: Implement delete comic comment by comicId and commentId
     */
    fun deleteChapterComment(
        comicId: Long,
        commentId: Long,
        chapterNumber: Int,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isDeleteCommentSuccess = false)
            _state.value = _state.value.copy(isDeleteMainCommentSuccess = false)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = deleteChapterCommentUC.deleteChapterComment(
                token = token,
                comicId = comicId,
                chapterNumber = chapterNumber,
                commentId = commentId
            )
            if (result.code() == 204) {
                _state.value = _state.value.copy(isDeleteCommentSuccess = true)
                //remove item from list
                if (commentId != _state.value.mainComment?.id) {
                    val list = _state.value.listComicComment.toMutableList()
                    list.removeIf { it.id == commentId }
                    _state.value = _state.value.copy(listComicComment = list)
                    _state.value.totalCommentResults.value =
                        _state.value.totalCommentResults.value - 1
                } else {
                    _state.value = _state.value.copy(isDeleteMainCommentSuccess = true)
                    appPreference.mainReplyCommentIdDeleted = _state.value.mainComment!!.id
                    withContext(Dispatchers.Main) {
                        navigateBack()
                    }
                }

            } else {
                _state.value = _state.value.copy(isDeleteCommentSuccess = false)
                _state.value = _state.value.copy(isDeleteMainCommentSuccess = false)
                Log.e("ReplyCommentDetailViewModel", "deleteComicCommentByComicId: $result")
            }
        }
    }

    /**
     * Todo: Implement update comic comment by comicId and commentId
     */
    fun updateChapterComment(
        comicId: Long,
        commentId: Long,
        chapterNumber: Int,
        content: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isUpdateCommentSuccess = false)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            //log content
//            Log.d("ReplyCommentDetailViewModel", "updateComicCommentByComicId: $content")
            val listPathRequest = listOf(
                PathRequest(content, "/content", "replace")
            )
            val result = updateChapterCommentUC.updateChapterComment(
                token = token,
                comicId = comicId,
                commentId = commentId,
                chapterNumber = chapterNumber,
                pathRequest = listPathRequest
            )
            if (result.code() == 204) {
//                Log.d("ReplyCommentDetailViewModel", "updateComicCommentByComicId: $result")
                _state.value = _state.value.copy(isUpdateCommentSuccess = true)
                // update it content
                val list = _state.value.listComicComment.toMutableList()
                list.forEach {
                    if (it.id == commentId) {
                        it.content = content
                    }
                }
                _state.value = _state.value.copy(listComicComment = list)
            } else {
                _state.value = _state.value.copy(isUpdateCommentSuccess = false)
                Log.e("ReplyCommentDetailViewModel", "updateComicCommentByComicId: $result")
            }
        }
    }


    /**
     * Todo: Implement get main comment by commentId
     */
    fun getMainCommentByCommentId(
        comicId: Long,
        commentId: Long
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = provideGetMainCommentByCommentIdUC.getMainCommentByCommentId(
                token = token,
                comicId = comicId,
                commentId = commentId
            )
            result.fold(
                onSuccess = { commentResponse ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(mainComment = commentResponse)
//                    Log.d(
//                        "ReplyCommentDetailViewModel",
//                        "getMainCommentByCommentId: $commentResponse"
//                    )
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ReplyCommentDetailViewModel", "getMainCommentByCommentId: $exception")
                }
            )
        }
    }

    /**
     * Todo: Implement get main chapter comment
     *
     */
    fun getMainChapterComment(
        comicId: Long,
        chapterNumber: Int,
        commentId: Long
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = getMainChapterComment.getMainChapterComment(
                token = token,
                comicId = comicId,
                chapterNumber = chapterNumber,
                commentId = commentId
            )
//            Log.d(
//                "ReplyCommentDetailViewModel",
//                "getMainChapterComment: $comicId, $chapterNumber, $commentId"
//            )
            result.fold(
                onSuccess = { commentResponse ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(mainComment = commentResponse)
//                    Log.d(
//                        "ReplyCommentDetailViewModel",
//                        "getMainChapterComment: $commentResponse"
//                    )
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ReplyCommentDetailViewModel", "getMainChapterComment: $exception")
                }
            )
        }
    }

    /**
     * Todo: Implement react comic comment by comicId - ComicComment
     */
    fun reactComicComment(
        comicId: Long,
        commentId: Long,
        reactionType: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val reactionRequest = ReactionRequest(reactionType)
            val result = reactComicCommentByComicIdUC.reactComicCommentByComicId(
                token = token,
                comicId = comicId,
                commentId = commentId,
                reactionRequest = reactionRequest
            )
            if (result.code() == 200 || result.code() == 204) {
//                Log.d("ComicCommentViewModel", "reactComicCommentByComicId: $result")
                // update it content
                _state.value = _state.value.copy(
                    listComicComment = _state.value.listComicComment.map {
                        if (it.id == commentId) {
                            if (reactionType == "Like") {
                                if (it.myReaction == "Like") {
                                    // Already liked, so unlike it
                                    it.copy(
                                        totalLikes = it.totalLikes - 1,
                                        myReaction = "",
                                        isReacted = false
                                    )
                                } else {
                                    // Either it was disliked or no reaction before, so like it
                                    val newTotalDislikes =
                                        if (it.myReaction == "Dislike") it.totalDislikes - 1 else it.totalDislikes
                                    it.copy(
                                        totalLikes = it.totalLikes + 1,
                                        totalDislikes = newTotalDislikes,
                                        myReaction = "Like",
                                        isReacted = true
                                    )
                                }
                            } else {
                                if (it.myReaction == "Dislike") {
                                    // Already disliked, so undislike it
                                    it.copy(
                                        totalDislikes = it.totalDislikes - 1,
                                        myReaction = "",
                                        isReacted = false
                                    )
                                } else {
                                    // Either it was liked or no reaction before, so dislike it
                                    val newTotalLikes =
                                        if (it.myReaction == "Like") it.totalLikes - 1 else it.totalLikes
                                    it.copy(
                                        totalDislikes = it.totalDislikes + 1,
                                        totalLikes = newTotalLikes,
                                        myReaction = "Dislike",
                                        isReacted = true
                                    )
                                }
                            }
                        } else {
                            it
                        }
                    }
                )
                // update main comment
                _state.value = _state.value.copy(
                    mainComment = _state.value.mainComment?.let {
                        if (it.id == commentId) {
                            if (reactionType == "Like") {
                                if (it.myReaction == "Like") {
                                    // Already liked, so unlike it
                                    it.copy(
                                        totalLikes = it.totalLikes - 1,
                                        myReaction = "",
                                        isReacted = false
                                    )
                                } else {
                                    // Either it was disliked or no reaction before, so like it
                                    val newTotalDislikes =
                                        if (it.myReaction == "Dislike") it.totalDislikes - 1 else it.totalDislikes
                                    it.copy(
                                        totalLikes = it.totalLikes + 1,
                                        totalDislikes = newTotalDislikes,
                                        myReaction = "Like",
                                        isReacted = true
                                    )
                                }
                            } else {
                                if (it.myReaction == "Dislike") {
                                    // Already disliked, so undislike it
                                    it.copy(
                                        totalDislikes = it.totalDislikes - 1,
                                        myReaction = "",
                                        isReacted = false
                                    )
                                } else {
                                    // Either it was liked or no reaction before, so dislike it
                                    val newTotalLikes =
                                        if (it.myReaction == "Like") it.totalLikes - 1 else it.totalLikes
                                    it.copy(
                                        totalDislikes = it.totalDislikes + 1,
                                        totalLikes = newTotalLikes,
                                        myReaction = "Dislike",
                                        isReacted = true
                                    )
                                }
                            }
                        } else {
                            it
                        }
                    }
                )

            } else {
                Log.e("ComicCommentViewModel", "reactComicCommentByComicId: $result")
            }
        }
    }

    /**
     * Todo: Implement react chapter comment by comicId - ChapterComment
     *
     */
    fun reactChapterComment(
        comicId: Long,
        chapterNumber: Int,
        commentId: Long,
        reactionType: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val reactionRequest = ReactionRequest(reactionType)
            val result = reactChapterCommentUC.reactChapterComment(
                token = token,
                comicId = comicId,
                chapterNumber = chapterNumber,
                commentId = commentId,
                reactionRequest = reactionRequest
            )
            if (result.code() == 200 || result.code() == 204) {
//                Log.d("ChapterReplyReact", "reactChapterCommentByComicId: $result")
                // update it content
                _state.value = _state.value.copy(
                    listComicComment = _state.value.listComicComment.map {
                        if (it.id == commentId) {
                            if (reactionType == "Like") {
                                if (it.myReaction == "Like") {
                                    // Already liked, so unlike it
                                    it.copy(
                                        totalLikes = it.totalLikes - 1,
                                        myReaction = "",
                                        isReacted = false
                                    )
                                } else {
                                    // Either it was disliked or no reaction before, so like it
                                    val newTotalDislikes =
                                        if (it.myReaction == "Dislike") it.totalDislikes - 1 else it.totalDislikes
                                    it.copy(
                                        totalLikes = it.totalLikes + 1,
                                        totalDislikes = newTotalDislikes,
                                        myReaction = "Like",
                                        isReacted = true
                                    )
                                }
                            } else {
                                if (it.myReaction == "Dislike") {
                                    // Already disliked, so undislike it
                                    it.copy(
                                        totalDislikes = it.totalDislikes - 1,
                                        myReaction = "",
                                        isReacted = false
                                    )
                                } else {
                                    // Either it was liked or no reaction before, so dislike it
                                    val newTotalLikes =
                                        if (it.myReaction == "Like") it.totalLikes - 1 else it.totalLikes
                                    it.copy(
                                        totalDislikes = it.totalDislikes + 1,
                                        totalLikes = newTotalLikes,
                                        myReaction = "Dislike",
                                        isReacted = true
                                    )
                                }
                            }
                        } else {
                            it
                        }
                    }
                )
                // update main comment
                _state.value = _state.value.copy(
                    mainComment = _state.value.mainComment?.let {
                        if (it.id == commentId) {
                            if (reactionType == "Like") {
                                if (it.myReaction == "Like") {
                                    // Already liked, so unlike it
                                    it.copy(
                                        totalLikes = it.totalLikes - 1,
                                        myReaction = "",
                                        isReacted = false
                                    )
                                } else {
                                    // Either it was disliked or no reaction before, so like it
                                    val newTotalDislikes =
                                        if (it.myReaction == "Dislike") it.totalDislikes - 1 else it.totalDislikes
                                    it.copy(
                                        totalLikes = it.totalLikes + 1,
                                        totalDislikes = newTotalDislikes,
                                        myReaction = "Like",
                                        isReacted = true
                                    )
                                }
                            } else {
                                if (it.myReaction == "Dislike") {
                                    // Already disliked, so undislike it
                                    it.copy(
                                        totalDislikes = it.totalDislikes - 1,
                                        myReaction = "",
                                        isReacted = false
                                    )
                                } else {
                                    // Either it was liked or no reaction before, so dislike it
                                    val newTotalLikes =
                                        if (it.myReaction == "Like") it.totalLikes - 1 else it.totalLikes
                                    it.copy(
                                        totalDislikes = it.totalDislikes + 1,
                                        totalLikes = newTotalLikes,
                                        myReaction = "Dislike",
                                        isReacted = true
                                    )
                                }
                            }
                        } else {
                            it
                        }
                    }
                )
            } else {
                Log.e("ChapterReplyReact", "reactComicCommentByComicId: $result")
            }
        }
    }

    /**
     * Todo: Implement report comment
     */
    fun reportComment(commentId: Long, reportReasonId: Long, reportContent: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val newReportRequest = ReportRequest(reportReasonId, reportContent)
            val result = reportCommentUC.reportComment(token, commentId, newReportRequest)
            if (result.code() == 201) {
//                Log.d("ComicDetailViewModel", "reportComment: $result")
            } else {
//                Log.e("ComicDetailViewModel", "reportComment: $result")
            }
        }
    }
    init {
        getCommonCommentReportReasons()
    }
    /**
     * Todo: Implement get common comment report reasons
     */
    fun getCommonCommentReportReasons() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCommonCommentReportReasonsUC.getCommonCommentReport()
            result.fold(
                onSuccess = { listReportResponse ->
                    // Xử lý kết quả thành công
                    _state.value =
                        _state.value.copy(listCommonCommentReportResponse = listReportResponse)
//                    Log.d("ComicDetailViewModel", "getCommonCommentReportReasons: $result")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ComicDetailViewModel", "getCommonCommentReportReasons: $exception")
                }
            )
        }
    }

}