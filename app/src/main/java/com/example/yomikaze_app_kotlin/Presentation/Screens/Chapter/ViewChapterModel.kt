package com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ReportRequest
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChaptersByComicIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetPageByComicIdAndChapterNumberDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.UpdateLastReadPageUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetCommonChapterReportReasonsUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetListChaptersByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.ReportChapterUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.UnlockAChapterUC
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
    private val unlockAChapterUC: UnlockAChapterUC,
    private val updateLastReadPageUC: UpdateLastReadPageUC,
    private val reportChapterUC: ReportChapterUC,
    private val getCommonChapterReportReasonsUC: GetCommonChapterReportReasonsUC,
) : ViewModel() {


    private val _state = MutableStateFlow(ViewChapterState())
    val state: StateFlow<ViewChapterState> get() = _state

    //navController
    private var navController: NavController? = null

    //set navController
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun navigateToCoinShop() {
        navController?.navigate("coins_shop_route")
    }

    fun navigateToChapterComment(comicId: Long, chapterTitle: String?, chapterNumber: Int) {
        navController?.navigate("chapter_comment_route/$comicId/$chapterTitle/$chapterNumber")
    }

    //reset state
    fun resetState() {
        _state.value = ViewChapterState()
    }

    override fun onCleared() {
        resetState()
        super.onCleared()
    }


    //reset chapter unlock number
    fun resetChapterUnlockNumberAndIsChapterNeedToUnlock() {
        _state.value = _state.value.copy(chapterUnlockNumber = -1, isChapterNeedToUnlock = false)
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
//        Log.d("ViewChapterModel", "getNextChapterNumber1: $currentChapterNumber")
        val currentIndex =
            state.value.listChapters!!.indexOfFirst { it.number == currentChapterNumber }
        return if (currentIndex >= 0 && currentIndex < state.value.listChapters!!.size - 1) state.value.listChapters!![currentIndex + 1].number else null
    }

    //rest stateisgetpageapisuccess
    fun resetStateIsGetPageApiSuccess() {
        _state.value = _state.value.copy(isGetPageApiSuccess = false)
    }

    // get pages by chapter number of comic
    fun getPagesByChapterNumberOfComic(comicId: Long, chapterNumber: Int) {
        _state.value = _state.value.copy(isGetPageApiSuccess = false)
        _state.value = _state.value.copy(isUserNeedToLogin = false)
        _state.value = _state.value.copy(pagesImage = emptyList())
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
                        currentChapterTitle = page.name,

                        isUserNeedToLogin = false,
                        isChapterNeedToUnlock = false

                    )
                    _state.value = _state.value.copy(isGetPageApiSuccess = true)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isGetPageApiSuccess = false)
                    _state.value = _state.value.copy(pagesImage = emptyList())
//                    Log.e("ViewChapterModel", "getPagesByChapterNumberOfComic: $exception")

                    // catch 403 code
                    if (exception.message == "403") {

                        _state.value = _state.value.copy(isChapterNeedToUnlock = true)
                        _state.value = _state.value.copy(chapterUnlockNumber = chapterNumber)

                        //for each list chapter get price by chapter number
                        val chapter = state.value.listChapters!!.find { it.number == chapterNumber }
                        if (chapter != null) {
                            _state.value = _state.value.copy(priceToUnlockChapter = chapter.price)
                        }
//                        Log.e(
//                            "ViewChapterModel",
//                            "getPagesByChapterNumberOfComic: Chapter is locked (403) $chapterNumber"
//                        )
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
//        Log.d("ViewChapterModel", "getPageByComicIdAndChapterNumberInDB: $comicId, $chapterNumber")
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
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = getListChaptersByComicIdUC.getListChapters(token, comicId)
            result.fold(
                onSuccess = { listChapter ->
                    // Xử lý kết quả thành công
                    //_state.value.listChapters.value = listChapter
                    _state.value = _state.value.copy(
                        listChapters = listChapter.results.sortedBy { it.number }
                    )
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ComicDetailViewModel", "getListChapterByComicId: $exception")
                }
            )
        }
    }


    /**
     * Todo: Implement unlock a chapter in comic detail view
     */
    fun unlockAChapter(comicId: Long, chapterNumber: Int, price: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isUnlockChapterSuccess = false)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {
                _state.value = _state.value.copy(isUserNeedToLogin = true)
                return@launch
            }
            val result = unlockAChapterUC.unlockAChapter(token, comicId, chapterNumber)
            if (result.isSuccessful) {
//                navController?.navigateUp()
//                getPagesByChapterNumberOfComic(comicId, chapterNumber)
                _state.value = _state.value.copy(isUnlockChapterSuccess = true)
//                Log.d("ViewChapterModel", "unlockAChapter: ${result.body()}")
                appPreference.userBalance = appPreference.userBalance - price
            } else {
                _state.value = _state.value.copy(isUnlockChapterSuccess = false)
                Log.e("ViewChapterModel", "unlockAChapter: ${result.errorBody()?.string()}")
            }
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

    fun updateLastReadPage(comicId: Long, chapterNumber: Int, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {
                return@launch
            }
            val listPathRequest = listOf(
                PathRequest(page.toString(), "/pageNumber", "replace")
            )
            val result = updateLastReadPageUC.updateLastReadPage(
                token,
                comicId,
                chapterNumber,
                listPathRequest
            )
//            Log.d("ViewChapterModel", "updateLastReadPage: $result")
            if (result.isSuccessful) {
                Log.d("ViewChapterModel", "updateLastReadPage: ${result.body()}")
            } else {
                Log.e("ViewChapterModel", "updateLastReadPage: ${result.errorBody()?.string()}")
            }
        }
    }
    init {
        getCommonChapterReportReasons()
    }
    /**
     * Todo: Implement get common report reasons of chapter
     */
    fun getCommonChapterReportReasons() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCommonChapterReportReasonsUC.getCommonChapterReportReasons()
            result.fold(
                onSuccess = { listReportResponse ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(listCommonChapterReportResponse = listReportResponse)
//                    Log.d("ComicDetailViewModel", "getCommonChapterReportReasons: $result")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ComicDetailViewModel", "getCommonChapterReportReasons: $exception")
                }
            )
        }
    }

    /**
     * Todo: Implement report chapter
     */
    fun reportChapter(
        comicId: Long,
        chapterNumber: Int,
        reportReasonId: Long,
        reportContent: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
//            Log.d("ComicDetailViewModel", "reportChapter: $reportContent")
//            Log.d("ComicDetailViewModel", "reportChapter: $reportReasonId")
//            Log.d("ComicDetailViewModel", "reportChapter: $comicId")
//            Log.d("ComicDetailViewModel", "reportChapter: $chapterNumber")
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val newReportRequest = ReportRequest(reportReasonId, reportContent)
            val result =
                reportChapterUC.reportChapter(token, comicId, chapterNumber, newReportRequest)
            if (result.code() == 201) {
//                Log.d("ComicDetailViewModel", "reportChapter: $result")
            } else {
                Log.e("ComicDetailViewModel", "reportChapter: $result")
            }
        }
    }

}