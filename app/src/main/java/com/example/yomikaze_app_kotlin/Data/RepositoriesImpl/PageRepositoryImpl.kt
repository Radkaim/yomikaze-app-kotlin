package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.util.Log
import com.example.yomikaze_app_kotlin.Data.DataSource.API.PageApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ChapterDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.PageDao
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
    private val api: PageApiService,
) : PageRepository {


    override suspend fun getPagesByChapterNumberOfComic(
        token: String,
        comicId: Long,
        chapterIndex: Int
    ): Result<Page> {
        return try {
            val response =
                api.getPagesByChapterNumberOfComic("Bearer $token", comicId, chapterIndex)
            Log.d("PageRepositoryImpl", "getPagesByChapterNumberOfComic: $response")

            if (response.isSuccessful) {
                val page = response.body()
                if (page != null) {
                    Result.success(page)
                } else {
                    Result.failure(Throwable("No pages found"))
                }
            } else {
                if (response.code() == 403) {
                    Result.failure(Throwable("403"))
                } else if (response.code() == 401) {
                    Result.failure(Throwable("401"))
                } else {
                    Result.failure(Throwable("API call failed: ${response.code()} ${response.message()}"))
                }
            }
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }


    override suspend fun getPageByComicIdAndChapterNumberDB(
        comicId: Long,
        number: Int
    ): Page {
        return pageDao.getPageByComicIdAndChapterNumberDB(comicId, number)
    }

    override suspend fun deletePageByComicIdAndChapterNumberDB(comicId: Long, number: Int) {
        val page = pageDao.getPageByComicIdAndChapterNumberDB(comicId, number)
        if (page.imageLocalPaths == null) {
            return
        }
        page.imageLocalPaths.forEach { imagePath ->
            imageRepository.deleteImageFromLocal(imagePath)
        }

        pageDao.deletePage(page)
    }
}