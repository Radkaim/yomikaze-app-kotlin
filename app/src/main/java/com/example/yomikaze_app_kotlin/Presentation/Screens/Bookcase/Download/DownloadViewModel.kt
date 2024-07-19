package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChaptersByComicIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.UpdateTotalMbsOfComicDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DeleteComicByIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.GetAllComicInDBUC
import com.example.yomikaze_app_kotlin.Presentation.Screens.BaseModel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getAllComicInDBUC: GetAllComicInDBUC,
    private val deleteComicByIdDBUC: DeleteComicByIdDBUC,
    private val getChaptersByComicIdDBUC: GetChaptersByComicIdDBUC,
    private val updateTotalMbsOfComicDBUC: UpdateTotalMbsOfComicDBUC
) : ViewModel(), StatefulViewModel<DownloadState> {

    private var navController: NavController? = null

    private val _state = MutableStateFlow(DownloadState())
    override val state: StateFlow<DownloadState> get() = _state

    override val isUpdateSuccess: Boolean? = null
    override val isDeleteSuccess: Boolean = _state.value.isDeleteByIdSuccess

    //for StatefulViewModel
    override fun update(key: Long, value: String) {}


    fun setNavController(navController: NavController) {
        this.navController = navController
    }


    private fun convertToMbsOrGbs(kbs: Long): Float {
        return when {
            kbs < 1024 -> kbs.toFloat() // KB
            kbs < 1024 * 1024 -> String.format("%.1f", kbs / 1024.0).toFloat() // MB
            else -> String.format("%.1f", kbs / (1024.0 * 1024.0)).toFloat() // GB
        }
    }

    fun getAllComicsDownloadedDB() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllComicInDBUC.getAllComicsDownloadedDB().onSuccess {
                withContext(Dispatchers.IO) {
                    it.forEach { comic ->
                        val chaptersInDB =
                            getChaptersByComicIdDBUC.getChaptersByComicIdDB(comic.comicId)
                        val totalMbs = chaptersInDB.sumOf { it.size }
                        updateTotalMbsOfComicDBUC.updateTotalMbsOfComicDB(comic.comicId, totalMbs)
                    }
                    _state.value = state.value.copy(listComicsDB = it)
                }
                Log.d("DownloadViewModel", "getAllComicsDownloadedDB: ${it.size}")
            }
        }
    }

    override fun delete(key: Long, key2: Long?, isDeleteAll: Boolean?) {
        if (isDeleteAll == true) {
            //  deleteAllComicsDB()
        } else {
            deleteComicByIdDB(key)
        }
    }

    fun deleteComicByIdDB(comicId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteComicByIdDBUC.deleteComicByIdDB(comicId, context)

                _state.value = state.value.copy(isDeleteByIdSuccess = true)
                //remove comic from list
                val list = state.value.listComicsDB.toMutableList()
                list.removeIf { it.comicId == comicId }
                _state.value = state.value.copy(listComicsDB = list)
            } catch (e: Exception) {
                Log.e("ComicDetailViewModelDownload", "downloadComic: $e")
            }
        }
    }


    fun onComicDownloadedClick(comicName: String, comicId: Long) {
        navController?.navigate("download_detail_route/$comicId/$comicName")
    }
}