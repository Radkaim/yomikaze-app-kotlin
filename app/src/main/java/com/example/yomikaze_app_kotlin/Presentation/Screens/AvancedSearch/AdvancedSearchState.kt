package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.Tag


//initial const for status of comic
enum class ComicStatus {
    None,
    OnGoing,
    Completed,
    Hiatus,
    Cancelled
}
enum class Mode {
    None,
    And,
    Or
}
enum class OrderBy {
    None,
    Name,
    NameDesc,
    PublishedDate,
    PublishedDateDesc,
//    CreateTime,
    CreateTimeDesc,
//    LastModified,
    LastModifiedDesc,
    TotalChapters,
    TotalChaptersDesc,
    TotalViews,
    TotalViewsDesc,
//    AverageRating,
    AverageRatingDesc,
//    TotalFollows,
    TotalFollowsDesc,
//    TotalComments,
    TotalCommentsDesc
}

data class AdvancedSearchState(

    var queryByComicName: String = "",

    val queryListAuthorsInput: List<String> = emptyList(),
//    val queryByAuthor: String = "",

    val queryByPublisher: String = "",

    val queryByStatus: ComicStatus? = null,
    val selectedStatus: ComicStatus = ComicStatus.None,

    val queryFromPublishedDate: String? = null,
    val queryToPublishedDate: String? = null,

    val queryFromTotalChapters: Int? = null,
    val queryToTotalChapters: Int? = null,

    val queryFromTotalViews: Int? = null,
    val queryToTotalViews: Int? = null,

    val queryFromAverageRating: Float? = null,
    val queryToAverageRating: Float? = null,


    val queryFromTotalFollows: Int? = null,
    val queryToTotalFollows: Int? = null,

    //genre
    val queryIncludeTags: List<Long> = emptyList(),
    val queryExcludeTags: List<Long> = emptyList(),

    val queryInclusionMode: Mode? = null,
    val queryExclusionMode: Mode? = null,


    val queryOrderBy: OrderBy? = null,
    val selectedOrderBy: OrderBy = OrderBy.None,

    val querySize: Int? = null,
    val queryPage: Int? = null,


    val totalResults: Int? = 0,
    val searchResults: List<ComicResponse> = emptyList(),
    val isSearchLoading: Boolean = false,


    val tags: List<Tag> = emptyList(),
    val isTagsLoading: Boolean = false,
)

