package com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.ChapterComment

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
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment.GetAllChapterCommentUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment.PostChapterCommentUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment.ReactChapterCommentUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment.UpdateChapterCommentUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.Common.GetCommonCommentReportReasonsUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.Common.ReportCommentUC
import com.example.yomikaze_app_kotlin.Presentation.Screens.BaseModel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChapterCommentViewModel @Inject constructor(
    private val appPreference: AppPreference,
    private val getAllChapterCommentUC: GetAllChapterCommentUC,
    private val postChapterCommentUC: PostChapterCommentUC,
    private val deleteChapterCommentUC: DeleteChapterCommentUC,
    private val updateChapterCommentUC: UpdateChapterCommentUC,

    private val reactChapterCommentUC: ReactChapterCommentUC,

    private val reportCommentUC: ReportCommentUC,
    private val getCommonCommentReportReasonsUC: GetCommonCommentReportReasonsUC
) : ViewModel(), StatefulViewModel<ChapterCommentState> {
    //navController
    private var navController: NavController? = null

    private val _state = MutableStateFlow(ChapterCommentState())
    override val state: StateFlow<ChapterCommentState> get() = _state


    override val isUpdateSuccess: Boolean = _state.value.isUpdateCommentSuccess
    override val isDeleteSuccess: Boolean = _state.value.isDeleteCommentSuccess


    override fun update(key: Long, key2: Long?, key3: Int?, value: String) {
        updateChapterComment(
            comicId = key,
            commentId = key2!!,
            chapterNumber = key3!!,
            content = value
        )
    }

    override fun delete(key: Long, key2: Long?, key3:Int?, isDeleteAll: Boolean?) {
        deleteChapterComment(
            comicId = key,
            commentId = key2!!,
            chapterNumber = key3!!
        )
    }


    //set navController
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun onNavigateToReplyCommentDetail(commentId: Long, comicId: Long, chapterNumber: Int, authorName: String) {
        navController?.navigate("reply_comment_detail_route/$comicId/$commentId/$chapterNumber/$authorName")
    }

    /**
     * Todo: Implement check user is login
     */
    fun checkUserIsLogin(): Boolean {
        return appPreference.isUserLoggedIn
    }

    // remove a reply comment have deleted from list
    fun removeMainReplyCommentHasDeletedFromList(mainReplyCommentId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = _state.value.listChapterComment.toMutableList()
            list.removeIf { it.id == mainReplyCommentId }
            _state.value = _state.value.copy(listChapterComment = list)
            _state.value.totalCommentResults.value = _state.value.totalCommentResults.value - 1
            appPreference.deleteMainReplyCommentIdDeleted()
        }
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

    fun resetState() {
        _state.value = ChapterCommentState()
    }

    override fun onCleared() {
        resetState()
        super.onCleared()
    }

    /**
     * Todo: Implement get all comment of comic by comicId
     */
    fun getAllChapterComment(
        comicId: Long,
        chapterNumber: Int,
        page: Int? = 1,
        orderBy: String? = "CreationTime",
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isListChapterCommentLoading = true)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val size = _state.value.size

            val currentPage = _state.value.currentPage.value
            val totalPages = _state.value.totalPages.value

            if (currentPage >= totalPages && totalPages != 0) return@launch
            val result = getAllChapterCommentUC.getAllChapterComment(
                token = token,
                comicId = comicId,
                chapterNumber = chapterNumber,
                orderBy = orderBy,
                page = page,
                size = size
            )
            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        listChapterComment = state.value.listChapterComment + results,
                    )

                    _state.value.currentPage.value = baseResponse.currentPage
                    _state.value.totalPages.value = baseResponse.totalPages
                    _state.value.totalCommentResults.value = baseResponse.totals


                    _state.value = _state.value.copy(isListChapterCommentLoading = false)

//                    Log.d(
//                        "ChapterCommentViewModel",
//                        "listComicComment: ${state.value.listChapterComment.size}"
//                    )
                },

                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isListChapterCommentLoading = false)
                    Log.e("ChapterCommentViewModel", "getAllComicCommentByComicId: $exception")
                }
            )
        }
    }


    /**
     * Todo: Implement post comment of comic by comicId
     */
    fun postChapterComment(
        comicId: Long,
        chapterNumber: Int,
        content: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isPostChapterCommentSuccess = false)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = postChapterCommentUC.postChapterComment(
                token = token,
                comicId = comicId,
                chapterNumber  = chapterNumber,
                content = CommentRequest(content = content)
            )
            if (result.code() == 201) {
                _state.value = _state.value.copy(isPostChapterCommentSuccess = true)
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
//                Log.d("ChapterCommentViewModel", "postComicCommentByComicId: $newCommentResponse")
                val list = _state.value.listChapterComment.toMutableList()
                if (!list.any { it.id == newComment.id }) {
                    list.add(newCommentResponse)
                    _state.value = _state.value.copy(
                        // sort by creation time
                        listChapterComment = list.sortedByDescending { it.creationTime },
                        isPostChapterCommentSuccess = true,
                    )
                    _state.value.totalCommentResults.value =
                        _state.value.totalCommentResults.value + 1
                }

            } else {
                _state.value = _state.value.copy(isPostChapterCommentSuccess = false)
                Log.e("ChapterCommentViewModel", "postComicCommentByComicId: $result")
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
                val list = _state.value.listChapterComment.toMutableList()
                list.removeIf { it.id == commentId }
                _state.value = _state.value.copy(listChapterComment = list)
                _state.value.totalCommentResults.value = _state.value.totalCommentResults.value - 1

            } else {
                _state.value = _state.value.copy(isDeleteCommentSuccess = false)
//                Log.e("ChapterCommentViewModel", "deleteComicCommentByComicId: $result")
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
//            Log.d("ChapterCommentViewModel", "updateComicCommentByComicId: $content")
            val listPathRequest = listOf(
                PathRequest(content, "/content", "replace")
            )
            val result = updateChapterCommentUC.updateChapterComment(
                token = token,
                comicId = comicId,
                chapterNumber = chapterNumber,
                commentId = commentId,
                pathRequest = listPathRequest
            )
            if (result.code() == 204) {
//                Log.d("ChapterCommentViewModel", "updateComicCommentByComicId: $result")
                _state.value = _state.value.copy(isUpdateCommentSuccess = true)
                // update it content
                val list = _state.value.listChapterComment.toMutableList()
                list.forEach {
                    if (it.id == commentId) {
                        it.content = content
                    }
                }
                _state.value = _state.value.copy(listChapterComment = list)
            } else {
                _state.value = _state.value.copy(isUpdateCommentSuccess = false)
                Log.e("ChapterCommentViewModel", "updateComicCommentByComicId: $result")
            }
        }
    }

    /**
     * Todo: Implement react comic comment by comicId
     */
    fun reactChapterComment(
        comicId: Long,
        commentId: Long,
        chapterNumber: Int,
        reactionType: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val reactionRequest = ReactionRequest(reactionType)
            val result = reactChapterCommentUC.reactChapterComment(
                token = token,
                comicId = comicId,
                commentId = commentId,
                chapterNumber = chapterNumber,
                reactionRequest = reactionRequest
            )
            if (result.code() == 200 || result.code() == 204) {
//                Log.d("ChapterCommentViewModel", "reactComicCommentByComicId: $result")
                // update it content
                _state.value = _state.value.copy(
                    listChapterComment = _state.value.listChapterComment.map {
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
                Log.e("ChapterCommentViewModel", "reactComicCommentByComicId: $result")
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
                Log.e("ComicDetailViewModel", "reportComment: $result")
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