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
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChaptersByComicIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetListChaptersByComicIdUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.UnlockManyChaptersUC
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
    private val getChaptersByComicIdDBUC: GetChaptersByComicIdDBUC,
    private val workManager: WorkManager,
    private val unlockManyChaptersUC: UnlockManyChaptersUC
) : ViewModel() {
    private var navController: NavController? = null

    private val _state = MutableStateFlow(ChooseChapterDownloadState())
    val state: StateFlow<ChooseChapterDownloadState> get() = _state

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun navigateToCoinShop() {
        navController?.navigate("coins_shop_route")
    }

    // Get selected chapters

    fun getTotalPriceOfSelectedChaptersContainLock(): Int {
        // return _state.value.listChapterForDownloaded.filter { it.isSelected }.sumBy { it.price }
        // get total price of selected chapters and selected chapters contain locked chapter
        return if (appPreference.isUserLoggedIn) {
            _state.value.listChapterForDownloaded.filter { it.isSelected && !it.isUnlocked }
                .sumBy { it.price }
        } else {
            _state.value.listChapterForDownloaded.filter { it.isSelected && it.hasLock }
                .sumBy { it.price }
        }
    }

    fun getChapterNumberOfSelectedChaptersContainingLockedChapter(): List<Int> {
        return _state.value.listChapterForDownloaded.filter { it.isSelected && !it.isUnlocked }
            .map { it.number }
    }

    fun getSelectedChapters(): List<Chapter> {
        return _state.value.listChapterForDownloaded.filter { it.isSelected }
    }

    //fun get selectedCapterContainIsUnlocked
    fun getSelectedChaptersContainIsUnlocked(): List<Chapter> {
        Log.d("ChooseChapterDownloadViewModel", "getSelectedChaptersContainIsUnlocked: ${_state.value.listChapterForDownloaded.filter { it.isSelected }}")
        return _state.value.listChapterForDownloaded.filter { it.isSelected && !it.isUnlocked }

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
                val downloadedChapter =
                    downloadedChapters.find { it.chapterId == chapter.chapterId }
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
        Log.d("ChooseChapterDownloadViewModel", "getComicDetailsAndDownload: $selectedChapters")
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
                    currentList?.addAll(listChapter.results.map { it.number })
                    //sort list number chapter
                    currentList?.sort()

                    _state.value = state.value.copy(
                        listChapterForDownloaded = listChapter.results,
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


    /**
     * Todo: Implement unlock many chapters
     */
    fun unlockManyChapters(comicId: Long, listChapterNumbers: List<Int>, totalPrice: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isUnlockChapterSuccess = false)

            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!
            if (token.isEmpty()) {
                return@launch
            }
            val result = unlockManyChaptersUC.unlockManyChapters(token, comicId, listChapterNumbers)
            if (result.isSuccessful) {
//                navController?.navigateUp()
//                getPagesByChapterNumberOfComic(comicId, chapterNumber)
                _state.value = _state.value.copy(isUnlockChapterSuccess = true)
                Log.d("ViewChapterModel", "unlockAChapter: ${result.code()}")
                //set isUnlocked of selected chapters to true
                val updatedChapters = _state.value.listChapterForDownloaded.map { chapter ->
                    if (listChapterNumbers.contains(chapter.number)) {
                        chapter.copy(isUnlocked = true, isDownloaded = true)
                    } else {
                        chapter
                    }
                }
                _state.value = _state.value.copy(listChapterForDownloaded = updatedChapters)
                appPreference.userBalance = appPreference.userBalance - totalPrice
            } else {
                _state.value = _state.value.copy(isUnlockChapterSuccess = false)
                Log.e("ViewChapterModel", "unlockAChapter: ${result.errorBody()?.string()}")
            }
        }
    }
}
