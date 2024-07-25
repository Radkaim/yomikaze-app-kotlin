package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentRequest
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ReactionRequest
import retrofit2.Response

interface ComicCommentRepository {

    /**
     * TODO: use for get all comment of comic by comicId
     */
    suspend fun getAllComicCommentByComicId(
        token: String,
        comicId: Long,
        orderBy: String? = null,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<CommentResponse>>

    /**
     * TODO: use for get comment by comicId and commentId
     */
    suspend fun getMainCommentByCommentId(
        token: String,
        comicId: Long,
        commentId: Long
    ): Result<CommentResponse>

    /**
     * TODO: use for post comment of comic by comicId
     */
    suspend fun postComicCommentByComicId(
        token: String,
        comicId: Long,
        content: CommentRequest
    ): Response<CommentResponse>

    /**
     * TODO: use for post reply comment by comicId and commentId
     */
    suspend fun postReplyCommentByComicId(
        token: String,
        comicId: Long,
        commentId: Long,
        content: CommentRequest
    ): Response<CommentResponse>

    /**
     * TODO: use for get all reply comment by comicId and commentId
     */
    suspend fun getAllReplyCommentByComicId(
        token: String,
        comicId: Long,
        commentId: Long,
        orderBy: String? = null,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<CommentResponse>>

    /**
     * TODO: use for delete comic comment by comicId and commentId
     */
    suspend fun deleteComicCommentByComicId(
        token: String,
        comicId: Long,
        commentId: Long
    ): Response<Unit>

    /**
     * TODO: use for update comic comment by comicId and commentId
     */
    suspend fun updateComicCommentByComicId(
        token: String,
        comicId: Long,
        commentId: Long,
        pathRequest: List<PathRequest>
    ): Response<Unit>

    /**
     * TODO: use for react comic comment by comicId and commentId
     */
    suspend fun reactComicCommentByComicId(
        token: String,
        comicId: Long,
        commentId: Long,
        reactionRequest: ReactionRequest
    ): Response<Unit>

}