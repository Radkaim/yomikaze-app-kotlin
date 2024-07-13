package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.DownloadDetailsView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChapterByComicIdDBUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadDetailViewModel @Inject constructor(
    navController: NavController,
    private val getChapterByComicIdDBUC: GetChapterByComicIdDBUC
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

    fun getChaptersFromDBByComicId(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val chapters = getChapterByComicIdDBUC.getChapterByComicIdDB(comicId)
            _state.value = _state.value.copy(
                listChapterDownloaded = chapters
            )
        }
    }

    // Get selected chapters
    fun getSelectedChapters(): List<Long> {
        return _state.value.listChapterDownloaded.filter { it.isSelected }
            .map { it.chapterId!! }
    }

    // navigate to choose download chapter screen
    fun navigateToChooseDownloadChapterScreen(comicId: Long, comicName: String) {
        navController?.navigate("choose_chapter_download_route/$comicId/$comicName")
    }

    // navigate to view chapter screen
    fun navigateToViewChapterScreen(comicId: Long, chapterNumber: Int) {
        navController?.navigate("view_chapter_route/$comicId/$chapterNumber")
    }
}