package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.content.Context
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ChapterDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.PageDao
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Models.Page
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import javax.inject.Inject

class ChapterRepositoryImpl @Inject constructor(
    private val chapterDao: ChapterDao,
    private val pageDao: PageDao,
    private val imageRepository: PageRepository,
    private val pageRepository: PageRepository
) : ChapterRepository {

    override suspend fun downloadChapter(chapter: Chapter, context: Context) {
        val pages = pageRepository.getPagesByChapterIndex(chapter.chapterIndex) // Hàm tải danh sách ảnh từ server
        var totalSize = 0L

        for (image in pages.getOrNull() ?: emptyList()) {
            val downloadedImage = pageRepository.downloadImage(image.imageApiUrl) // Hàm tải hình ảnh từ URL
            val localPath = imageRepository.saveImageToLocal(downloadedImage, context) // Hàm lưu ảnh vào bộ nhớ trong

            totalSize += downloadedImage.size
            pageDao.insertImage(Page(image.id, chapter.chapterId, image.pageIndex, image.imageApiUrl, localPath))
        }

        chapter.size = totalSize
        chapter.isDownloaded = true
        chapterDao.updateChapterDownloaded(chapter)
    }
}