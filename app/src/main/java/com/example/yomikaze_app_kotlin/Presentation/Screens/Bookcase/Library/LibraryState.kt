package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry

data class LibraryState(
    val error: String = "",

    val searchResult: MutableState<List<LibraryEntry>> = mutableStateOf(emptyList()),
    val totalSearchResults: Int? = 0,
    val isSearchLoading: Boolean = false,
    val isSearchResult: Boolean = false,

    //category
    val categoryList: List<LibraryCategoryResponse> = emptyList(),
    val totalCategoryResults: Int? = 0,
    val isCategoryLoading: Boolean = false,
    val comicList: List<LibraryEntry> = emptyList(),

    // danh sách các danh mục đã thêm vào truyện
    val listCateComicIsIn: List<LibraryCategoryResponse> = emptyList(),



    //create category
    val isCreateCategorySuccess: Boolean = true,
    val isUpdateCategoryNameSuccess: Boolean = true,
    val isDeleteCategorySuccess: Boolean = true,

    val imageCoverOfCate: String = "",

    val isRemoveComicFromCategorySuccess: Boolean = false,
    val isAddComicToCategoryFirstTimeSuccess: Boolean = false,
    val isAddComicToCategorySecondTimeSuccess: Boolean = false,
    val isUnFollowComicSuccess: Boolean = false,


    //default comic not in category
    val defaultComicResult: List<LibraryEntry> = emptyList(),
    val isDefaultComicResult: Boolean = false,
    val totalDefaultComicResults: Int? = 0,
    val isDefaultComicLoading: Boolean = false,


    //for follow
//    val listCateComicIsIn: List<LibraryCategoryResponse> = emptyList(),
)