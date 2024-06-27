package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import com.example.yomikaze_app_kotlin.Domain.Models.Page
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import javax.inject.Inject

class PageRepositoryImpl @Inject constructor(

) : PageRepository {

    override suspend fun getPagesByChapterIndex(chapterIndex: Int): Result<List<Page>> {
        return Result.success(listOf())
    }


}