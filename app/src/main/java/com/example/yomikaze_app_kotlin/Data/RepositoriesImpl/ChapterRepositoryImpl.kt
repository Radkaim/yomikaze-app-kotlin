package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.content.Context
import android.util.Log
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ChapterDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.PageDao
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Models.Page
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ImageRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import javax.inject.Inject

class ChapterRepositoryImpl @Inject constructor(
    private val chapterDao: ChapterDao,
    private val pageDao: PageDao,
    private val imageRepository: ImageRepository,
    private val pageRepository: PageRepository
) : ChapterRepository {

    override suspend fun downloadPagesOfChapter(chapter: Chapter, context: Context) {
        try {
            val pages =
                pageRepository.getPagesByChapterIndex(chapter.chapterIndex) // Lấy danh sách các trang ảnh của chương
            var totalSize = 0L

            for (page in pages.getOrNull() ?: emptyList()) {
                val imageResult =
                    imageRepository.downloadImageFromApi(page.imageApiUrl) // Tải hình ảnh từ URL

                var localPath: String? = null
                when (imageResult) {
                    is DownloadResult.Success -> {
                        localPath = imageRepository.returnImageLocalPath(
                            imageResult.imageData,
                            context
                        ) // Lưu ảnh vào bộ nhớ trong
                        totalSize += imageResult.imageData.size.toLong()
                    }

                    is DownloadResult.Failure -> {
                        // Xử lý khi tải ảnh thất bại
                        Log.e(
                            "PageRepositoryImpl",
                            "Failed to download image for page ${page.pageIndex}"
                        )
                    }
                }

                // Insert thông tin trang ảnh vào cơ sở dữ liệu
                pageDao.insertImage(
                    Page(
                        id = page.id,
                        chapterId = chapter.chapterId,
                        pageIndex = page.pageIndex,
                        imageApiUrl = page.imageApiUrl,
                        imageLocalPath = localPath ?: ""
                    )
                )
            }

            // Cập nhật thông tin chương đã tải xuống
            chapter.size = totalSize
            chapter.isDownloaded = true
            chapterDao.updateChapterDownloaded(chapter)
        } catch (e: Exception) {
            throw e
        }
    }
}