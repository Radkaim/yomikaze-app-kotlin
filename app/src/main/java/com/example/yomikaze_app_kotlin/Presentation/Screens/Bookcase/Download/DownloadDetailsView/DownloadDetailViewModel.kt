package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.DownloadDetailsView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.DeleteChapterByChapterIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.DeletePageByComicIdAndChapterNumberDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChaptersByComicIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetPageByComicIdAndChapterNumberDBUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DownloadDetailViewModel @Inject constructor(
    navController: NavController,
    private val getChaptersByComicIdDBUC: GetChaptersByComicIdDBUC,
    private val deleteChapterByChapterIdDBUC: DeleteChapterByChapterIdDBUC,
    private val getPageByComicIdAndChapterNumberDBUC: GetPageByComicIdAndChapterNumberDBUC,
    private val deletePageByComicIdAndChapterNumberDBUC: DeletePageByComicIdAndChapterNumberDBUC
) : ViewModel() {

    private var navController: NavController? = null

    private val _state = MutableStateFlow(DownloadDetailsState())
    val state: StateFlow<DownloadDetailsState> get() = _state

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun toggleEditMode() {
        _state.value = _state.value.copy(isEditMode = !_state.value.isEditMode)
    }

    fun toggleChapterSelection(index: Int) {
        val updatedChapters = _state.value.listChapterDownloaded.toMutableList()
        val chapter = updatedChapters[index]
        updatedChapters[index] = chapter.copy(isSelected = !chapter.isSelected)
        _state.value = _state.value.copy(listChapterDownloaded = updatedChapters)
    }

    // Get selected chapters
    fun getSelectedChapters(): List<Int> {
        return _state.value.listChapterDownloaded.filter { it.isSelected }
            .map { it.number!! }
    }

    fun selectAllChapters() {
        val areAllSelected = _state.value.listChapterDownloaded.all { it.isSelected }
        val updatedChapters =
            _state.value.listChapterDownloaded.map { it.copy(isSelected = !areAllSelected) }
        _state.value = _state.value.copy(listChapterDownloaded = updatedChapters)
    }

    fun getChaptersFromDBByComicId(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val chapters = getChaptersByComicIdDBUC.getChaptersByComicIdDB(comicId)
            _state.value = _state.value.copy(
                listChapterDownloaded = chapters
            )
        }
    }


    /**
     * Delete all selected chapters and Pages
     */
    fun deleteAllSelectedChaptersAndPages() {
        val selectedChapters = _state.value.listChapterDownloaded.filter { it.isSelected }
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedChapters.isEmpty()) return@launch
            selectedChapters.forEach {
                withContext(Dispatchers.IO){
                    deletePageByComicIdAndChapterNumberDBUC.deletePageByComicIdAndChapterNumberDB(
                        it.comicId!!,
                        it.number!!
                    )
                    deleteChapterByChapterIdDBUC.deleteChapterByChapterIdDB(it.chapterId!!)
                    _state.value = _state.value.copy(
                       isDeleteSuccess = true
                    )
                }
            }
            getChaptersFromDBByComicId(selectedChapters.first().comicId!!)
        }
    }

    /**
     * Delete selected chapters
     */
    fun deleteSelectedChapters() {
        val selectedChapters = _state.value.listChapterDownloaded.filter { it.isSelected }
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedChapters.isEmpty()) return@launch
            selectedChapters.forEach {
                deleteChapterByChapterIdDBUC.deleteChapterByChapterIdDB(it.chapterId!!)
            }
            getChaptersFromDBByComicId(selectedChapters.first().comicId!!)
        }
    }

    /**
     * Delete all image of a page of selected chapters
     */
    fun deleteAllImageOfSelectedChapters() {
        val selectedChapters = _state.value.listChapterDownloaded.filter { it.isSelected }
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedChapters.isEmpty()) return@launch
            selectedChapters.forEach { chapter ->

                val page = getPageByComicIdAndChapterNumberDBUC.getPageByComicIdAndChapterNumberDB(
                    chapter.comicId!!,
                    chapter.number!!
                )
                deletePageByComicIdAndChapterNumberDBUC.deletePageByComicIdAndChapterNumberDB(
                    page.comicId,
                    page.number
                )
            }
            getChaptersFromDBByComicId(selectedChapters.first().comicId!!)
        }
    }

    // navigate to choose download chapter screen
    fun navigateToChooseDownloadChapterScreen(comicId: Long, comicName: String) {
        navController?.navigate("choose_chapter_download_route/$comicId/$comicName")
    }

    // navigate to view chapter screen
    fun navigateToViewChapterScreen(comicId: Long, chapterNumber: Int) {
        navController?.navigate("view_chapter_route/$comicId/$chapterNumber/0")
    }
}