package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library.PersonalCategory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetComicsInCateUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalCategoryViewModel @Inject constructor(
    private val getComicsInCateUC: GetComicsInCateUC,
    private val appPreference: AppPreference
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

    fun getComicsInCate(page: Int? = 1, categoryId: Long) {
        viewModelScope.launch {
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

                },
                onFailure = { exception ->
                    // Xử lý kết quả thất bại
                    Log.e("PersonalCategoryViewModel", "getComicsInCate: $exception")
                }
            )
        }
    }

}