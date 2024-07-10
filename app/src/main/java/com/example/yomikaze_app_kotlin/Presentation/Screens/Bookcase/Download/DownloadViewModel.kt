package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DeleteComicByIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.GetAllComicInDBUC
import com.example.yomikaze_app_kotlin.Presentation.Screens.Base.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getAllComicInDBUC: GetAllComicInDBUC,
    private val deleteComicByIdDBUC: DeleteComicByIdDBUC
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

    fun getAllComicsDownloadedDB() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllComicInDBUC.getAllComicsDownloadedDB().onSuccess {
                _state.value = state.value.copy(listComicsDB = it)
                Log.d("DownloadViewModel", "getAllComicsDownloadedDB: ${it.size}")
            }
        }
    }

    override fun delete(key: Long, isDeleteAll: Boolean?) {
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