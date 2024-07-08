package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch

data class AdvancedSearchState(
    val queryByName: String = "",
    val queryByAuthors: List<String> = emptyList(),
    val queryByAuthor: String = "",
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