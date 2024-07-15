package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch


//initial const for status of comic
enum class ComicStatus {
    OnGoing,
    Completed,
    Hiatus,
    Cancelled
}
enum class Mode {
    And,
    Or
}
enum class OrderBy {
    Name,
    NameDesc,
    PublishedDate,
    PublishedDateDesc,
    CreateTime,
    CreateTimeDesc,
    LastModified,
    LastModifiedDesc,
    TotalChapters,
    TotalChaptersDesc,
    TotalViews,
    TotalViewsDesc,
    AverageRating,
    AverageRatingDesc,
    TotalFollows,
    TotalFollowsDesc,
    TotalComments,
    TotalCommentsDesc
}

data class AdvancedSearchState(

    val queryByComicName: String = "",

    val listAuthorsInput: List<String> = emptyList(),
    val queryByAuthor: String = "",

    val queryByPublisher: String = "",

    val queryByStatus: ComicStatus? = null,

    val queryFromPublishedDate: String = "",
    val queryToPublishedDate: String = "",

    val queryFromTotalChapters: Int? = null,
    val queryToTotalChapters: Int? = null,

    val queryFromTotalViews: Int? = null,
    val queryToTotalViews: Int? = null,

    val queryFromAverageRating: Float? = null,
    val queryToAverageRating: Float? = null,

    val queryFromTotalFollows: Int? = null,
    val queryToTotalFollows: Int? = null,

    //genre
    val queryIncludeTags: List<String> = emptyList(),
    val queryExcludeTags: List<String> = emptyList(),

    val queryInclusionMode: Mode? = null,
    val queryExclusionMode: Mode? = null,

    val queryOrderBy: OrderBy? = null,

    val querySize: Int? = null,
    val queryPage: Int? = null,


    val searchResults: List<String> = emptyList(),
    val isLoading: Boolean = false
)



//// Thêm Authors vào queryMap dưới dạng chuỗi
//val authors = listOf("Author1", "Author2")
//if (authors.isNotEmpty()) {
//    authors.forEachIndexed { index, author ->
//        queryMap["Authors[$index]"] = author
//    }
//}

//val queryMap = mutableMapOf<String, String?>()
//queryMap["Name"] = name
//queryMap["Size"] = size?.toString()
//queryMap["OrderBy"] = if (shouldOrderByTotalViews) "TotalViews" else null
//queryMap["OrderByName"] = if (shouldOrderByName) "Name" else null