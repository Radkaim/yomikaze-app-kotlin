package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryResponse
import com.example.yomikaze_app_kotlin.Domain.Models.Tag

data class ComicDetailState(
    val comicResponse: ComicResponse? = null,

    val listTagGenres: List<Tag>? = emptyList(),


    val listChapters: List<Chapter>? = emptyList(),
    val isLoading: Boolean = true,

    val isRatingComicSuccess: Boolean = true,// Đổi thành Boolean thay vì MutableState<Boolean>

    //category
    val categoryList: List<LibraryCategoryResponse> = emptyList(),
    val totalCategoryResults: Int? = 0,
    val isCategoryLoading: Boolean = true,

    val isFollowComicSuccess: Boolean = true,

    //for Database
    val isComicExistInDB: Boolean = false,

    //for comment
    val listComicComment: List<CommentResponse>? = emptyList(),
    val isListComicCommentLoading: Boolean = true,

    // continue reading
//    val continueReadingResponse: ContinueReadingResponse? = null,

    )