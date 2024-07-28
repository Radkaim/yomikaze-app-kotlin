package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment

import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterCommentRepository
import retrofit2.Response
import javax.inject.Inject

class UpdateChapterCommentUC @Inject constructor(
    private val chapterCommentRepository: ChapterCommentRepository
) {

    suspend fun updateChapterComment(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long,
        pathRequest: List<PathRequest>
    ):Response<Unit> {
       return chapterCommentRepository.updateChapterComment(
            token,
            comicId,
            chapterNumber,
            commentId,
            pathRequest
        )
    }
}