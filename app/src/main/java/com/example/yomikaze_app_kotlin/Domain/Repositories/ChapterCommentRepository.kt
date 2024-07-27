package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentRequest
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ReactionRequest
import retrofit2.Response

interface ChapterCommentRepository {

    /**
     * TODO: use for get all comment of chapter by comicId, chapter number
     */
    suspend fun getAllChapterCommentByComicId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        orderBy: String? = null,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<CommentResponse>>

    /**
     * TODO: use for get main chapter comment by comicId and commentId, chapter number
     */
    suspend fun getMainChapterCommentByCommentId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long
    ): Result<CommentResponse>

    /**
     * TODO: use for post comment of comic by comicId,
     */
    suspend fun postChapterCommentByComicId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        content: CommentRequest
    ): Response<CommentResponse>

    /**
     * TODO: use for post reply comment by comicId and commentId, chapter number
     */
    suspend fun postReplyChapterCommentByComicId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long,
        content: CommentRequest
    ): Response<CommentResponse>

    /**
     * TODO: use for get all reply comment by comicId and commentId
     */
    suspend fun getAllReplyCommentByComicId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long,
        orderBy: String? = null,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<CommentResponse>>

    /**
     * TODO: use for delete chapter comment by comicId and commentId, chapter number
     */
    suspend fun deleteChapterCommentByComicId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long
    ): Response<Unit>

    /**
     * TODO: use for update comic comment by comicId and commentId
     */
    suspend fun updateChapterCommentByComicId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long,
        pathRequest: List<PathRequest>
    ): Response<Unit>

    /**
     * TODO: use for react comic comment by comicId and commentId
     */
    suspend fun reactChapterCommentByComicId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long,
        reactionRequest: ReactionRequest
    ): Response<Unit>
}