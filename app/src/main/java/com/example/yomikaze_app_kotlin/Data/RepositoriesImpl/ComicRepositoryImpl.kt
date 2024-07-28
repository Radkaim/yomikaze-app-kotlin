package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.content.Context
import android.util.Log
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ChapterDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ComicDao
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.RatingRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ReportRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ReportResponse
import com.example.yomikaze_app_kotlin.Domain.Models.Tag
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ImageRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import retrofit2.Response
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val api: ComicApiService,
    private val comicDao: ComicDao,
    private val chapterDao: ChapterDao,
    private val imageRepository: ImageRepository,
    private val pageRepository: PageRepository
) : ComicRepository {


    /**
     * TODO: Implement the function to search comic by name
     */
    override suspend fun searchComic(
        token: String,
        comicNameQuery: String,
        size: Int?
    ): Result<BaseResponse<ComicResponse>> {
        return try {
            val response = api.searchComicByName("Bearer $token", comicNameQuery, size)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: Implement the function to advanced search comic
     */
    override suspend fun advancedSearchComic(
        token: String,
        queryMap: Map<String, String>
    ): Result<BaseResponse<ComicResponse>> {
        return try {
            val response = api.advancedSearchComic("Bearer $token", queryMap)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: use for get comic in ranking weekly
     */
    override suspend fun getComicWeekly(token: String): Result<List<ComicResponse>> {
        return try {
            val response = api.getComicWeekly("Bearer $token")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: Implement the function to get comic by view ranking
     */
    override suspend fun getComicByViewRanking(
        token: String,
        orderByTotalViews: String,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<ComicResponse>> {
        return try {
            val response = api.getComicByViewRanking("Bearer $token", orderByTotalViews, page, size)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: Implement the function to get comic by comments ranking
     */
    override suspend fun getComicByCommentsRanking(
        token: String,
        orderByTotalComments: String,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<ComicResponse>> {
        return try {
            val response =
                api.getComicByCommentsRanking("Bearer $token", orderByTotalComments, page, size)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: Implement the function to get comic by follow ranking
     */
    override suspend fun getComicByFollowRanking(
        token: String,
        orderByTotalFollows: String,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<ComicResponse>> {
        return try {
            val response =
                api.getComicByFollowRanking("Bearer $token", orderByTotalFollows, page, size)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: Implement the function to get comic by rating ranking
     */
    override suspend fun getComicByRatingRanking(
        token: String,
        orderByTotalRatings: String,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<ComicResponse>> {
        return try {
            val response =
                api.getComicByRatingRanking("Bearer $token", orderByTotalRatings, page, size)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: Implement the function to get comic details by comic id
     */
    override suspend fun getComicDetailsFromApi(
        token: String,
        comicId: Long,
    ): Result<ComicResponse> {
        return try {
            val response = api.getComicDetailsFromAPI("Bearer $token", comicId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: Implement the function to rate a comic
     *
     */
    override suspend fun rateComic(
        token: String,
        comicId: Long,
        rating: RatingRequest
    ): Response<Unit> {
        // check status code of response
        val response = api.rateComic("Bearer $token", comicId, rating)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("ComicRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("ComicRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                }
            }
            Result.failure(Exception("Failed to rate comic"))
        }
        return response
    }


    /**
     * ---------------------------------------------------------------------------------------------------
     * TODO: Use For DATABASE
     */

    /**
     * TODO: Implement the function to download comic and add to database
     */
    override suspend fun insertComicDB(comic: ComicResponse, context: Context) {
        try {
            val imgUrlApi = APIConfig.imageAPIURL.toString()
            val coverResult = imageRepository.downloadImageFromApi(imgUrlApi + comic.cover)
            val bannerResult = imageRepository.downloadImageFromApi(imgUrlApi + comic.banner)

            var coverLocalPath: String? = null
            var bannerLocalPath: String? = null

            if (coverResult is DownloadResult.Success) {
                coverLocalPath =
                    imageRepository.returnImageLocalPath(coverResult.imageData, context)
                Log.d("ComicRepositoryImpl", "insertComicDB: Cover local path = $coverLocalPath")
            } else {
                Log.e("ComicRepositoryImpl", "Failed to download cover image")
            }

            if (bannerResult is DownloadResult.Success) {
                bannerLocalPath =
                    imageRepository.returnImageLocalPath(bannerResult.imageData, context)
                Log.d("ComicRepositoryImpl", "insertComicDB: Banner local path = $bannerLocalPath")
            } else {
                Log.e("ComicRepositoryImpl", "Failed to download banner image")
            }
            // get name of list Tags
            val tags = comic.tags.map { it.name }
            Log.d("ComicRepositoryImpl", "insertComicDB: Tags = $tags")

            // Chỉ thực hiện lưu vào cơ sở dữ liệu nếu cả hai ảnh đều tải xuống thành công
//            if (coverLocalPath != null && bannerLocalPath != null) {
            comicDao.insertComicDB(
                ComicResponse(
                    comicId = comic.comicId,
                    name = comic.name,
                    aliases = comic.aliases,
                    description = comic.description,
                    cover = coverLocalPath ?: "",
                    banner = bannerLocalPath ?: "",
                    publicationDate = comic.publicationDate,
                    authors = comic.authors,
                    status = comic.status,
                    tags = comic.tags,
                    tagDB = tags,
                    totalRatings = comic.totalRatings,
                    averageRating = comic.averageRating,
                    views = comic.views,
                    follows = comic.follows,
                    comments = comic.comments,
                    totalChapters = comic.totalChapters,
                    totalMbs = comic.totalMbs,
                )
            )
//            } else {
//                // Xử lý khi ít nhất một trong hai ảnh không tải xuống thành công
//                Log.e("ComicRepositoryImpl", "Failed to insert comics to database")
//                // Có thể hiển thị thông báo cho người dùng hoặc xử lý theo cách khác tùy vào yêu cầu của ứng dụng
//            }
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Todo delete a comic in database by comic id and all related data
     */
    override suspend fun deleteComicByIdDB(comicId: Long, context: Context) {
        try {
            val comic = comicDao.getComicByIdDB(comicId)
            if (comic != null) {
                // Xóa ảnh bìa
                imageRepository.deleteImageFromLocal(comic.cover)
                // Xóa ảnh banner
                imageRepository.deleteImageFromLocal(comic.banner)

                //get all number of chapter by comicId
                val chapters = chapterDao.getListChaptersDownloadedByComicId(comicId)
                val numberChaptersInDB = mutableListOf<Int>()

                chapters.forEach {
                    numberChaptersInDB.add(it.number)
                }

                numberChaptersInDB.forEach {
                    pageRepository.deletePageByComicIdAndChapterNumberDB(comicId, it)
                }


                // Xóa các ảnh của các chapter

                // Xóa comic
                comicDao.deleteComicByIdDB(comicId)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * TODO: Implement the function to get all comic downloaded in database
     */
    override suspend fun getAllComicsDownloadedDB(): Result<List<ComicResponse>> {
        return try {
            val response = comicDao.getAllComicsDownloadedDB()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: Implement the function to get comic by comic id in database
     */
    override suspend fun getComicByIdDB(comicId: Long): ComicResponse {
        return try {
            comicDao.getComicByIdDB(comicId)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * TODO: Implement the function to update total mbs of comic in database
     */
    override suspend fun updateTotalMbsOfComic(comicId: Long, totalsMbs: Long) {
        try {
            comicDao.updateTotalMbsOfComic(comicId, totalsMbs)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * TODO: get tags
     */
    override suspend fun getTags(
        token: String,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<Tag>> {
        return try {
            val response = api.getTags("Bearer $token", page, size)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: get common report reasons of comic
     */
    override suspend fun getComicCommonReportReasons(): Result<List<ReportResponse>> {
        return try {
            val response = api.getComicCommonReportReasons()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: use for report a comic
     *
     */
    override suspend fun reportComic(
        token: String,
        comicId: Long,
        reportRequest: ReportRequest,
    ): Response<Unit> {

        val response = api.reportComic("Bearer $token", comicId, reportRequest)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("ComicRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("ComicRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                }
            }
            Result.failure(Exception("Failed to report comic"))
        }
        return response
    }
}

