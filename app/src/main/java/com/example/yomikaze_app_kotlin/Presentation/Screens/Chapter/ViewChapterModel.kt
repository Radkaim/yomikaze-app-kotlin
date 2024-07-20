package com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChaptersByComicIdDBUC
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
    private val getListChaptersByComicIdUC: GetListChaptersByComicIdUC,
    private val getChaptersByComicIdDBUC: GetChaptersByComicIdDBUC,
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
        val currentIndex =
            state.value.listChapters!!.indexOfFirst { it.number == currentChapterNumber }
        return if (currentIndex > 0) state.value.listChapters!![currentIndex - 1].number else null
    }

    fun getNextChapterNumber(currentChapterNumber: Int): Int? {
        Log.d("ViewChapterModel", "getNextChapterNumber1: $currentChapterNumber")
        val currentIndex =
            state.value.listChapters!!.indexOfFirst { it.number == currentChapterNumber }
        return if (currentIndex >= 0 && currentIndex < state.value.listChapters!!.size - 1) state.value.listChapters!![currentIndex + 1].number else null
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
//            if (result.hashCode() == 403) {
//                _state.value = _state.value.copy(isGetPageApiSuccess = false)
//                Log.e("ViewChapterModel", "getPagesByChapterNumberOfComic: Hung is here")
//                return@launch
//            }
            result.fold(
                onSuccess = { page ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        pagesImage = page.pages,
                        pageResponse = page,
                        currentChapterNumber = chapterNumber,

                        isUserNeedToLogin = false,
                        isChapterNeedToUnlock = false

                    )
                    _state.value = _state.value.copy(isGetPageApiSuccess = true)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isGetPageApiSuccess = false)
                    Log.e("ViewChapterModel", "getPagesByChapterNumberOfComic: $exception")

                    // catch 403 code
                    if (exception.message == "403") {
                        _state.value = _state.value.copy(isChapterNeedToUnlock = true)
                        _state.value = _state.value.copy(chapterUnlockNumber = chapterNumber)
                        Log.e(
                            "ViewChapterModel",
                            "getPagesByChapterNumberOfComic: Chapter is locked (403)"
                        )
                    }

                    if (exception.message == "401") {
                        _state.value = _state.value.copy(isUserNeedToLogin = true)
                        Log.e(
                            "ViewChapterModel",
                            "getPagesByChapterNumberOfComic: Unauthorized (401)"
                        )
                    }
                }
            )
        }
    }

    // get page by comic id and chapter number
    fun getPageByComicIdAndChapterNumberInDB(comicId: Long, chapterNumber: Int) {
        _state.value = _state.value.copy(isPagesExistInDB = false)
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
            } else {

                _state.value = _state.value.copy(
                    pagesImage = result.imageLocalPaths!!,
                    pageResponse = result,
                    currentChapterNumber = chapterNumber,

                    isUserNeedToLogin = false,
                    isChapterNeedToUnlock = false
                )
                _state.value = _state.value.copy(isPagesExistInDB = true)
            }
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

    fun getChaptersFromDBByComicId(comicId: Long) {
        _state.value = _state.value.copy(listChapters = emptyList())
        viewModelScope.launch(Dispatchers.IO) {
            val chapters = getChaptersByComicIdDBUC.getChaptersByComicIdDB(comicId)
            _state.value = _state.value.copy(
                listChapters = chapters.sortedBy { it.number }
            )
        }
    }

}