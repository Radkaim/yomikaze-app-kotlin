package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment

import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterCommentRepository
import retrofit2.Response
import javax.inject.Inject

class DeleteChapterCommentUC @Inject constructor(
    private val chapterCommentRepository: ChapterCommentRepository
) {
    suspend fun deleteChapterComment(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long
    ): Response<Unit> {
        return chapterCommentRepository.deleteChapterComment(
            token,
            comicId,
            chapterNumber,
            commentId
        )
    }
}