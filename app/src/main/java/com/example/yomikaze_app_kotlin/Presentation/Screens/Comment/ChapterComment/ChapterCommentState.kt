package com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.ChapterComment

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ReportResponse

data class ChapterCommentState(
    //for comment
    val listChapterComment: List<CommentResponse> = emptyList(),
//    val listComicComment: MutableSet<CommentResponse> = mutableSetOf(),
    val isListChapterCommentLoading: Boolean = true,
    val totalCommentResults: MutableState<Int> = mutableStateOf(0),
    val currentPage: MutableState<Int> = mutableStateOf(0),
    val totalPages: MutableState<Int> = mutableStateOf(0),
    val page: Int = 1,
    val size: Int = 9,


    val isPostChapterCommentSuccess: Boolean = false,
    val isUpdateCommentSuccess: Boolean = true,
    val isDeleteCommentSuccess: Boolean = true,

    val listCommonCommentReportResponse: List<ReportResponse> = emptyList(),
)

