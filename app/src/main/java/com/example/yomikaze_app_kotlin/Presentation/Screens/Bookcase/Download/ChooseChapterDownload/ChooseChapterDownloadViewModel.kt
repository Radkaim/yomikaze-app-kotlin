package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.ChooseChapterDownload

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.DownloadPagesOfChapterUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChapterByComicIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChapterByIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetComicByIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.InsertChapterToDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DownloadComicDetailUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetChapterDetailUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetComicDetailsFromApiUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetListChaptersByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetPagesByChapterNumberOfComicUC
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseChapterDownloadViewModel @Inject constructor(
    private val appPreference: AppPreference,
    @ApplicationContext private val context: Context,
    private val getListChaptersByComicIdUC: GetListChaptersByComicIdUC,
    private val getComicDetailsFromApiUC: GetComicDetailsFromApiUC,
    private val downloadComicDetailUC: DownloadComicDetailUC,
    private val getPagesByChapterNumberOfComicUC: GetPagesByChapterNumberOfComicUC,
    private val getChapterDetailUC: GetChapterDetailUC,
    private val insertChapterToDBUC: InsertChapterToDBUC,
    private val getChapterByIdDBUC: GetChapterByIdDBUC,
    private val getComicByIdDBUC: GetComicByIdDBUC,
    private val downloadPagesOfChapterUC: DownloadPagesOfChapterUC,
    private val getChapterByComicIdDBUC: GetChapterByComicIdDBUC,
    private val pageRepository: PageRepository


) : ViewModel() {
    private var navController: NavController? = null

    private val _state = MutableStateFlow(ChooseChapterDownloadState())
    val state: StateFlow<ChooseChapterDownloadState> get() = _state

    fun setNavController(navController: NavController) {
        this.navController = navController
    }



    //test get page
    fun getPagesByChapterNumberOfComic(
        comicId: Long,
        chapterIndex: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = pageRepository.getImagesByComicIdAndChapterNumberDB(comicId, chapterIndex)
            Log.d("ChooseChapterDownloadViewModel", "getPagesByChapterNumberOfComic: $result")
        }
    }



    /**
     *Todo: Implement get the list chapter of comic from API
     */

    fun getListChapterByComicId(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = state.value.copy(isListNumberLoading = true)
            val result = getListChaptersByComicIdUC.getListChapters(comicId)

            result.fold(
                onSuccess = { listChapter ->
                    // Xử lý kết quả thành công
                    //_state.value.listChapters.value = listChapter
                    //add number of list chapter to state
                    val currentList = _state.value.listNumberChapters?.toMutableList()
                    currentList?.addAll(listChapter.map { it.number })
                    //sort list number chapter
                    currentList?.sort()

                    _state.value = state.value.copy(
                        listNumberChapters = currentList,
                        isListNumberLoading = false
                    )

                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    _state.value = state.value.copy(isListNumberLoading = true)
                    Log.e("ComicDetailViewModel", "getListChapterByComicId: $exception")
                }
            )
        }
    }

    /**
     * Todo: Implement get comic details and download
     */
    fun getComicDetailsAndDownload(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = getComicDetailsFromApiUC.getComicDetailsFromApi(token, comicId)
            result.fold(
                onSuccess = { comicDetailResponse ->
                    // Xử lý kết quả thành công
                    downloadComic(comicDetailResponse)
                    //  Log.d("ChooseChapterDownloadViewModel", "getComicDetailsFromApi: $comicDetailResponse")
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ChooseChapterDownloadViewModel", "getComicDetailAndDownload: $exception")
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
    private fun downloadComic(comic: ComicResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ComicDetailViewModelDownload", "Comic details Download: $comic")
            try {
                val existingComic = getComicByIdDBUC.getComicByIdDB(comic.comicId)

                if (existingComic == null) {
                    downloadComicDetailUC.insertComicDB(comic, context)
                }
            } catch (e: Exception) {
                Log.e("ComicDetailViewModelDownload", "downloadComic: $e")
            }

        }
    }

    /**
     * TODO: download List Chapter choose by user
     */
    fun downloadListChapterChoose(comicId: Long, listChapterNumberChoose: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ComicDetailViewModelDownload", "List Chapter Choose: $listChapterNumberChoose")
            listChapterNumberChoose.forEach { chapterNumber ->
                Log.d("ComicDetailViewModelDownload", "hung")
                downloadChapterDetail(comicId, chapterNumber)
            }
        }
    }

    /**
     * TODO: Implement download detail of chapter and insert to database
     */
    private fun downloadChapterDetail(comicId: Long, chapterNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!

            val chapterResult = getChapterDetailUC.getChapterDetail(token, comicId, chapterNumber)
            chapterResult.fold(
                onSuccess = { chapter ->
                    // Xử lý kết quả thành công
                    try {
                        val existingChapter = getChapterByIdDBUC.getChapterByIdDB(chapter.chapterId)
                        if (existingChapter == null) {
                            //log yes
                            insertChapterToDBUC.insertChapterDB(chapter)
                        } else {
                            return@fold
                        }
                    } catch (e: Exception) {
                        Log.e("ComicDetailViewModelDownload", "insertChapterToDB: $e")
                    }
                },
                onFailure = { exception ->
                    // Xử lý lỗi
                    Log.e("ComicDetailViewModelDownload", "insertChapterToDB: $exception")
                }
            )
        }
    }


    /**
     * TODO get list chapter of downloaded comic from database
     */
    fun downloadAllPageOfChapterFromDB(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val chapters = getChapterByComicIdDBUC.getChapterByComicIdDB(comicId)

            Log.d("ComicDetailViewModelDownload", "Chapter: $chapters")

            chapters.forEach { chapter ->
                downloadPageOfChapter(comicId, chapter)
            }
        }
    }

    /**
     * TODO: Implement get list page of chapter and download
     */
  private  fun downloadPageOfChapter(comicId: Long, chapter: Chapter) {
        viewModelScope.launch(Dispatchers.IO) {
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            try {
                downloadPagesOfChapterUC.downloadPagesOfChapter(
                    token,
                    comicId,
                    chapter,
                    context
                )
            } catch (e: Exception) {
                Log.e("ComicDetailViewModelDownload", "downloadPageOfChapter: $e")
            }

        }
    }

}
