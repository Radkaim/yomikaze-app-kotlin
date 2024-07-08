package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryRequest
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.CreateLibraryCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetLibraryCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.SearchInLibraryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.UpdateCateNameUC
import com.example.yomikaze_app_kotlin.Presentation.Screens.Base.StatefulViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Home.SearchWidgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val searchInLibraryUC: SearchInLibraryUC,
    private val appPreference: AppPreference,
    private val getLibraryCategoryUC: GetLibraryCategoryUC,
    private val createLibraryCategoryUC: CreateLibraryCategoryUC,
    private val updateCateNameUC: UpdateCateNameUC,
) : ViewModel(), StatefulViewModel<LibraryState> {

    private val _state = MutableStateFlow(LibraryState())
    private var navController: NavController? = null

    //for StatefulViewModel
    override val state: StateFlow<LibraryState> get() = _state

    override val isUpdateSuccess: Boolean = _state.value.isUpdateCategoryNameSuccess


    //for search widget
    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSE)
    val searchWidgetState: MutableState<SearchWidgetState> get() = _searchWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: MutableState<String> get() = _searchTextState

    // for LibraryView use
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun updateSearchResult(newSearchResult: List<LibraryEntry>) {
        _state.value = _state.value.copy(searchResult = mutableStateOf(newSearchResult))
    }

    fun updateSearchWidgetState(newState: SearchWidgetState) {
        _searchWidgetState.value = newState
    }

    fun updateSearchText(newValue: String) {
        _searchTextState.value = newValue
    }

    fun updateTotalResults(newValue: Int) {
        _state.value = _state.value.copy(totalSearchResults = newValue)
    }

    @SuppressLint("SuspiciousIndentation")
    fun searchComic(comicNameQuery: String) {
        _state.value.searchResult.value = emptyList() // for clear search result for search again
        _state.value = _state.value.copy(isSearchLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!

            val result = searchInLibraryUC.searchComic(token, comicNameQuery)

            result.fold(
                onSuccess = { baseResponse ->

                    val results = baseResponse.results

                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(totalSearchResults = baseResponse.totals)
                    _state.value.searchResult.value = results
                    _state.value = _state.value.copy(isSearchLoading = false)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isSearchLoading = false)
                    Log.d("LibraryViewModel", "searchComic: $exception")
                }
            )
        }
    }

    /**
     * Todo: Implement get all Category
     */
    fun getAllCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isCategoryLoading = true)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!

            val result = getLibraryCategoryUC.getLibraryCategory(token)

            result.fold(
                onSuccess = { baseResponse ->

                    val results = baseResponse.results

                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(totalCategoryResults = baseResponse.totals)
                    _state.value = _state.value.copy(categoryList = results)
                    _state.value = _state.value.copy(isCategoryLoading = false)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isCategoryLoading = false)
                    Log.d("LibraryViewModel", "Get All Categories: $exception")
                }
            )
        }
    }

    /**
     * Todo: Implement add new category
     */
    fun addNewCategory(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isCreateCategorySuccess = false)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            Log.d("LibraryViewModel", "Create category: $categoryName")
            val result = createLibraryCategoryUC.createLibraryCategory(
                token,
                LibraryCategoryRequest(categoryName)
            )

            result.fold(
                onSuccess = {
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(isCreateCategorySuccess = true)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isCreateCategorySuccess = false)
                    Log.d("LibraryViewModel", "CreateCategory: $exception")
                }
            )
        }
    }

    /**
     * Todo: Implement edit category of StatefulViewModel
     */
    override fun update(key: Long, value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isUpdateCategoryNameSuccess = false)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val listPathRequest = listOf(
                PathRequest(value, "/name", "replace")
            )
            val result =
                updateCateNameUC.updateCateName(token, key, listPathRequest)

            if (result.code() == 200) {
                _state.value = _state.value.copy(isUpdateCategoryNameSuccess = true)
            } else {
                _state.value = _state.value.copy(isUpdateCategoryNameSuccess = false)
                Log.e("LibraryViewModel", "updateCategory: $result")
            }
        }
    }

    /**
     * Todo: Implement check user is login
     */
    fun checkUserIsLogin(): Boolean {
        return appPreference.isUserLoggedIn
    }


    fun onNavigateComicDetail(comicId: Long) {
        navController?.navigate("comic_detail_route/$comicId")
    }

    fun onNavigateCategoryDetail(categoryId: Long) {
        Log.d("LibraryViewModel", "onNavigateCategoryDetail: $categoryId")
        // navController?.navigate("category_detail_route/$categoryId")
    }

}