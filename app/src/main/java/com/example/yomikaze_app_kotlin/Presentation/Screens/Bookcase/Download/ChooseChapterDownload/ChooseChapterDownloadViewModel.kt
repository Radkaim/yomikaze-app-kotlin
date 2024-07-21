package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.ChooseChapterDownload

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.DownloadPagesOfChapterUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChapterByComicIdAndChapterNumberDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChapterByIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChaptersByComicIdDBUC
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
    private val getChaptersByComicIdDBUC: GetChaptersByComicIdDBUC,
    private val pageRepository: PageRepository,
    private val workManager: WorkManager,
    private val getChapterByComicIdAndChapterNumberDBUC: GetChapterByComicIdAndChapterNumberDBUC
) : ViewModel() {
    private var navController: NavController? = null

    private val _state = MutableStateFlow(ChooseChapterDownloadState())
    val state: StateFlow<ChooseChapterDownloadState> get() = _state

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    // Get selected chapters

    fun getTotalPriceOfSelectedChapters(): Int {
        return _state.value.listChapterForDownloaded.filter { it.isSelected }.sumBy { it.price }
    }
    fun getChapterIdOfSelectedChaptersContainingLockedChapter(): List<Long> {
        return _state.value.listChapterForDownloaded.filter { it.isSelected && it.hasLock }.map { it.chapterId }
    }

    fun getSelectedChapters(): List<Chapter> {
        return _state.value.listChapterForDownloaded.filter { it.isSelected }
    }

    fun selectAllChapters() {
        val areAllSelected = _state.value.listChapterForDownloaded.all { it.isSelected }
        val updatedChapters =
            _state.value.listChapterForDownloaded.map { it.copy(isSelected = !areAllSelected) }
        _state.value = _state.value.copy(listChapterForDownloaded = updatedChapters)
    }
    fun toggleChapterSelection(chapter: Chapter) {
        val index = _state.value.listChapterForDownloaded.indexOf(chapter)
        val updatedChapters = _state.value.listChapterForDownloaded.toMutableList()
        val chapter = updatedChapters[index]
        updatedChapters[index] = chapter.copy(isSelected = !chapter.isSelected)
        _state.value = _state.value.copy(listChapterForDownloaded = updatedChapters)
    }

    // a funtion for get downloaded chapter from db and map to listChapterForDownloaded map isdownloaded of the state
    fun getDownloadedChaptersFromDB(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val downloadedChapters = getChaptersByComicIdDBUC.getChaptersByComicIdDB(comicId)
            val currentChapters = _state.value.listChapterForDownloaded

            val updatedChapters = currentChapters.map { chapter ->
                val downloadedChapter = downloadedChapters.find { it.chapterId == chapter.chapterId }
                chapter.copy(isDownloaded = downloadedChapter?.isDownloaded ?: false)
            }
            _state.value = _state.value.copy(listChapterForDownloaded = updatedChapters)
        }
    }


    fun getComicDetailsAndDownload(comicId: Long) {
        val selectedChapters = _state.value.listChapterForDownloaded.filter { it.isSelected }
        if (selectedChapters.isEmpty()) return
        val inputData = workDataOf(
            "comicId" to comicId,
            "listChapterNumberChoose" to selectedChapters.map { it.number }.toIntArray()
        )
//        val inputData = workDataOf(
//            "comicId" to comicId,
//            "listChapterNumberChoose" to listChapterNumberChoose.toIntArray()
//        )

        val downloadWorkRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(inputData)
            .build()

        workManager.enqueue(downloadWorkRequest)
    }

    /**
     *Todo: Implement get the list chapter of comic from API
     */

    fun getListChapterByComicId(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = state.value.copy(isListNumberLoading = true)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            val result = getListChaptersByComicIdUC.getListChapters(token, comicId)

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
                     listChapterForDownloaded = listChapter,
                    )
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

            getDownloadedChaptersFromDB(comicId) // map downloaded chapter to listChapterForDownloaded
        }
    }


//    /**
//     * Todo: Implement get comic details and download
//     */
//    fun getComicDetailsAndDownload(comicId: Long, listChapterNumberChoose: List<Int>) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _state.value = state.value.copy(isPrepareForDownload = true)
//            val token =
//                if (appPreference.authToken == null) "" else appPreference.authToken!!
//            val result = getComicDetailsFromApiUC.getComicDetailsFromApi(token, comicId)
//            withContext(Dispatchers.IO) {
//                result.fold(
//                    onSuccess = { comicDetailResponse ->
//                        // Xử lý kết quả thành công
//                        downloadComic(comicDetailResponse, listChapterNumberChoose)
//                        //  Log.d("ChooseChapterDownloadViewModel", "getComicDetailsFromApi: $comicDetailResponse")
//                        _state.value = state.value.copy(isPrepareForDownload = false)
//                    },
//                    onFailure = { exception ->
//                        // Xử lý lỗi
//                        _state.value = state.value.copy(isPrepareForDownload = false)
//                        Log.e(
//                            "ChooseChapterDownloadViewModel",
//                            "getComicDetailAndDownload: $exception"
//                        )
//                    }
//                )
//
//                delay(4000)
//
//                downloadListChapterChoose(comicId, listChapterNumberChoose)
//            }
//        }
//    }
//
//    /**
//     * -----------------------------------------------------------------------------------------------
//     * Todo: Implement for Download Database
//     */
//    /**
//     * Todo: Implement download comic in comic detail view
//     * 1
//     */
//    @SuppressLint("SuspiciousIndentation")
//    private fun downloadComic(comic: ComicResponse, listChapterNumberChoose: List<Int>) {
//        viewModelScope.launch(Dispatchers.IO) {
//            Log.d("ComicDetailViewModelDownload", "Comic details Download: $comic")
//
//            try {
//                val existingComic = getComicByIdDBUC.getComicByIdDB(comic.comicId)
//
//                if (existingComic == null) {
//                    downloadComicDetailUC.insertComicDB(comic, context)
//                }
//            } catch (e: Exception) {
//                Log.e("ComicDetailViewModelDownload", "downloadComic: $e")
//
//            }
//
//        }
//    }
//
//    /**
//     * TODO: download List Chapter choose by user
//     * 2
//     */
//    fun downloadListChapterChoose(comicId: Long, listChapterNumberChoose: List<Int>) {
//        viewModelScope.launch(Dispatchers.IO) {
//            withContext(Dispatchers.IO) {
//                Log.d("downloadListChapterChoose", "List Chapter Choose: $listChapterNumberChoose")
//                listChapterNumberChoose.forEach { chapterNumber ->
//                    Log.d("ComicDetailViewModelDownload", "hung")
//                    withContext(Dispatchers.IO) {
//                        downloadChapterDetail(comicId, chapterNumber)
//                    }
//                }
//                delay(2000)
//                downloadAllPageOfChapterFromDB(comicId, listChapterNumberChoose)
//            }
//        }
//    }
//
//    /**
//     * TODO: Implement download detail of chapter and insert to database
//     */
//    private fun downloadChapterDetail(comicId: Long, chapterNumber: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val token =
//                if (appPreference.authToken == null) "" else appPreference.authToken!!
//
//            val chapterResult = getChapterDetailUC.getChapterDetail(token, comicId, chapterNumber)
//            chapterResult.fold(
//                onSuccess = { chapter ->
//                    // Xử lý kết quả thành công
//                    try {
//                        val existingChapter = getChapterByIdDBUC.getChapterByIdDB(chapter.chapterId)
//                        if (existingChapter == null) {
//                            Log.d("ComicDetailViewModelDownload", "chapter do not exist")
//                            insertChapterToDBUC.insertChapterDB(chapter)
//                        } else {
//                            Log.d("ComicDetailViewModelDownload", "chapter exist")
//                            return@fold
//                        }
//                    } catch (e: Exception) {
//                        Log.e("ComicDetailViewModelDownload", "insertChapterToDB: $e")
//                    }
//                },
//                onFailure = { exception ->
//                    // Xử lý lỗi
//                    Log.e("ComicDetailViewModelDownload", "insertChapterToDB: $exception")
//                }
//            )
//        }
//    }
//
//
//    /**
//     * TODO get list chapter of downloaded comic from database
//     */
//    fun downloadAllPageOfChapterFromDB(comicId: Long, listNumberChoose: List<Int>) {
//        viewModelScope.launch(Dispatchers.IO) {
//            //  val chapters = getChapterByComicIdDBUC.getChapterByComicIdDB(comicId)
//            val chapters = mutableListOf<Chapter>()
//            withContext(Dispatchers.IO) {
//
//                listNumberChoose.forEach() { number ->
//                    val chapter =
//                        getChapterByComicIdAndChapterNumberDBUC.getChapterByComicIdAndChapterNumberDB(
//                            comicId,
//                            number
//                        )
//                    chapters.add(chapter)
//                }
//
//                delay(2000)
//                Log.d("ComicDetailViewModelDownload", "Chapter: $chapters")
//
//                chapters.forEach { chapter ->
//                    downloadPageOfChapter(comicId, chapter)
//                }
//            }
//
//        }
//    }
//
//    /**
//     * TODO: Implement get list page of chapter and download
//     */
//    private fun downloadPageOfChapter(comicId: Long, chapter: Chapter) {
//        viewModelScope.launch(Dispatchers.IO) {
//            withContext(Dispatchers.IO) {
//                val token =
//                    if (appPreference.authToken == null) "" else appPreference.authToken!!
//                try {
//                    downloadPagesOfChapterUC.downloadPagesOfChapter(
//                        token,
//                        comicId,
//                        chapter,
//                        context
//                    )
//                } catch (e: Exception) {
//                    Log.e("ComicDetailViewModelDownload", "downloadPageOfChapter: $e")
//                }
//            }
//        }
//    }

}
