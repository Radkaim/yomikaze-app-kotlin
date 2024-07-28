package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment

import com.example.yomikaze_app_kotlin.Domain.Models.ReactionRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterCommentRepository
import retrofit2.Response
import javax.inject.Inject

class ReactChapterCommentUC @Inject constructor(
    private val chapterCommentRepository: ChapterCommentRepository
) {
    suspend fun reactChapterComment(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long,
        reactionRequest: ReactionRequest
    ): Response<Unit> {
        return chapterCommentRepository.reactChapterComment(
            token,
            comicId,
            chapterNumber,
            commentId,
            reactionRequest
        )
    }
}