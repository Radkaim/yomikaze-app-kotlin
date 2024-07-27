package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.UseCases.AdvancedSearchComicUC
import com.example.yomikaze_app_kotlin.Domain.UseCases.GetTagsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdvancedSearchViewModel @Inject constructor(
    private val appPreference: AppPreference,
    private val advancedSearchComicUC: AdvancedSearchComicUC,
    private val getTagsUC: GetTagsUC
) : ViewModel() {

    private var navController: NavController? = null


    //for StatefulViewModel
    private val _state = MutableStateFlow(AdvancedSearchState())
    val state: StateFlow<AdvancedSearchState> get() = _state

    // for Navigation
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun onNavigateComicDetail(comicId: Long) {
        navController?.navigate("comic_detail_route/$comicId")
    }


    private val _tagStates = MutableStateFlow<Map<Long, TagState>>(emptyMap())
    val tagStates: StateFlow<Map<Long, TagState>> get() = _tagStates

    fun toggleTagState(tagId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _tagStates.value[tagId] ?: TagState.NONE
            val newState = when (currentState) {
                TagState.NONE -> TagState.INCLUDE
                TagState.INCLUDE -> TagState.EXCLUDE
                TagState.EXCLUDE -> TagState.NONE
            }
            _tagStates.value = _tagStates.value.toMutableMap().apply { put(tagId, newState) }
//            Log.d("AdvancedSearchViewModel", "tagStates: ${_tagStates.value}")
        }
    }

    fun updateQueryTags() {
        viewModelScope.launch(Dispatchers.IO) {
            val includeTags = _tagStates.value.filterValues { it == TagState.INCLUDE }.keys.toList()
            val excludeTags = _tagStates.value.filterValues { it == TagState.EXCLUDE }.keys.toList()
            _state.value = _state.value.copy(
                queryIncludeTags = includeTags,
                queryExcludeTags = excludeTags
            )
//            Log.d("AdvancedSearchViewModel", "queryIncludeTags: ${_state.value.queryIncludeTags}")
//            Log.d("AdvancedSearchViewModel", "queryExcludeTags: ${_state.value.queryExcludeTags}")
        }
    }

    fun updateOrderBy(orderBy: OrderBy) {
        viewModelScope.launch(Dispatchers.IO) {
            updateQueryOrderBy(orderBy)
            _state.value = _state.value.copy(selectedOrderBy = orderBy)
        }
    }

    fun updateStatus(status: ComicStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            updateQueryByStatus(status)
            _state.value = _state.value.copy(selectedStatus = status)
        }
    }

    //reset updateOrderBy
    fun resetOrderBy() {
        viewModelScope.launch(Dispatchers.IO) {
            updateQueryOrderBy(OrderBy.Name)
            _state.value = _state.value.copy(selectedOrderBy = OrderBy.None)
            updateQueryOrderBy(null)
        }
    }

    //reser status
    fun resetStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            updateQueryByStatus(ComicStatus.None)
            _state.value = _state.value.copy(selectedStatus = ComicStatus.None)
            updateQueryByStatus(null)
        }
    }


    //reset all tags
    fun resetTags() {
        viewModelScope.launch(Dispatchers.IO) {
            _tagStates.value = _tagStates.value.mapValues { TagState.NONE }
            updateQueryTags()
        }
    }

    //reset average rating
    fun resetAverageRatingValue() {
        viewModelScope.launch(Dispatchers.IO) {
            updateQueryFromAverageRating(null)
            updateQueryToAverageRating(null)
        }
    }

    // reset total views
    fun resetTotalViewsValue() {
        viewModelScope.launch(Dispatchers.IO) {
            updateQueryFromTotalViews(null)
            updateQueryToTotalViews(null)
        }
    }

    //reset total follows
    fun resetTotalFollowsValue() {
        viewModelScope.launch(Dispatchers.IO) {
            updateQueryFromTotalFollows(null)
            updateQueryToTotalFollows(null)
        }
    }

    //reset published date
    fun resetPublishedDateValue() {
        viewModelScope.launch(Dispatchers.IO) {
            updateQueryFromPublishedDate("")
            updateQueryToPublishedDate("")
        }
    }

    //reset total chapters
    fun resetTotalChaptersValue() {
        viewModelScope.launch(Dispatchers.IO) {
            updateQueryFromTotalChapters(null)
            updateQueryToTotalChapters(null)
        }
    }

    fun resetAuthors() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(queryListAuthorsInput = emptyList())
        }
    }

    fun resetInclusionExclusionMode() {
        viewModelScope.launch(Dispatchers.IO) {
            updateQueryInclusionMode(Mode.None)
            updateQueryExclusionMode(Mode.None)
        }
    }


    //reset all state
    fun resetState() {
        viewModelScope.launch(Dispatchers.IO) {
            updateQueryByComicName("")

            updateQueryByPublisher("")

            updateQueryByStatus(null)

            updateQueryFromPublishedDate("")
            updateQueryToPublishedDate("")

            updateQueryFromTotalChapters(null)
            updateQueryToTotalChapters(null)

            updateQueryFromTotalViews(null)
            updateQueryToTotalViews(null)

            updateQueryFromAverageRating(null)
            updateQueryToAverageRating(null)

            updateQueryFromTotalFollows(null)
            updateQueryToTotalFollows(null)


            updateQueryIncludeTags(emptyList())
            resetTags()

            resetStatus()
            resetOrderBy()

            resetAuthors()

            resetInclusionExclusionMode()

        }
    }


    //update each state
    fun updateQueryByComicName(queryByComicName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(queryByComicName = queryByComicName)
        }
    }

    fun updateListAuthorsInput(listAuthorsInput: String) {
        //add author to list
        viewModelScope.launch(Dispatchers.IO) {
            val listAuthors = _state.value.queryListAuthorsInput.toMutableList()
            listAuthors.add(listAuthorsInput)
            _state.value = _state.value.copy(queryListAuthorsInput = listAuthors)
        }
    }

    fun removeAuthor(author: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listAuthors = _state.value.queryListAuthorsInput.toMutableList()
            listAuthors.remove(author)
            _state.value = _state.value.copy(queryListAuthorsInput = listAuthors)
        }
    }

//    fun updateQueryByAuthor(queryByAuthor: String) {
//        _state.value = _state.value.copy(queryByAuthor = queryByAuthor)
//    }

    fun updateQueryByPublisher(queryByPublisher: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(queryByPublisher = queryByPublisher)
        }
    }

    fun updateQueryByStatus(queryByStatus: ComicStatus?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (queryByStatus == ComicStatus.None) {
                _state.value = _state.value.copy(queryByStatus = null)
            } else {
                _state.value = _state.value.copy(queryByStatus = queryByStatus)
            }

        }
    }

    fun updateQueryFromPublishedDate(queryFromPublishedDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (queryFromPublishedDate.isEmpty()) {
                _state.value = _state.value.copy(queryFromPublishedDate = null)
            } else {
                Log.d("AdvancedSearchViewModel", "queryFromPublishedDate: $queryFromPublishedDate")
                _state.value = _state.value.copy(queryFromPublishedDate = queryFromPublishedDate)
            }
//            _state.value = _state.value.copy(queryFromPublishedDate = queryFromPublishedDate)
        }
    }

    fun updateQueryToPublishedDate(queryToPublishedDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (queryToPublishedDate.isEmpty()) {
                _state.value = _state.value.copy(queryToPublishedDate = null)
            } else {
                Log.d("AdvancedSearchViewModel", "queryToPublishedDate: $queryToPublishedDate")
                _state.value = _state.value.copy(queryToPublishedDate = queryToPublishedDate)
            }
        }
    }

    fun updateQueryFromTotalChapters(queryFromTotalChapters: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(queryFromTotalChapters = queryFromTotalChapters)
        }
    }

    fun updateQueryToTotalChapters(queryToTotalChapters: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(queryToTotalChapters = queryToTotalChapters)
        }
    }

    fun updateQueryFromTotalViews(queryFromTotalViews: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
//            Log.d("AdvancedSearchViewModel", "queryFromTotalViews: $queryFromTotalViews")
            _state.value = _state.value.copy(queryFromTotalViews = queryFromTotalViews)
        }
    }

    fun updateQueryToTotalViews(queryToTotalViews: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
//            Log.d("AdvancedSearchViewModel", "queryToTotalViews: $queryToTotalViews")
            _state.value = _state.value.copy(queryToTotalViews = queryToTotalViews)
        }
    }

    fun updateQueryFromAverageRating(queryFromAverageRating: Float?) {
        viewModelScope.launch(Dispatchers.IO) {
//            Log.d("AdvancedSearchViewModel", "queryFromAverageRating: ${queryFromAverageRating}")
            _state.value = _state.value.copy(queryFromAverageRating = queryFromAverageRating)
        }
    }


    fun updateQueryToAverageRating(queryToAverageRating: Float?) {
        viewModelScope.launch(Dispatchers.IO) {
//            Log.d("AdvancedSearchViewModel", "queryToAverageRating: $queryToAverageRating")
            _state.value = _state.value.copy(queryToAverageRating = queryToAverageRating)
        }
    }

    fun updateQueryFromTotalFollows(queryFromTotalFollows: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(queryFromTotalFollows = queryFromTotalFollows)
        }
    }

    fun updateQueryToTotalFollows(queryToTotalFollows: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(queryToTotalFollows = queryToTotalFollows)
        }
    }

    fun updateQueryIncludeTags(queryIncludeTags: List<Long>) {
        _state.value = _state.value.copy(queryIncludeTags = queryIncludeTags)
    }

    fun updateQueryExcludeTags(queryExcludeTags: List<Long>) {
        _state.value = _state.value.copy(queryExcludeTags = queryExcludeTags)
    }

    fun updateQueryInclusionMode(queryInclusionMode: Mode?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (queryInclusionMode == Mode.None) {
                _state.value = _state.value.copy(queryInclusionMode = null)
                return@launch
            } else {
                _state.value = _state.value.copy(queryInclusionMode = queryInclusionMode)
            }
        }
    }

    fun updateQueryExclusionMode(queryExclusionMode: Mode?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (queryExclusionMode == Mode.None) {
                _state.value = _state.value.copy(queryExclusionMode = null)
                return@launch
            } else {
                _state.value = _state.value.copy(queryExclusionMode = queryExclusionMode)
            }
        }
    }

    fun updateQueryOrderBy(queryOrderBy: OrderBy?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (queryOrderBy == OrderBy.None) {
                _state.value = _state.value.copy(queryOrderBy = null)
//                return@launch
            } else {
                _state.value = _state.value.copy(queryOrderBy = queryOrderBy)
            }
        }
    }

    fun updateQuerySize(querySize: Int) {
        _state.value = _state.value.copy(querySize = querySize)
    }

    fun updateQueryPage(queryPage: Int) {
        _state.value = _state.value.copy(queryPage = queryPage)
    }

    // Function to execute advanced search
    fun performAdvancedSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            val queryMap = mutableMapOf<String, String>()

            _state.value.queryByComicName.takeIf { it.isNotEmpty() }?.let {
                queryMap["Name"] = it
            }
            _state.value.queryListAuthorsInput.takeIf { it.isNotEmpty() }
                ?.forEachIndexed { index, author ->
                    queryMap["Authors[$index]"] = author
                }
//            _state.value.queryByAuthor.takeIf { it.isNotEmpty() }?.let {
//                queryMap["Author"] = it
//            }
            _state.value.queryByPublisher.takeIf { it.isNotEmpty() }?.let {
                queryMap["Publisher"] = it
            }
            _state.value.queryByStatus?.let {
                queryMap["Status"] = it.name
            }
            _state.value.queryFromPublishedDate?.takeIf { it.isNotEmpty() }?.let {
                queryMap["FromPublicationDate"] = it
            }
            _state.value.queryToPublishedDate?.takeIf { it.isNotEmpty() }?.let {
                queryMap["ToPublicationDate"] = it
            }
            _state.value.queryFromTotalChapters?.let {
                queryMap["FromTotalChapters"] = it.toString()
            }
            _state.value.queryToTotalChapters?.let {
                queryMap["ToTotalChapters"] = it.toString()
            }
            _state.value.queryFromTotalViews?.let {
                queryMap["FromTotalViews"] = it.toString()
            }
            _state.value.queryToTotalViews?.let {
                queryMap["ToTotalViews"] = it.toString()
            }
            _state.value.queryFromAverageRating?.let {
                queryMap["FromAverageRating"] = it.toString()
            }
            _state.value.queryToAverageRating?.let {
                queryMap["ToAverageRating"] = it.toString()
            }
            _state.value.queryFromTotalFollows?.let {
                queryMap["FromTotalFollows"] = it.toString()
            }
            _state.value.queryToTotalFollows?.let {
                queryMap["ToTotalFollows"] = it.toString()
            }
            _state.value.queryIncludeTags.takeIf { it.isNotEmpty() }?.let {
                queryMap["IncludeTags"] = it.joinToString(",")
            }
            _state.value.queryExcludeTags.takeIf { it.isNotEmpty() }?.let {
                queryMap["ExcludeTags"] = it.joinToString(",")
            }
            _state.value.queryInclusionMode?.let {
                queryMap["InclusionMode"] = it.name
            }
            _state.value.queryExclusionMode?.let {
                queryMap["ExclusionMode"] = it.name
            }
            _state.value.queryOrderBy?.let {
                queryMap["OrderBy"] = it.name
            }
            _state.value.querySize?.let {
                queryMap["Size"] = it.toString()
            }
            _state.value.queryPage?.let {
                queryMap["Page"] = it.toString()
            }

            _state.value = _state.value.copy(isSearchLoading = true)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!

            try {
                Log.d("AdvancedSearchViewModel", "queryMap: $queryMap")
                val result = advancedSearchComicUC.executeAdvancedSearchComic(token, queryMap)
                result.fold(
                    onSuccess = { baseResponse ->
                        _state.value = _state.value.copy(
                            searchResults = baseResponse.results,
                            totalResults = baseResponse.totals
                        )
//                        Log.d("AdvancedSearchViewModel", "searchResults: ${baseResponse.results}")
                    },
                    onFailure = { throwable ->
                        // handle error
                        _state.value = _state.value.copy(isSearchLoading = false)
                        Log.e("AdvancedSearchViewModel", "Error: $throwable")
                    }
                )
            } catch (e: Exception) {
                // handle exception
                _state.value = _state.value.copy(isSearchLoading = false)
                Log.e("AdvancedSearchViewModel", "Error: $e")
            } finally {
                _state.value = _state.value.copy(isSearchLoading = false)
            }
        }
    }


    /**
     * Get tags from API
     */
    fun getTags() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isTagsLoading = true)
            val token =
                if (appPreference.authToken == null) "" else appPreference.authToken!!

            try {
                val result = getTagsUC.getTags(token, 1, 500)

                result.fold(
                    onSuccess = { tags ->
                        _state.value = _state.value.copy(tags = tags.results)
//                        val tagStates = tags.associateWith {tags.results. TagState.NONE }
//                        _tagStates.value = tagStates
                        Log.d("AdvancedSearchViewModel", "tags: ${tags.results}")
                        _state.value = _state.value.copy(isTagsLoading = false)
                    },
                    onFailure = { throwable ->
                        // handle error
                        Log.e("AdvancedSearchViewModel", "Error: $throwable")
                    }
                )
            } catch (e: Exception) {
                // handle exception
                _state.value = _state.value.copy(isTagsLoading = false)
                Log.e("AdvancedSearchViewModel", "Error: $e")
            }
        }
    }

}