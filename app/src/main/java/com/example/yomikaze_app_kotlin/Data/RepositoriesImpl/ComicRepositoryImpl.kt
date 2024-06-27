package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.content.Context
import android.util.Log
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ComicDao
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponseTest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import com.example.yomikaze_app_kotlin.Domain.Repositories.ImageRepository
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val api: ComicApiService,
    private val comicDao: ComicDao,
    private val imageRepository: ImageRepository
) : ComicRepository {

    /**
     * TODO: Implement the function to get hot comic banner images
     */
    override suspend fun getHotComicBannerImages(): Result<List<ComicResponseTest>> {
        return try {
            val response = api.getHotComicBannerImages()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: Implement the function to search comic by name
     */
    override suspend fun searchComic(
        token: String,
        comicNameQuery: String,
    ): Result<BaseResponse<ComicResponse>> {
        return try {
            val response = api.searchComicByName("Bearer $token", comicNameQuery)
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
        size: Int?
    ): Result<BaseResponse<ComicResponse>> {
        return try {
            val response = api.getComicByViewRanking("Bearer $token", orderByTotalViews, size)
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
        size: Int?
    ): Result<BaseResponse<ComicResponse>> {
        return try {
            val response =
                api.getComicByCommentsRanking("Bearer $token", orderByTotalComments, size)
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
        size: Int?
    ): Result<BaseResponse<ComicResponse>> {
        return try {
            val response = api.getComicByFollowRanking("Bearer $token", orderByTotalFollows, size)
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
        size: Int?
    ): Result<BaseResponse<ComicResponse>> {
        return try {
            val response = api.getComicByRatingRanking("Bearer $token", orderByTotalRatings, size)
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
     * TODO: Implement the function to download comic and add to database
     */
    override suspend fun insertComicDB(comic: ComicResponse, context: Context) {
        try {
            val imgUrlApi = APIConfig.imageAPIURL.toString()
            val coverResult = imageRepository.downloadImageFromApi(imgUrlApi + comic.cover)
            val bannerResult = imageRepository.downloadImageFromApi(imgUrlApi + comic.banner)

            var coverLocalPath: String? = null
            var bannerLocalPath: String? = null

            when (coverResult) {
                is ImageRepositoryImpl.DownloadResult.Success -> {
                    coverLocalPath = imageRepository.returnImageLocalPath(coverResult.imageData, context)
                    Log.d("ComicRepositoryImpl", "insertComicDB: Cover local path = $coverLocalPath")
                }
                ImageRepositoryImpl.DownloadResult.Failure -> {
                    // Xử lý khi tải ảnh bìa thất bại
                    Log.e("ComicRepositoryImpl", "Failed to download cover image")
                    // Có thể hiển thị thông báo cho người dùng hoặc xử lý theo cách khác tùy vào yêu cầu của ứng dụng
                }
            }
            when (bannerResult) {
                is ImageRepositoryImpl.DownloadResult.Success -> {
                    bannerLocalPath = imageRepository.returnImageLocalPath(bannerResult.imageData, context)
                    Log.d("ComicRepositoryImpl", "insertComicDB: Banner local path = $bannerLocalPath")
                }
                ImageRepositoryImpl.DownloadResult.Failure -> {
                    // Xử lý khi tải ảnh banner thất bại
                    Log.e("ComicRepositoryImpl", "Failed to download banner image")
                    // Có thể hiển thị thông báo cho người dùng hoặc xử lý theo cách khác tùy vào yêu cầu của ứng dụng
                }
            }

            // Chỉ thực hiện lưu vào cơ sở dữ liệu nếu cả hai ảnh đều tải xuống thành công
            if (coverLocalPath != null && bannerLocalPath != null) {
                comicDao.insertComicDB(
                    ComicResponse(
                        comicId = comic.comicId,
                        name = comic.name,
                        aliases = comic.aliases,
                        description = comic.description,
                        cover = coverLocalPath,
                        banner = bannerLocalPath,
                        publicationDate = comic.publicationDate,
                        authors = comic.authors,
                        status = comic.status,
                        tags = comic.tags,
                        averageRating = comic.averageRating,
                        views = comic.views,
                        follows = comic.follows,
                        comments = comic.comments,
                        totalChapters = comic.totalChapters,
                        totalMbs = comic.totalMbs
                    )
                )
            } else {
                // Xử lý khi ít nhất một trong hai ảnh không tải xuống thành công
                Log.e("ComicRepositoryImpl", "Failed to download one or both images")
                // Có thể hiển thị thông báo cho người dùng hoặc xử lý theo cách khác tùy vào yêu cầu của ứng dụng
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
}