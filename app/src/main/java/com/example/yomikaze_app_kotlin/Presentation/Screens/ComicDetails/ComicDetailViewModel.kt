package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.UseCases.DownloadUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetComicDetailsFromApiUC
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
    appPreference: AppPreference,
     @ApplicationContext private val context: Context,
    private val downloadUC: DownloadUC
) : ViewModel() {
    //navController
    private var navController: NavController? = null
    private val appPreference = appPreference
    private var comic: ComicResponse? = null

    private val _state = MutableStateFlow(ComicDetailState())
    val state: StateFlow<ComicDetailState> get() = _state

    //set navController
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    //navigate to view chapter
    fun navigateToViewChapter(chapterId: Int) {
        navController?.navigate("view_chapter_route/$chapterId")
    }


     fun getComicDetailsFromApi(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = getComicDetailsFromApiUC.getComicDetailsFromApi(token, comicId)
            Log.d("ComicDetailViewModel", "getComicDetailsFromApi: $result")
            Log.d("ComicDetailsViewModel", "ComicDetailsView: $comicId")
            result.fold(
                onSuccess = { comicDetailResponse ->
                    // Xử lý kết quả thành công
                    _state.value = _state.value.copy(
                        comicResponse = comicDetailResponse,
                        listTagGenres = comicDetailResponse.tags,
                        isLoading = false,
                    )
                  //  comic = comicDetailResponse
                    downloadComic(comicDetailResponse)
                    Log.d("ComicDetailsViewModel", "ComicDetailsViewLocal: $comic")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.d("ComicDetailsViewModel", "ComicDetailsView: $comicId")
                    Log.e("ComicDetailsViewModel", "getComicDetailsFromApi: $exception")
                }
            )
        }
    }


    /**
     * Todo: Implement download comic test case in comic detail view
     */
    @SuppressLint("SuspiciousIndentation")
    fun downloadComic(comic: ComicResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ComicDetailViewModelDownload", "Comic details: $comic")
            val result = downloadUC.insertComicDB(comic, context)
//            val result = downloadUC.testDownloadComic("https://yomikaze.org/string")
//            val result2 = downloadUC.testDownloadComic("https://yomikaze.org/string")
//                Log.e("ComicDetailViewModelDownload", "downloadComic1: $result")
//                Log.e("ComicDetailViewModelDownload", "downloadComic2: $result2")
//            val resultAfter = downloadUC.testReturnImageLocalPath(result, context)
//            Log.e("ComicDetailViewModelDownload", "downloadComic1: $resultAfter")
//            val resultAfter2 = downloadUC.testReturnImageLocalPath(result2, context)
//            Log.e("ComicDetailViewModelDownload", "downloadComic2: $resultAfter2")
        }
    }
}