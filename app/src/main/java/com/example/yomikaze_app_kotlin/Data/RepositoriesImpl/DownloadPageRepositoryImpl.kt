package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.content.Context
import android.util.Log
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ChapterDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.PageDao
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Models.Page
import com.example.yomikaze_app_kotlin.Domain.Repositories.DownloadPageRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ImageRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import javax.inject.Inject

class DownloadPageRepositoryImpl @Inject constructor(
    private val chapterDao: ChapterDao,
    private val pageDao: PageDao,
    private val imageRepository: ImageRepository,
    private val pageRepository: PageRepository
) : DownloadPageRepository {
    override suspend fun downloadPagesOfChapter(
        token: String,
        comicId: Long,
        chapter: Chapter,
        context: Context
    ) {
        try {
            // Lấy danh sách các trang ảnh của chương

            val result =
                pageRepository.getPagesByChapterNumberOfComic(
                    "Bearer $token",
                    comicId,
                    chapter.number
                )
            if (result.isSuccess) {
                val pages = result.getOrThrow() // Lấy đối tượng Page
              //  Log.d("PageRepositoryImpl", "downloadPagesOfChapter: $pages")
                var totalSize = 0L
                var localPaths: MutableList<String> = mutableListOf()
                for ((index, pageUrl) in pages.pages.withIndex()) {
                    val imageResult =
                        imageRepository.downloadImageFromApi(APIConfig.imageAPIURL.toString() + pageUrl) // Tải hình ảnh từ URL

                    when (imageResult) {
                        is DownloadResult.Success -> {
                            val localPath = imageRepository.returnImageLocalPath(
                                imageResult.imageData,
                                context
                            ) // Lưu ảnh vào bộ nhớ trong
                            totalSize += imageResult.imageData.size.toLong()
                            localPaths.add(localPath ?: "")
                        }

                        is DownloadResult.Failure -> {
                            // Xử lý khi tải ảnh thất bại
                            Log.e(
                                "PageRepositoryImpl",
                                "Failed to download image for page $index"
                            )
                        }
                    }
                }
                Log.d("PageRepositoryImpl", "downloadPagesOfChapter: $localPaths")
                // Insert thông tin trang ảnh vào cơ sở dữ liệu
                pageDao.insertImage(
                    Page(
                        id = pages.id,
                        comicId = comicId,
                        number = pages.number,
                        name = pages.name,
                        pages = emptyList(),
                        creationTime = pages.creationTime,
                        imageLocalPaths = localPaths
                    )
                )
                // Cập nhật thông tin chương đã tải xuống
                chapter.size = totalSize
                chapter.isDownloaded = true
                chapterDao.updateChapterDownloaded(chapter)
            } else {
                throw result.exceptionOrNull() ?: Exception("Unknown error occurred")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}