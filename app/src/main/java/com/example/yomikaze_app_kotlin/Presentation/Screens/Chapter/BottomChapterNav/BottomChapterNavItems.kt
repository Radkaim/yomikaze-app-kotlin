package com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter.BottomChapterNav

import com.example.yomikaze_app_kotlin.R


sealed class BottomChapterNavItems(var title: String, var icon: Int, var screen_route: String) {
  object PreviousChapter : BottomChapterNavItems ("Previous", R.drawable.ic_previous_chapter, "previous_chapter_route")
  object ListChapter : BottomChapterNavItems ("Chapters", R.drawable.ic_chapter, "list_chapter_route")
  object Comment : BottomChapterNavItems ("Comment", R.drawable.ic_comment, "comment_route")
  object Setting : BottomChapterNavItems ("Setting", R.drawable.ic_setting, "setting_route")
  object NextChapter : BottomChapterNavItems ("Next", R.drawable.ic_next, "next_chapter_route")
}