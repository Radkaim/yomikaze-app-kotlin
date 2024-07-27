package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentRequest
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ReactionRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChapterCommentApiService {

    /**
     * TODO: use for get all chapter comment by comicId
     */
    @GET("comics/{comicId}/chapters/{number}/comments")
    suspend fun getAllChapterCommentByComicId(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("number") number: Int,
        @Query("OrderBy") orderBy: String? = null,
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int? = null,
    ): BaseResponse<CommentResponse>

    /**
     * TODO: use for get comment by comicId, chapter n  umber and commentId
     */
    @GET("comics/{comicId}/chapters/{number}/comments/{commentId}")
    suspend fun getMainChapterCommentByCommentId(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("number") number: Int,
        @Path("commentId") commentId: Long,
    ): CommentResponse

    /**
     * TODO: use for post chapter comment by comicId, chapter Number
     */
    @POST("comics/{comicId}/chapters/{number}/comments")
    suspend fun postChapterCommentByComicId(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("number") number: Int,
        @Body content: CommentRequest
    ): Response<CommentResponse>

    /**
     * TODO: use for post reply comment by comicId, chapter Number and commentId
     */
    @POST("comics/{comicId}/chapters/{number}/comments/{commentId}/replies")
    suspend fun postReplyChapterCommentByComicId(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("number") number: Int,
        @Path("commentId") commentId: Long,
        @Body content: CommentRequest
    ): Response<CommentResponse>

    /**
     * TODO: use for get all reply comment by comicId, chapter Number and commentId
     */
    @GET("comics/{comicId}/chapters/{number}/comments/{commentId}/replies")
    suspend fun getAllReplyChapterCommentByComicId(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("number") number: Int,
        @Path("commentId") commentId: Long,
        @Query("OrderBy") orderBy: String? = null,
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int? = null,
    ): BaseResponse<CommentResponse>

    /**
     * TODO: use for delete chapter comment by comicId, chapter Number and commentId
     */
    @DELETE("comics/{comicId}/chapters/{number}/comments/{commentId}")
    suspend fun deleteChapterCommentByComicId(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("number") number: Int,
        @Path("commentId") commentId: Long,
    ): Response<Unit>

    /**
     * TODO: use for update chapter comment by comicId, chapterNumber and commentId
     */
    @PATCH("comics/{comicId}/chapters/{number}/comments/{commentId}")
    suspend fun updateChapterCommentByComicId(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("number") number: Int,
        @Path("commentId") commentId: Long,
        @Body pathRequest: List<PathRequest>,
    ): Response<Unit>

    /**
     * TODO: use for react chapter comment by comicId, chapterNumber and commentId
     */
    @POST("comics/{comicId}/chapters/{number}/comments/{commentId}/reactions")
    suspend fun reactChapterCommentByComicId(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("number") number: Int,
        @Path("commentId") commentId: Long,
        @Body reactionRequest : ReactionRequest,
    ): Response<Unit>
}