package com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetPagesByChapterNumberOfComicUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewChapterModel @Inject constructor(
    private val getPagesByChapterNumberOfComicUC: GetPagesByChapterNumberOfComicUC
) : ViewModel() {


    private val _state = MutableStateFlow(ViewChapterState())
    val state: StateFlow<ViewChapterState> get() = _state

    //navController
    private var navController: NavController? = null


    //set navController
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    // get pages by chapter number of comic
     fun getPagesByChapterNumberOfComic(comicId: Long, chapterNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getPagesByChapterNumberOfComicUC.getPagesByChapterNumberOfComic(comicId, chapterNumber)
            result.fold(
                onSuccess = { page ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        pages = page.pages
                    )
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ViewChapterModel", "getPagesByChapterNumberOfComic: $exception")
                }
            )
        }
    }

}