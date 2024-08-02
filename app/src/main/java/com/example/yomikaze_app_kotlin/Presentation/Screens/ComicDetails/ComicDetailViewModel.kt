package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.RatingRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ReactionRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ReportRequest
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetComicByIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History.GetContinueReadingUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetLibraryCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetComicCommonReportReasonsUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetComicDetailsFromApiUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetCommonChapterReportReasonsUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetListChaptersByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.RatingComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.ReportChapterUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.ReportComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.Common.GetCommonCommentReportReasonsUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.Common.ReportCommentUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.GetAllComicCommentByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ReactComicCommentByComicIdUC
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
    private val getAllComicCommentByComicIdUC: GetAllComicCommentByComicIdUC,
    private val reactComicCommentByComicIdUC: ReactComicCommentByComicIdUC,

    private val getComicCommonReportReasonsUC: GetComicCommonReportReasonsUC,
    private val reportComicUC: ReportComicUC,

    private val getCommonChapterReportReasonsUC: GetCommonChapterReportReasonsUC,
    private val reportChapterUC: ReportChapterUC,

    private val reportCommentUC: ReportCommentUC,
    private val getCommonCommentReportReasonsUC: GetCommonCommentReportReasonsUC
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

    fun onNavigateToReplyCommentDetail(commentId: Long, comicId: Long, authorName: String) {
        navController?.navigate("reply_comment_detail_route/$comicId/$commentId/$authorName")
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


    init {
        getComicCommonReportReasons()
        getCommonCommentReportReasons()
    }

    fun getComicDetailsFromApi(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true)
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
//                    Log.d("ComicDetailsViewModel", "ComicDetailsViewLocal: $comicDetailResponse")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isLoading = false)
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
            _state.value = _state.value.copy(isListChaptersLoading = true)
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
//
                    _state.value = _state.value.copy(isListChaptersLoading = false)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = _state.value.copy(isListChaptersLoading = false)
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
//                    Log.d("ComicDetailViewModel", "getAllComicCommentByComicId: $result")
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
    fun onStartReading(comicId: Long, chapterNumber: Int? = 0, lastPageNumber: Int? = 0) {
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
//                    Log.d("ComicDetailViewModel", "getContinueReading: $result")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ComicDetailViewModel", "getContinueReading: $exception")
                }
            )
        }
    }

    /**
     * Todo: Implement react comic comment by comicId
     */
    fun reactComicCommentByComicId(
        comicId: Long,
        commentId: Long,
        reactionType: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val reactionRequest = ReactionRequest(reactionType)
            val result = reactComicCommentByComicIdUC.reactComicCommentByComicId(
                token = token,
                comicId = comicId,
                commentId = commentId,
                reactionRequest = reactionRequest
            )
            if (result.code() == 200 || result.code() == 204) {
//                Log.d("ComicCommentViewModel", "reactComicCommentByComicId: $result")
                // update it content
                _state.value = _state.value.copy(
                    listComicComment = _state.value.listComicComment.map {
                        if (it.id == commentId) {
                            if (reactionType == "Like") {
                                if (it.myReaction == "Like") {
                                    // Already liked, so unlike it
                                    it.copy(
                                        totalLikes = it.totalLikes - 1,
                                        myReaction = "",
                                        isReacted = false
                                    )
                                } else {
                                    // Either it was disliked or no reaction before, so like it
                                    val newTotalDislikes =
                                        if (it.myReaction == "Dislike") it.totalDislikes - 1 else it.totalDislikes
                                    it.copy(
                                        totalLikes = it.totalLikes + 1,
                                        totalDislikes = newTotalDislikes,
                                        myReaction = "Like",
                                        isReacted = true
                                    )
                                }
                            } else {
                                if (it.myReaction == "Dislike") {
                                    // Already disliked, so undislike it
                                    it.copy(
                                        totalDislikes = it.totalDislikes - 1,
                                        myReaction = "",
                                        isReacted = false
                                    )
                                } else {
                                    // Either it was liked or no reaction before, so dislike it
                                    val newTotalLikes =
                                        if (it.myReaction == "Like") it.totalLikes - 1 else it.totalLikes
                                    it.copy(
                                        totalDislikes = it.totalDislikes + 1,
                                        totalLikes = newTotalLikes,
                                        myReaction = "Dislike",
                                        isReacted = true
                                    )
                                }
                            }
                        } else {
                            it
                        }
                    }
                )
            } else {
                Log.e("ComicCommentViewModel", "reactComicCommentByComicId: $result")
            }
        }
    }

    /**
     * Todo: Implement get common report reasons
     */
    fun getComicCommonReportReasons() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getComicCommonReportReasonsUC.getComicCommonReportReasons()
            result.fold(
                onSuccess = { listReportResponse ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(listCommonComicReportResponse = listReportResponse)
//                    Log.d("ComicDetailViewModel", "getComicCommonReportReasons: $result")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ComicDetailViewModel", "getComicCommonReportReasons: $exception")
                }
            )
        }
    }

    /**
     * Todo: Implement report comic
     */
    fun reportComic(comicId: Long, reportReasonId: Long, reportContent: String?) {
        viewModelScope.launch(Dispatchers.IO) {
//            Log.d("ComicDetailViewModel", "reportComic: $reportContent")
//            Log.d("ComicDetailViewModel", "reportComic: $reportReasonId")
//            Log.d("ComicDetailViewModel", "reportComic: $comicId")

            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val newReportRequest = ReportRequest(reportReasonId, reportContent)
            val result = reportComicUC.reportComic(token, comicId, newReportRequest)
            if (result.code() == 201) {
//                Log.d("ComicDetailViewModel", "reportComic: $result")
            } else {
                Log.e("ComicDetailViewModel", "reportComic: $result")
            }
        }
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

    /**
     * Todo: Implement report comment
     */
    fun reportComment(commentId: Long, reportReasonId: Long, reportContent: String?) {
        viewModelScope.launch(Dispatchers.IO) {
//            Log.d("ComicDetailViewModel", "reportComment: $reportContent")
//            Log.d("ComicDetailViewModel", "reportComment: $reportReasonId")
//            Log.d("ComicDetailViewModel", "reportComment: $commentId")
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val newReportRequest = ReportRequest(reportReasonId, reportContent)
            val result = reportCommentUC.reportComment(token, commentId, newReportRequest)
            if (result.code() == 201) {
                Log.d("ComicDetailViewModel", "reportComment: $result")
            } else {
                Log.e("ComicDetailViewModel", "reportComment: $result")
            }
        }
    }

    /**
     * Todo: Implement get common comment report reasons
     */
    fun getCommonCommentReportReasons() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCommonCommentReportReasonsUC.getCommonCommentReport()
            result.fold(
                onSuccess = { listReportResponse ->
                    // Xử lý kết quả thành công
                    _state.value =
                        _state.value.copy(listCommonCommentReportResponse = listReportResponse)
//                    Log.d("ComicDetailViewModel", "getCommonCommentReportReasons: $result")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ComicDetailViewModel", "getCommonCommentReportReasons: $exception")
                }
            )
        }
    }
}