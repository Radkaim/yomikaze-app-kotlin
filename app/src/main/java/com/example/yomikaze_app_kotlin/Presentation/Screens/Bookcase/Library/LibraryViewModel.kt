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
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryRequest
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.AddComicToLibraryFirstTimeUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.AddComicToLibrarySecondTimeUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.CreateLibraryCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.DeleteCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetCategoriesOfComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetComicsInCateUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetDefaultComicsInLibraryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetLibraryCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.RemoveComicFromCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.SearchInLibraryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.UnfollowComicFromLibraryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.UpdateCateNameUC
import com.example.yomikaze_app_kotlin.Presentation.Screens.BaseModel.StatefulViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Home.SearchWidgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val searchInLibraryUC: SearchInLibraryUC,
    private val appPreference: AppPreference,
    private val getLibraryCategoryUC: GetLibraryCategoryUC,
    private val createLibraryCategoryUC: CreateLibraryCategoryUC,
    private val updateCateNameUC: UpdateCateNameUC,
    private val deleteCategoryUC: DeleteCategoryUC,
    private val getComicsInCateUC: GetComicsInCateUC,
    private val addComicToLibraryFirstTimeUC: AddComicToLibraryFirstTimeUC,
    private val addComicToLibrarySecondTimeUC: AddComicToLibrarySecondTimeUC,
    private val removeComicFromCategoryUC: RemoveComicFromCategoryUC,
    private val unfollowComicFromLibraryUC: UnfollowComicFromLibraryUC,
    private val getCategoriesOfComicUC: GetCategoriesOfComicUC,
    private val getDefaultComicsInLibraryUC: GetDefaultComicsInLibraryUC
) : ViewModel(), StatefulViewModel<LibraryState> {

    private var navController: NavController? = null


    //for StatefulViewModel
    private val _state = MutableStateFlow(LibraryState())
    override val state: StateFlow<LibraryState> get() = _state

    override val isUpdateSuccess: Boolean = _state.value.isUpdateCategoryNameSuccess
    override val isDeleteSuccess: Boolean = _state.value.isDeleteCategorySuccess


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

    fun updateIsSearchResult(newValue: Boolean) {
        _state.value = _state.value.copy(isSearchResult = newValue)
    }

    /**
     * Todo: Implement check user is login
     */
    fun checkUserIsLogin(): Boolean {
        return appPreference.isUserLoggedIn
    }

    //reset state
    fun resetState() { // khi chuyển qua màn hình khác thì reset state
        _state.value = LibraryState()
    }

    override fun onCleared() {
        resetState()
        super.onCleared()
    }

    fun onNavigateComicDetail(comicId: Long) {
        navController?.navigate("comic_detail_route/$comicId")
    }

    fun onNavigateCategoryDetail(categoryId: Long, categoryName: String) {
        navController?.navigate("category_detail_route/$categoryId/$categoryName")
    }

    @SuppressLint("SuspiciousIndentation")
    fun searchComic(comicNameQuery: String) {
        _state.value.searchResult.value = emptyList() // for clear search result for search again
        _state.value = _state.value.copy(isSearchLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {

                return@launch
            }

            val result = searchInLibraryUC.searchComic(token, comicNameQuery)

            result.fold(
                onSuccess = { baseResponse ->

                    val results = baseResponse.results
                    if (results.isEmpty()) {
                        Log.d("LibraryViewModel", "searchComic: No result")
                        _state.value = _state.value.copy(totalSearchResults = 0)
                        _state.value = _state.value.copy(isSearchResult = true)
                        return@fold
                    } else {
                        // Xử lý kết quả thành công
                        _state.value = _state.value.copy(totalSearchResults = baseResponse.totals)
                        _state.value.searchResult.value = results
                        _state.value = _state.value.copy(isSearchLoading = false)
                    }
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isSearchLoading = false)
                    _state.value = _state.value.copy(totalSearchResults = 0)
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
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {
                _state.value = _state.value.copy(isCategoryLoading = false)
                return@launch
            }
            _state.value = _state.value.copy(isCategoryLoading = true)

            val result = getLibraryCategoryUC.getLibraryCategory(token, 1, 500)

            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results.toMutableList()
//                     Lấy ảnh bìa cho mỗi danh mục
                    _state.value = _state.value.copy(categoryList = baseResponse.results)
                    results.forEach { category ->
                        val coverImage = getCoverImage(category.id)
                        val totalsComics = getTotalsComicsInCate(category.id)
                        category.firstCoverImage = coverImage
                        category.totalComics = totalsComics
                    }

                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(totalCategoryResults = baseResponse.totals)

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

    private suspend fun getTotalsComicsInCate(categoryId: Long): Int {
        var totalComics = 0
        val token =
            if (appPreference.authToken == null) "" else appPreference.authToken!!
        if (token.isEmpty()) {
            return 0
        }
        return withContext(Dispatchers.IO) {
            val result = getComicsInCateUC.getComicsInCate(
                token = token,
                categoryId = categoryId,
                orderBy = "CreationTime",
                page = 1,
                size = 500
            )

            result.fold(
                onSuccess = { baseResponse ->
                    totalComics = baseResponse.totals
                    totalComics ?: 0
                },
                onFailure = { exception ->
                    Log.d("LibraryViewModel", "Get Comics In Category: $exception")
                    totalComics ?: 0
                }
            )
        }
    }


    private suspend fun getCoverImage(
        categoryId: Long,
    ): String? {
        var coverImage: String? = ""
        val token =
            if (appPreference.authToken == null) "" else appPreference.authToken!!
        if (token.isEmpty()) {

            return null
        }
        return withContext(Dispatchers.IO) {
            val result = getComicsInCateUC.getComicsInCate(
                token = token,
                categoryId = categoryId,
                orderBy = "CreationTime",
                page = 1,
                size = 1
            )

            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Trả về ảnh bìa đầu tiên nếu có
                    val coverImage = results.firstOrNull()?.libraryEntry?.cover
                    coverImage
                },
                onFailure = { exception ->
                    Log.d("LibraryViewModel", "Get Comics In Category: $exception")
                    null
                }
            )
        }
    }

    /**
     * Todo: Implement add new category
     *
     */
    fun addNewCategory(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isCreateCategorySuccess = false)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {

                return@launch
            }
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
    override fun update(key: Long, key2: Long?, key3: Int?, value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isUpdateCategoryNameSuccess = false)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!

            if (token.isEmpty()) {

                return@launch
            }
            val listPathRequest = listOf(
                PathRequest(value, "/name", "replace")
            )
            val response =
                updateCateNameUC.updateCateName(token, key, listPathRequest)

            if (response.code() == 204) {
                _state.value = _state.value.copy(isUpdateCategoryNameSuccess = true)
            } else {
                _state.value = _state.value.copy(isUpdateCategoryNameSuccess = false)
                Log.e("LibraryViewModel", "updateCategory: $response")
            }
        }
    }

    /**
     * Todo: Implement delete category of StatefulViewModel
     *
     */
    override fun delete(key: Long, key2: Long?, key3: Int?, isDeleteAll: Boolean?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isDeleteCategorySuccess = false)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {

                return@launch
            }
            val response = deleteCategoryUC.deleteCategory(token, key)
            if (response.code() == 204) {
                _state.value = _state.value.copy(isDeleteCategorySuccess = true)
            } else {
                _state.value = _state.value.copy(isDeleteCategorySuccess = false)
                Log.e("LibraryViewModel", "deleteCategory: $response")
            }
        }
    }


    /**
     * TODO: Implement the function to get
     * list category which comic is in
     */
    fun getCategoriesOfComic(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = getCategoriesOfComicUC.getCategoriesOfComic(token, comicId)

            result.fold(
                onSuccess = { libraryEntry ->
                    val listCateComicIsIn = libraryEntry.categories
                    _state.value =
                        _state.value.copy(listCateComicIsIn = listCateComicIsIn!!)

//                    Log.d("LibraryViewModel", "getCategoriesOfComic: $result")

                },

                onFailure = { exception ->

                    Log.d("LibraryViewModel", "getCategoriesOfComic: $exception")
                }
            )
        }
    }


    /**
     * TODO: Implement the function to add a comic to library category the first time they follow the comic
     */
    fun addComicToLibraryFirstTime(comicId: Long, categoriesId: List<Long>) {
        _state.value = _state.value.copy(isAddComicToCategoryFirstTimeSuccess = false)
        viewModelScope.launch(Dispatchers.IO) {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {
                return@launch
            }
            val result = addComicToLibraryFirstTimeUC.addComicToLibraryFirstTime(
                token,
                LibraryRequest(comicId, categoriesId)
            )
            if (result.code() == 201) {
                Log.d("LibraryViewModel", "addComicToLibraryFirstTime: $result")
                _state.value = _state.value.copy(isAddComicToCategoryFirstTimeSuccess = true)
            } else {
                Log.e("LibraryViewModel", "addComicToLibraryFirstTime: $result")
                _state.value = _state.value.copy(isAddComicToCategoryFirstTimeSuccess = false)
            }
        }
    }

    /**
     * TODO: Implement the function to add a comic to library category
     * in the second time they have followed the comic and want to add
     */
    fun addComicToLibrarySecondTime(comicId: Long, categoriesId: List<Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isAddComicToCategorySecondTimeSuccess = false)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {

                return@launch
            }
            val result = addComicToLibrarySecondTimeUC.addComicToLibrarySecondTime(
                token,
                comicId,
                categoriesId
            )
            if (result.code() == 200) {
                Log.d("LibraryViewModel", "addComicToLibrarySecondTime: $result")
                _state.value = _state.value.copy(isAddComicToCategorySecondTimeSuccess = true)
            } else {
                Log.e("LibraryViewModel", "addComicToLibrarySecondTime: $result")
                _state.value = _state.value.copy(isAddComicToCategorySecondTimeSuccess = false)
            }
        }
    }

    /**
     * TODO: Implement the function to remove a comic from library category
     */
    fun removeComicFromCategory(comicId: Long, categoriesId: List<Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isRemoveComicFromCategorySuccess = false)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {

                return@launch
            }
            val result = removeComicFromCategoryUC.removeComicFromCategory(
                token,
                comicId,
                categoriesId
            )
            if (result.code() == 200) {
                Log.d("LibraryViewModel", "removeComicFromCategory: $result")
                _state.value = _state.value.copy(isRemoveComicFromCategorySuccess = true)
            } else {
                Log.e("LibraryViewModel", "removeComicFromCategory: $result")
                _state.value = _state.value.copy(isRemoveComicFromCategorySuccess = false)
            }
        }
    }

    /**
     * TODO: Implement the function to remove a comic from library category
     * when they unfollow the comic or remove a comic from all category and library
     */
    fun unfollowComicFromLibrary(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isUnFollowComicSuccess = false)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {

                return@launch
            }
            val result = unfollowComicFromLibraryUC.unfollowComicFromLibrary(
                token,
                comicId
            )
            if (result.code() == 204) {
                Log.d("LibraryViewModel", "unfollowComicFromLibrary: $result")
                _state.value = _state.value.copy(isUnFollowComicSuccess = true)
            } else {
                Log.e("LibraryViewModel", "unfollowComicFromLibrary: $result")
                _state.value = _state.value.copy(isUnFollowComicSuccess = false)
            }
        }
    }

    /**
     * TODO: Implement the function to get default comic that not in which category
     */
    fun getDefaultComicsInLibrary() {
        viewModelScope.launch(Dispatchers.IO) {

            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {
                _state.value = _state.value.copy(isDefaultComicLoading = false)
                return@launch
            }
            _state.value = _state.value.copy(isDefaultComicLoading = true)
            val result =
                getDefaultComicsInLibraryUC.getDefaultComicsInLibrary(token, "CreationTime", 1, 500)

            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    _state.value =
                        _state.value.copy(totalDefaultComicResults = baseResponse.totals)
                    _state.value = _state.value.copy(defaultComicResult = results)
                    _state.value = _state.value.copy(isDefaultComicLoading = false)

                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isDefaultComicLoading = false)
                    _state.value = _state.value.copy(totalDefaultComicResults = 0)
                    Log.d("LibraryViewModel", "getDefaultComicsInLibrary: $exception")
                }
            )
        }
    }
}