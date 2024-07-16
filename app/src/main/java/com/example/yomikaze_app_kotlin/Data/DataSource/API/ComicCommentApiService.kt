package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicCommentApiService {

    /**
     * TODO: use for get all comic comment by comicId
     */
    @GET("comics/{comicId}/comments")
    suspend fun getAllComicCommentByComicId(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Query("OrderBy") orderBy: String? = null,
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int? = null,
    ): BaseResponse<CommentResponse>

    /**
     * TODO: use for post comic comment by comicId
     */
    @POST("comics/{comicId}/comments")
    suspend fun postComicCommentByComicId(
        @Header("Authorization") token: String,
        @Query("Content") content: String,
    ): BaseResponse<CommentResponse>
}