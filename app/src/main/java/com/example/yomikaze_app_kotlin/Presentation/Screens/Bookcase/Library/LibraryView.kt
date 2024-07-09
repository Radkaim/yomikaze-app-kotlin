package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.BookcaseComicCard.BasicComicCard
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.BookcaseComicCard.CategoryCard
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialog.CreateCategoryDialog
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.UnNetworkScreen
import com.example.yomikaze_app_kotlin.Presentation.Components.NotSignIn.NotSignIn
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.BasicComicCardShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.NormalComicCardShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.SearchTopAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Home.SearchWidgetState
import com.example.yomikaze_app_kotlin.R

@Composable
fun LibraryView(
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by libraryViewModel.state.collectAsState()
    val searchWidgetState by libraryViewModel.searchWidgetState
    val searchTextState by libraryViewModel.searchTextState

    libraryViewModel.setNavController(navController)

    if (CheckNetwork()) {
        if (libraryViewModel.checkUserIsLogin()) {
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
        } else {
            NotSignIn(navController = navController)
        }

    } else {
        UnNetworkScreen()
    }


}

@OptIn(ExperimentalFoundationApi::class)
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

    //get all category
    LaunchedEffect(Unit) { // should be called only once
        libraryViewModel.getAllCategory()
    }
    LaunchedEffect(
        key1 = state.isCreateCategorySuccess,
        key2 = state.isUpdateCategoryNameSuccess,
        key3 = state.isDeleteCategorySuccess
    ) {
        libraryViewModel.getAllCategory()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 15.dp)
            .padding(bottom = 60.dp) // for show all content
    ) {

        //TODO Search widget
        stickyHeader {
            SearchTopAppBar(
                searchText = searchTextState,
                onTextChange = onTextChange,
                onCLoseClicked = {
                    libraryViewModel.updateSearchText(newValue = "")
                    libraryViewModel.updateSearchWidgetState(newState = SearchWidgetState.CLOSE)
                    libraryViewModel.updateSearchResult(newSearchResult = emptyList()) // Cập nhật searchResult
                    libraryViewModel.updateTotalResults(0)
                    libraryViewModel.updateIsSearchResult(false)

                },
                onSearchClicked = { onSearchClicked() }
            )
        }

        //TODO Search result
        item {
            if (state.totalSearchResults != 0 || state.isSearchResult) {
                Text(
                    text = "Results: " + state.totalSearchResults.toString(),
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
            }
            LazyRow(
                modifier = Modifier.padding(start = 10.dp, top = 20.dp, end = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                items(state.searchResult.value) { comic ->
                    if (state.isSearchLoading) {
                        repeat(4) {
                            BasicComicCardShimmerLoading()
                            Spacer(modifier = Modifier.width(20.dp))
                        }
                    }
                    SearchResultItem(
                        libraryViewModel = libraryViewModel,
                        comic = comic
                    )
                }
            }
        }

        //TODO total categories
        item {
            var showPopupMenu by remember { mutableStateOf(false) }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Personal Categories: " + state.totalCategoryResults.toString(),
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )

                //TODO Create Category

                IconButton(
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_create_to_library),
                            contentDescription = "Create Category",
                            tint = MaterialTheme.colorScheme.inverseSurface,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                                .offset(y = (5).dp)
                        )
                    },
                    onClick = {
                        showPopupMenu = true
                    })
            }
            when {
                showPopupMenu -> CreateCategoryDialog(
                    libraryViewModel = libraryViewModel,
                    onDismiss = { showPopupMenu = false }
                )
            }
        }

        item {
            if (state.isCategoryLoading) {
                repeat(4) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        NormalComicCardShimmerLoading()
                    }
                }
            }
        }
        //TODO Category list
        items(state.categoryList) { category ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, top = 10.dp, end = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                CategoryCard(
                    categoryId = category.id,
                    value = category.name,
                    name = category.name,
                    createAt = category.creationTime,
                    totalComics = category.totalComics,
                    image = APIConfig.imageAPIURL.toString() + category.firstCoverImage,
                    onClick = {
                        libraryViewModel.onNavigateCategoryDetail(
                            category.id,
                            category.name
                        )
                    },
                    libraryViewModel = libraryViewModel
                )
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
