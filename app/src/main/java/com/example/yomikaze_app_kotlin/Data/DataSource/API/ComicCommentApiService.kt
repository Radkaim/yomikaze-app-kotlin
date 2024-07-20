package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentRequest
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
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
        @Path("comicId") comicId: Long,
        @Body content: CommentRequest
    ): Response<Unit>

    /**
     * TODO: use for post reply comment by comicId and commentId
     */
    @POST("comics/{comicId}/comments/{commentId}/replies")
    suspend fun postReplyCommentByComicId(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("commentId") commentId: Long,
        @Body content: CommentRequest
    ): Response<Unit>

    /**
     * TODO: use for get all reply comment by comicId and commentId
     */
    @GET("comics/{comicId}/comments/{commentId}/replies")
    suspend fun getAllReplyCommentByComicId(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("commentId") commentId: Long,
        @Query("OrderBy") orderBy: String? = null,
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int? = null,
    ): BaseResponse<CommentResponse>

    /**
     * TODO: use for delete comic comment by comicId and commentId
     */
    @DELETE("comics/{comicId}/comments/{commentId}")
    suspend fun deleteComicCommentByComicId(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("commentId") commentId: Long,
    ): Response<Unit>

    /**
     * TODO: use for update comic comment by comicId and commentId
     */
    @PATCH("comics/{comicId}/comments/{commentId}")
    suspend fun updateComicCommentByComicId(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("commentId") commentId: Long,
        @Body pathRequest: List<PathRequest>,
    ): Response<Unit>

}