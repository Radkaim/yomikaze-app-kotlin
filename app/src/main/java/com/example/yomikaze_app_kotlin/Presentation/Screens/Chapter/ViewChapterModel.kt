package com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetPageByComicIdAndChapterNumberDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetListChaptersByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetPagesByChapterNumberOfComicUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewChapterModel @Inject constructor(
    private val appPreference: AppPreference,
    private val getPagesByChapterNumberOfComicUC: GetPagesByChapterNumberOfComicUC,
    private val getPageByComicIdAndChapterNumberDBUC: GetPageByComicIdAndChapterNumberDBUC,
    private val getListChaptersByComicIdUC: GetListChaptersByComicIdUC
) : ViewModel() {


    private val _state = MutableStateFlow(ViewChapterState())
    val state: StateFlow<ViewChapterState> get() = _state

    //navController
    private var navController: NavController? = null


    //set navController
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    //update can previous chapter
    fun updateCanPreviousChapter(canPreviousChapter: Boolean) {
        _state.value = _state.value.copy(canPreviousChapter = canPreviousChapter)
    }

    //update can next chapter
    fun updateCanNextChapter(canNextChapter: Boolean) {
        _state.value = _state.value.copy(canNextChapter = canNextChapter)
    }

    fun canGoToPreviousChapter(currentChapterNumber: Int): Boolean {
        val currentIndex =
            state.value.listChapters!!.indexOfFirst { it.number == currentChapterNumber }
        return currentIndex > 0
    }

    fun canGoToNextChapter(currentChapterNumber: Int): Boolean {
        val currentIndex =
            state.value.listChapters!!.indexOfFirst { it.number == currentChapterNumber }
        return currentIndex >= 0 && currentIndex < state.value.listChapters!!.size - 1
    }

    fun getPreviousChapterNumber(currentChapterNumber: Int): Int? {
        val currentIndex = state.value.listChapters!!.indexOfFirst { it.number == currentChapterNumber }
        return if (currentIndex > 0) state.value.listChapters!![currentIndex - 1].number else null
    }

    fun getNextChapterNumber(currentChapterNumber: Int): Int? {
        val currentIndex = state.value.listChapters!!.indexOfFirst { it.number == currentChapterNumber }
        return if (currentIndex >= 0 && currentIndex < state.value.listChapters!!.size - 1) state.value.listChapters!![currentIndex + 1].number else null
    }

    //navigate to view chapter
    fun navigateToViewChapter(comicId: Long, chapterNumber: Int) {
        navController?.navigate("view_chapter_route/$comicId/$chapterNumber") {
            //remove current view chapter from backstack
            popUpTo("view_chapter_route/$comicId/$chapterNumber") {
                inclusive = false
            }

        }
    }


    // get pages by chapter number of comic
    fun getPagesByChapterNumberOfComic(comicId: Long, chapterNumber: Int) {
        _state.value = _state.value.copy(isGetPageApiSuccess = false)
        viewModelScope.launch(Dispatchers.IO) {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!

            val result = getPagesByChapterNumberOfComicUC.getPagesByChapterNumberOfComic(
                token,
                comicId,
                chapterNumber
            )
            result.fold(
                onSuccess = { page ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        pagesImage = page.pages,
                        pageResponse = page,
                        currentChapterNumber = chapterNumber

                    )
                    _state.value = _state.value.copy(isGetPageApiSuccess = true)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isGetPageApiSuccess = false)
                    Log.e("ViewChapterModel", "getPagesByChapterNumberOfComic: $exception")
                }
            )
        }
    }

    // get page by comic id and chapter number
    fun getPageByComicIdAndChapterNumberInDB(comicId: Long, chapterNumber: Int) {
        Log.d("ViewChapterModel", "getPageByComicIdAndChapterNumberInDB: $comicId, $chapterNumber")
        viewModelScope.launch(Dispatchers.IO) {

            val result = getPageByComicIdAndChapterNumberDBUC.getPageByComicIdAndChapterNumberDB(
                comicId,
                chapterNumber
            )
            if (result == null) {
                _state.value = _state.value.copy(
                    isPagesExistInDB = false,
                    pagesImage = emptyList()
                )
                return@launch
            }
            _state.value = _state.value.copy(
                isPagesExistInDB = true,
                pagesImage = result.imageLocalPaths!!,
                pageResponse = result
            )
            Log.d("ViewChapterModel", "getPageByComicIdAndChapterNumberInDB: ${result.pages}")
        }
    }


    /**
     * Todo: Implement get list chapter by comic id in comic detail view
     */
    fun getListChapterByComicId(comicId: Long) {
        _state.value = _state.value.copy(listChapters = emptyList())
        viewModelScope.launch(Dispatchers.IO) {
            val result = getListChaptersByComicIdUC.getListChapters(comicId)
            result.fold(
                onSuccess = { listChapter ->
                    // Xử lý kết quả thành công
                    //_state.value.listChapters.value = listChapter
                    _state.value = _state.value.copy(
                        listChapters = listChapter.sortedBy { it.number }
                    )
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ComicDetailViewModel", "getListChapterByComicId: $exception")
                }
            )
        }
    }

}