package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.BookcaseComicCard.BasicComicCard
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.SearchTopAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Home.SearchWidgetState

@Composable
fun LibraryView(
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by libraryViewModel.state.collectAsState()
    val searchWidgetState by libraryViewModel.searchWidgetState
    val searchTextState by libraryViewModel.searchTextState

    libraryViewModel.setNavController(navController)

    LibraryContent(
        searchWidgetState = searchWidgetState,
        searchTextState = searchTextState,
        onTextChange = { libraryViewModel.updateSearchText(newValue = it) },
        onSearchTriggered = { /*TODO*/ },
        navController = navController,
        state = state,
        libraryViewModel = libraryViewModel,
        onSearchClicked = {
            libraryViewModel.searchComic(comicNameQuery = searchTextState)
        }

    )

}

@Composable
fun LibraryContent(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit = {},
    onSearchClicked: () -> Unit = {},
    onSearchTriggered: () -> Unit,
    navController: NavController,
    state: LibraryState,
    libraryViewModel: LibraryViewModel
) {
    // Show normal app bar
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp)
    ) {
        item {
            SearchTopAppBar(
                searchText = searchTextState,
                onTextChange = onTextChange,
                onCLoseClicked = {
                    libraryViewModel.updateSearchText(newValue = "")
                    libraryViewModel.updateSearchWidgetState(newState = SearchWidgetState.CLOSE)
                    libraryViewModel.updateSearchResult(newSearchResult = emptyList()) // Cập nhật searchResult
                },
                onSearchClicked = { onSearchClicked() }
            )
        }

        items(state.searchResult.value) { comic ->
            LazyRow() {
                item {
                    if (comic != null){
                        SearchResultItem(
                            libraryViewModel = libraryViewModel,
                            comic = comic
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun SearchResultItem(
    libraryViewModel: LibraryViewModel,
    comic: LibraryEntry
) {
    BasicComicCard(
        comicName = comic.libraryEntry.name,
        image = APIConfig.imageAPIURL.toString() + comic.libraryEntry.cover,
        isLibrarySearchComicCard = true,
        authorName = comic.libraryEntry.authors,
        onClick = { libraryViewModel.onNavigateComicDetail(comic.libraryEntry.comicId) }
    )
}
