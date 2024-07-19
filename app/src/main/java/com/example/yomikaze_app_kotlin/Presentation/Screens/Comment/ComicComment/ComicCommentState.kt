package com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.ComicComment

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse

data class ComicCommentState(
    //for comment
    val listComicComment: List<CommentResponse> = emptyList(),
    val isListComicCommentLoading: Boolean = true,
    val totalCommentResults: MutableState<Int> = mutableStateOf(0),
    val currentPage: MutableState<Int> = mutableStateOf(0),
    val totalPages: MutableState<Int> = mutableStateOf(0),
    val page: Int = 1,
    val size: Int = 9,


    val isPostComicCommentSuccess: Boolean = false,
    val isUpdateCommentSuccess: Boolean = true,
    val isDeleteCommentSuccess: Boolean = true,
)

