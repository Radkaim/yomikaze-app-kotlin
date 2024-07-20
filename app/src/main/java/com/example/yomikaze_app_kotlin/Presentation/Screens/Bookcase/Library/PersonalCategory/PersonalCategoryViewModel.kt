package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library.PersonalCategory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetComicsInCateUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.RemoveComicFromCategoryUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalCategoryViewModel @Inject constructor(
    private val getComicsInCateUC: GetComicsInCateUC,
    private val appPreference: AppPreference,
    private val removeComicFromCategoryUC: RemoveComicFromCategoryUC,
) : ViewModel() {

    private val _state = MutableStateFlow(PersonalCategoryState())
    private var navController: NavController? = null

    //for StatefulViewModel
    val state: StateFlow<PersonalCategoryState> get() = _state

    // for PersonalCategoryView
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    //navigate to comic detail
    fun navigateToComicDetail(comicId: Long) {
        navController?.navigate("comic_detail_route/$comicId")
    }

    // reset state
     fun resetState() {
        _state.value = PersonalCategoryState()
    }

    override fun onCleared() {
        //reset state
        resetState()
        super.onCleared()
    }

    fun getComicsInCate(page: Int? = 1, categoryId: Long) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoadingComicCate = true)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!

            val size = _state.value.size

            val currentPage = _state.value.currentPage.value
            val totalPages = _state.value.totalPages.value

            if (currentPage >= totalPages && totalPages != 0) return@launch

            val result =
                getComicsInCateUC.getComicsInCate(
                    token = token,
                    categoryId = categoryId,
                    orderBy = "CreationTime",
                    page = page,
                    size = size
                )
            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        listComics = state.value.listComics + results,
                    )
                    _state.value.currentPage.value = baseResponse.currentPage
                    _state.value.totalPages.value = baseResponse.totalPages
                    _state.value = _state.value.copy(isLoadingComicCate = false)

                },
                onFailure = { exception ->
                    // Xử lý kết quả thất bại
                    Log.e("PersonalCategoryViewModel", "getComicsInCate: $exception")
                    _state.value = _state.value.copy(isLoadingComicCate = false)
                }
            )
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
            val result = removeComicFromCategoryUC.removeComicFromCategory(
                token,
                comicId,
                categoriesId
            )
            if (result.code() == 200) {
                Log.d("LibraryViewModel", "removeComicFromCategory: $result")
                _state.value = _state.value.copy(isRemoveComicFromCategorySuccess = true)
                //remove item from list
                _state.value = _state.value.copy(isLoadingComicCate = true)
                val list = _state.value.listComics.toMutableList()
                list.removeAll { it.libraryEntry.comicId == comicId }
                _state.value = _state.value.copy(listComics = list)
                _state.value = _state.value.copy(isLoadingComicCate = false)
            } else {
                Log.e("LibraryViewModel", "removeComicFromCategory: $result")
                _state.value = _state.value.copy(isRemoveComicFromCategorySuccess = false)
            }
        }
    }

}