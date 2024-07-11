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


    fun getChaptersFromDBByComicId(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val chapters = getChapterByComicIdDBUC.getChapterByComicIdDB(comicId)
            _state.value = _state.value.copy(
                listChapterDownloaded = chapters
            )
        }
    }
}