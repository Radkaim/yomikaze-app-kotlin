package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Models.RatingRequest
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DownloadComicDetailUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Library.GetLibraryCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.AddComicToCategoryUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.DB.GetComicByIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.FollowComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetComicDetailsFromApiUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetListChaptersByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.RatingComicUC
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
    private val followComicUC: FollowComicUC,
    private val addComicToCategoryUC: AddComicToCategoryUC,
    //for Database
    private val downloadUC: DownloadComicDetailUC,
    private val getComicByIdDBUC: GetComicByIdDBUC
) : ViewModel() {
    //navController
    private var navController: NavController? = null
    private var comic: ComicResponse? = null

    private val _state = MutableStateFlow(ComicDetailState())
    val state: StateFlow<ComicDetailState> get() = _state

    //set navController
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    //navigate to view chapter
    fun navigateToViewChapter(comicId: Long, chapterId: Int) {
        navController?.navigate("view_chapter_route/$comicId/$chapterId")
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
     * Todo: Implement follow comic in comic detail view
     */
    fun followComic(comicId: Long, categoryId: List<Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = followComicUC.followComic(token, comicId)
            result.fold(
                onSuccess = {
                    // Xử lý kết quả thành công
                    addComicToCategory(it.id, categoryId)
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ComicDetailViewModel", "FollowComic: $exception")
                }
            )
        }
    }

    /**
     * Todo: Implement unfollow comic in comic detail view
     */

    /**
     * Todo: Implement add comic to category in comic detail view
     */
    fun addComicToCategory(libraryEntryId: Long, categoryId: List<Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isFollowComicSuccess = false)
            val token = if (appPreference.authToken == null) "" else appPreference.authToken!!

            //create list path request
            val pathRequest = mutableListOf<PathRequest>()

            categoryId.forEach { id ->
                pathRequest.add(PathRequest(id.toString(), "/categoryIds/0", "add"))
            }
            Log.d("ComicDetailViewModel", "addComicToCategory2: $pathRequest")
            val result = addComicToCategoryUC.addComicToCategory(token, libraryEntryId, pathRequest)
            if (result.code() == 204) {
                _state.value = _state.value.copy(isFollowComicSuccess = true)
            } else {
                _state.value = _state.value.copy(isFollowComicSuccess = false)
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
            val result = getListChaptersByComicIdUC.getListChapters(comicId)
            result.fold(
                onSuccess = { listChapter ->
                    // Xử lý kết quả thành công
                    //_state.value.listChapters.value = listChapter
                    _state.value = _state.value.copy(
                        listChapters = listChapter
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
     * -----------------------------------------------------------------------------------------------
     * Todo: Implement for Download Database
     */
    /**
     * Todo: Implement download comic in comic detail view
     */
    @SuppressLint("SuspiciousIndentation")
    fun downloadComic(comic: ComicResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ComicDetailViewModelDownload", "Comic details Download: $comic")
            try {
                downloadUC.insertComicDB(comic, context)
            } catch (e: Exception) {
                Log.e("ComicDetailViewModelDownload", "downloadComic: $e")
            }


            // val result = downloadUC.testDownloadComic("https://yomikaze.org/string")
            // val result2 = downloadUC.testDownloadComic("https://yomikaze.org/string")
            //           Log.e("ComicDetailViewModelDownload", "downloadComic1: $result")
//                Log.e("ComicDetailViewModelDownload", "downloadComic2: $result2")
            //  val resultAfter = downloadUC.testReturnImageLocalPath(result, context)
            //    Log.e("ComicDetailViewModelDownload", "downloadComic1: $resultAfter")
            //  val resultAfter2 = downloadUC.testReturnImageLocalPath(result2, context)
            //  Log.e("ComicDetailViewModelDownload", "downloadComic2: $resultAfter2")
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
}