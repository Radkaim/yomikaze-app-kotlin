package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.RatingRequest
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetComicByIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.GetContinueReadingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetLibraryCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetComicDetailsFromApiUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetListChaptersByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.RatingComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.GetAllComicCommentByComicIdUC
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicDetailViewModel @Inject constructor(
    private val getComicDetailsFromApiUC: GetComicDetailsFromApiUC,
    private val appPreference: AppPreference,
    @ApplicationContext private val context: Context,
    private val ratingComicUC: RatingComicUC,
    private val getListChaptersByComicIdUC: GetListChaptersByComicIdUC,
    private val getLibraryCategoryUC: GetLibraryCategoryUC,
    private val getComicByIdDBUC: GetComicByIdDBUC,
    private val getContinueReadingUC: GetContinueReadingUC,
    private val getAllComicCommentByComicIdUC: GetAllComicCommentByComicIdUC
) : ViewModel() {
    //navController
    private var navController: NavController? = null
    private var comic: ComicResponse? = null

    private val _state = MutableStateFlow(ComicDetailState())
    val state: StateFlow<ComicDetailState> get() = _state
//
//    private val _comicCommentState = MutableStateFlow(ComicCommentState())
//    val comicCommentState: StateFlow<ComicCommentState> get() = _comicCommentState

    //set navController
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    //navigate to view chapter
    fun navigateToViewChapter(comicId: Long, chapterNumber: Int) {
        navController?.navigate("view_chapter_route/$comicId/$chapterNumber/0")
    }

    fun navigateToChooseChapterDownload(comicId: Long, comicName: String) {
        navController?.navigate("choose_chapter_download_route/$comicId/$comicName")

    }
    fun navigateToComicComment(comicId: Long, comicName: String) {
        navController?.navigate("comic_comment_route/$comicId/$comicName")
    }

    fun navigateToCoinShop() {
        navController?.navigate("coins_shop_route")
    }

    /**
     * Todo: Implement check user is login
     */
    fun checkUserIsLogin(): Boolean {
        return appPreference.isUserLoggedIn
    }

    fun getComicDetailsFromApi(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = getComicDetailsFromApiUC.getComicDetailsFromApi(token, comicId)
//            Log.d("ComicDetailViewModel", "getComicDetailsFromApi: $result")
//            Log.d("ComicDetailsViewModel", "ComicDetailsView: $comicId")
            result.fold(
                onSuccess = { comicDetailResponse ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        comicResponse = comicDetailResponse,
                        listTagGenres = comicDetailResponse.tags,
                        isLoading = false,
                    )
                    //  comic = comicDetailResponse
                    Log.d("ComicDetailsViewModel", "ComicDetailsViewLocal: $comicDetailResponse")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ComicDetailsViewModel", "getComicDetailsFromApi: $exception")
                }
            )
        }
    }


    /**
     * Todo: Implement rating comic in comic detail view
     */
    fun rateComic(comicId: Long, rating: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isRatingComicSuccess = false)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val ratingRequest = RatingRequest(rating)

            val result = ratingComicUC.ratingComic(token, comicId, ratingRequest)
            if (result.code() == 200) {
                _state.value = _state.value.copy(isRatingComicSuccess = true)
            } else {
                _state.value = _state.value.copy(isRatingComicSuccess = false)
                Log.e("Rating", "rateComic: $result")
            }
        }
    }


    /**
     * Todo: Implement get all Category
     */
    fun getAllCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isCategoryLoading = false)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!

            val result = getLibraryCategoryUC.getLibraryCategory(token)

            result.fold(
                onSuccess = { baseResponse ->
                    val results = baseResponse.results

                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(totalCategoryResults = baseResponse.totals)
                    _state.value = _state.value.copy(categoryList = results)
                    _state.value = _state.value.copy(isCategoryLoading = true)
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
     * Todo: Implement get list chapter by comic id in comic detail view
     */
    fun getListChapterByComicId(comicId: Long) {
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
                    listChapter.results.forEach() {
                        Log.d("ComicDetailViewModel", "getListChapterByComicId: $it")
                    }
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ComicDetailViewModel", "getListChapterByComicId: $exception")
                }
            )
        }
    }


    /**
     * Todo: Implement get comic by comic id in database
     */
    fun getComicByIdDB(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getComicByIdDBUC.getComicByIdDB(comicId)
            Log.d("ComicDetailViewModel", "getComicByIdDB: $result")
            if (result != null) {
                _state.value = _state.value.copy(
                    comicResponse = result,
                    isComicExistInDB = true
                )
            }
        }
    }


    /**
     * Todo: Implement get all comment of comic by comicId
     */
    fun getAllComicCommentByComicId(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isListComicCommentLoading = true)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = getAllComicCommentByComicIdUC.getAllComicCommentByComicId(
                token = token,
                comicId = comicId,
                orderBy = "CreationTimeDesc",
                page = 1,
                size = 3
            )
            result.fold(
                onSuccess = { baseResponse ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        listComicComment = baseResponse.results,
                        isListComicCommentLoading = false
                        )
                    Log.d("ComicDetailViewModel", "getAllComicCommentByComicId: $result")
                },

                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isListComicCommentLoading = false)
                    Log.e("ComicDetailViewModel", "getAllComicCommentByComicId: $exception")
                }
            )
        }
    }

    /**
     * Todo: Implement start reading first time
     */
    fun onStartReading(comicId: Long, chapterNumber: Int? = 0,  lastPageNumber : Int? = 0){
        navController?.navigate("view_chapter_route/$comicId/$chapterNumber/$lastPageNumber")
    }


    /**
     * Todo: Implement get continue reading
     */
    fun getContinueReading(comicId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = getContinueReadingUC.getContinueReading(token, comicId)
            result.fold(
                onSuccess = { continueReadingResponse ->
                    // Xử lý kết quả thành công
//
                    navController?.navigate("view_chapter_route/${comicId}/${continueReadingResponse.chapter.number}/${continueReadingResponse.pageNumber}")
                    Log.d("ComicDetailViewModel", "getContinueReading: $result")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ComicDetailViewModel", "getContinueReading: $exception")
                }
            )
        }
    }
}