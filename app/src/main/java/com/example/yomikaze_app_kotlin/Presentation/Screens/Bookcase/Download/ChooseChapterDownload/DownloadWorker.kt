package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.ChooseChapterDownload

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.DownloadPagesOfChapterUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChapterByComicIdAndChapterNumberDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetChapterByIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.GetComicByIdDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB.InsertChapterToDBUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DownloadComicDetailUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetChapterDetailUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.Comic.GetComicDetailsFromApiUC
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@HiltWorker
class DownloadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val appPreference: AppPreference,
    private val getComicDetailsFromApiUC: GetComicDetailsFromApiUC,
    private val getComicByIdDBUC: GetComicByIdDBUC,
    private val downloadComicDetailUC: DownloadComicDetailUC,
    private val getChapterDetailUC: GetChapterDetailUC,
    private val getChapterByIdDBUC: GetChapterByIdDBUC,
    private val insertChapterToDBUC: InsertChapterToDBUC,
    private val getChapterByComicIdAndChapterNumberDBUC: GetChapterByComicIdAndChapterNumberDBUC,
    private val downloadPagesOfChapterUC: DownloadPagesOfChapterUC,
) : CoroutineWorker(context, workerParams) {


    /**
     * TODO: doWork
     */
    override suspend fun doWork(): Result {
        // Lấy comicId và listChapterNumberChoose từ input data
        val comicId = inputData.getLong("comicId", -1)
        val listChapterNumberChoose =
            inputData.getIntArray("listChapterNumberChoose")?.toList() ?: emptyList()
        try {
            getComicDetailsAndDownload(comicId, listChapterNumberChoose)
            return Result.success()
        } catch (e: Exception) {
            Log.e("DownloadWorker", "doWork: $e")
            return Result.failure()
        }
    }

    /**
     * TODO: get comic informatio from api and download that comic information
     * delay 4s to wait for comic information to be downloaded
     * download list chapter choose
     * The order is 1
     */
    private suspend fun getComicDetailsAndDownload(
        comicId: Long,
        listChapterNumberChoose: List<Int>
    ) {
        val token = appPreference.authToken ?: ""
        val result = getComicDetailsFromApiUC.getComicDetailsFromApi(token, comicId)
        withContext(Dispatchers.IO) {
            result.fold(
                onSuccess = { comicDetailResponse ->
                    downloadComic(comicDetailResponse, listChapterNumberChoose)
                    delay(4000)
                    downloadListChapterChoose(comicId, listChapterNumberChoose)
                },
                onFailure = { exception ->
                    Log.e("DownloadWorker", "getComicDetailAndDownload: $exception")
                }
            )
        }
    }


    /**
     * TODO: download comic
     * check if comic is already in DB, if not insert comic to DB
     * The order is 2
     */
    private suspend fun downloadComic(comic: ComicResponse, listChapterNumberChoose: List<Int>) {
        val existingComic = getComicByIdDBUC.getComicByIdDB(comic.comicId)
        if (existingComic == null) {
            downloadComicDetailUC.insertComicDB(comic, applicationContext)
        }
    }

    /**
     * TODO: download list chapter choose
     * delay 2s to wait for chapter information to be downloaded
     * download all page of chapter from DB
     * The order is 3
     */
    private suspend fun downloadListChapterChoose(
        comicId: Long,
        listChapterNumberChoose: List<Int>
    ) {
        withContext(Dispatchers.IO) {
            listChapterNumberChoose.forEach { chapterNumber ->
                downloadChapterDetail(comicId, chapterNumber)
            }
            delay(2000)
            downloadAllPageFromApiOfChapterFromListNumberChooseToDB(
                comicId,
                listChapterNumberChoose
            )
        }
    }


    /**
     * TODO: download chapter detail
     * check if chapter is already in DB, if not insert chapter to DB
     * The order is 4
     */
    private suspend fun downloadChapterDetail(comicId: Long, chapterNumber: Int) {
        val token = appPreference.authToken ?: ""
        val chapterResult = getChapterDetailUC.getChapterDetail(token, comicId, chapterNumber)
        chapterResult.fold(
            onSuccess = { chapter ->
                val existingChapter = getChapterByIdDBUC.getChapterByIdDB(chapter.chapterId)
                if (existingChapter == null) {
                    insertChapterToDBUC.insertChapterDB(chapter)
                }
            },
            onFailure = { exception ->
                Log.e("DownloadWorker", "insertChapterToDB: $exception")
            }
        )
    }

    /**
     * TODO: download all page for chapter by list number choose
     * delay 2s to wait for get chapter from DB by comicId and ListNumberChoose
     * download all page for those chapter to DB
     * The order is 5
     */

    private suspend fun downloadAllPageFromApiOfChapterFromListNumberChooseToDB(
        comicId: Long,
        listNumberChoose: List<Int>
    ) {
        val chapters = mutableListOf<Chapter>()
        listNumberChoose.forEach { number ->
            val chapter =
                getChapterByComicIdAndChapterNumberDBUC.getChapterByComicIdAndChapterNumberDB(
                    comicId,
                    number
                )
            chapters.add(chapter)
        }
        delay(2000)
        chapters.forEach { chapter ->
            downloadPageOfChapter(comicId, chapter)
        }
    }


    /**
     * TODO: download all page of chapter from API to DB
     * The order is 6
     */
    private suspend fun downloadPageOfChapter(comicId: Long, chapter: Chapter) {
        val token = appPreference.authToken ?: ""
        try {
            downloadPagesOfChapterUC.downloadPagesOfChapter(
                token,
                comicId,
                chapter,
                applicationContext
            )
        } catch (e: Exception) {
            Log.e("DownloadWorker", "downloadPageOfChapter: $e")
        }
    }


}