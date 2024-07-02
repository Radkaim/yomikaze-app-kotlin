package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
        libraryViewModel = libraryViewModel
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp)
    ) {
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

}