package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.content.Context
import android.util.Log
import com.example.yomikaze_app_kotlin.Data.DataSource.API.PageApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ChapterDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.PageDao
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Models.Page
import com.example.yomikaze_app_kotlin.Domain.Repositories.ImageRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PageRepositoryImpl @Inject constructor(
    private val chapterDao: ChapterDao,
    private val pageDao: PageDao,
    private val imageRepository: ImageRepository,
    //  private val pageRepository: PageRepository,
    private val api: PageApiService,
) : PageRepository {

    @Inject
    lateinit var pageRepository: PageRepository
    override suspend fun getPagesByChapterNumberOfComic(
        comicId: Long,
        chapterIndex: Int
    ): Result<Page> {
        return try {
            val response = api.getPagesByChapterNumberOfComic(comicId, chapterIndex)
            Log.d("PageRepositoryImpl", "getPagesByChapterNumberOfComic: $response")

            if (response.isSuccessful) {
                val page = response.body()
                if (page != null) {
                    Result.success(page)
                } else {
                    Result.failure(Throwable("No pages found"))
                }
            } else {
                Result.failure(Throwable("API call failed: ${response.code()} ${response.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun downloadPagesOfChapter(comicId: Long, chapter: Chapter, context: Context) {
        try {
            // Lấy danh sách các trang ảnh của chương

            val result = pageRepository.getPagesByChapterNumberOfComic(comicId, chapter.number)
            if (result.isSuccess) {
                val pages = result.getOrThrow() // Lấy đối tượng Page

                var totalSize = 0L

                for ((index, pageUrl) in pages.pages.withIndex()) {
                    val imageResult =
                        imageRepository.downloadImageFromApi(pageUrl) // Tải hình ảnh từ URL

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
                                "Failed to download image for page $index"
                            )
                        }
                    }

                    // Insert thông tin trang ảnh vào cơ sở dữ liệu
                    pageDao.insertImage(
                        Page(
                            id = pages.id,
                            comicId = comicId,
                            number = pages.number,
                            name = pages.name,
                            pages = listOf(pageUrl),
                            creationTime = pages.creationTime,
                            imageLocalPaths = listOf(localPath ?: "")
                        )
                    )
                }
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