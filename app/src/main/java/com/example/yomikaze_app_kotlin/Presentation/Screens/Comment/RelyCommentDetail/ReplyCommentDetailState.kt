package com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.RelyCommentDetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ReportResponse

data class ReplyCommentDetailState(
    //for comment
    val mainComment: CommentResponse? = null,
    val isDeleteMainCommentSuccess: Boolean = false,
    val listComicComment: List<CommentResponse> = emptyList(),
    val isListComicCommentLoading: Boolean = false,
    val totalCommentResults: MutableState<Int> = mutableStateOf(0),
    val currentPage: MutableState<Int> = mutableStateOf(0),
    val totalPages: MutableState<Int> = mutableStateOf(0),
    val page: Int = 1,
    val size: Int = 9,


    val isPostComicCommentSuccess: Boolean = false,
    val isUpdateCommentSuccess: Boolean = true,
    val isDeleteCommentSuccess: Boolean = true,

    val isChapterComment : Boolean = false,

    val listCommonCommentReportResponse: List<ReportResponse> = emptyList(),

    )

