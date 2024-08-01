package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav.HomeBottomNavBar
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NetworkDisconnectedDialog
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.ComicIWroteCardShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.TopAppBar.DefaultTopAppBar
import com.example.yomikaze_app_kotlin.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileView(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    profileViewModel.setNavController(navController)
    val state by profileViewModel.state.collectAsState()

    val context = LocalContext.current
    val appPreference = AppPreference(context)




    Scaffold(
        topBar = {
            DefaultTopAppBar(navigationIcon = {},
                actions = {},
                isProfile = true,
                onLogoClicked = {},
                onSearchClicked = {},
                onSettingClicked = { profileViewModel.onSettingCLicked() }
            )
        },
        bottomBar = {
            HomeBottomNavBar(
                navController = navController
            )
        })
    {

        if (CheckNetwork()) {
            // Show UI when connectivity is available
            ProfileContent(
                state = state,
                profileViewModel = profileViewModel,
                navController = navController,
                appPreference = appPreference
            )
        } else {
            ProfileContent(
                state = state,
                profileViewModel = profileViewModel,
                navController = navController,
                appPreference = appPreference
            )
            // Show UI for No Internet Connectivity
            NetworkDisconnectedDialog()
            // NoDataAvailable()
        }
    }
}

@Composable
fun ProfileContent(
    state: ProfileState,
    profileViewModel: ProfileViewModel,
    navController: NavController,
    appPreference: AppPreference
) {
//    profileViewModel.getUserInfo(CheckNetwork())

//    LaunchedEffect(Unit) {
//        profileViewModel.getProfile()
//    }
    if (appPreference.isUserLoggedIn &&
        (state.profileResponse?.roles?.contains("Publisher") == true
                || state.profileResponse?.roles?.contains("Super") == true
                || state.profileResponse?.roles?.contains("Administrator") == true
                )
    ) {
        LaunchedEffect(Unit) {
            profileViewModel.getComicByRolePublisher()
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .wrapContentHeight()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(140.dp)
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(100.dp)
                    )
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(APIConfig.imageAPIURL.toString() + state.profileResponse?.avatar)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_profile),
                    error = painterResource(R.drawable.ic_profile),
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .align(Alignment.Center)

                )
            }
        }

        item {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {

                Text(
                    text = state.profileResponse?.name ?: "Guest",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                )
                // Subtitle Text

                Text(
                    text = state.profileResponse?.roles?.firstOrNull() ?: "Guest",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }

        item {
            // Sign In Button
            if (!profileViewModel.checkUserIsLogin()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)

                ) {
                    Button(
                        onClick = { profileViewModel.onSignInButtonClicked() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier
                            .width(150.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = "Sign In",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

//            TODO: view balance
        if (appPreference.isUserLoggedIn) {
            val coin = appPreference.userBalance
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.background)

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp),

                        ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_coins),
                            contentDescription = "Coins Icon",
                            tint = MaterialTheme.colorScheme.scrim,
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp)
                        )
                        Text(
                            text = if (coin > 1) "$coin Coins" else "$coin Coin",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            }
        }

        //TODO: view comic I wrote here
        if (appPreference.isUserLoggedIn &&
            (state.profileResponse?.roles?.contains("Publisher") == true
                    || state.profileResponse?.roles?.contains("Super") == true
                    || state.profileResponse?.roles?.contains("Administrator") == true
                    )
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(850.dp)


                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.error.copy(0.5f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(MaterialTheme.colorScheme.onErrorContainer)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .align(Alignment.TopStart)
                        ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_comic_i_wrote),
                                    contentDescription = "Coins Icon",
                                    tint = MaterialTheme.colorScheme.surfaceTint,
                                    modifier = Modifier.size(30.dp)
                                )
                                Text(
//                                    text = "Comic I wrote" + " (${state.totalComic})",
                                    text = buildAnnotatedString {
                                        append("Comic I wrote ")
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                fontStyle = FontStyle.Italic
                                            )
                                        ) {
                                            append("(${state.totalComic})")
                                        }
                                    },
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W500,
                                    color = MaterialTheme.colorScheme.surfaceTint
                                )
                        }
                    }
                    val comics = state.comicResponse
                    //devide comic into 2 columns
                    if (comics != null) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxSize()
                                .align(Alignment.Center)
                                .offset(y = 50.dp)
                                .padding(bottom = 90.dp)
                                .background(MaterialTheme.colorScheme.onErrorContainer),
                            contentPadding = PaddingValues(10.dp),

                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(30.dp)

                        ) {
                            if (state.isGetComicByRolePublisherLoading) {
                                val item = 1..10

                                repeat(item.count()) {
                                    item {
                                        ComicIWroteCardShimmerLoading()
                                    }
                                }

                            } else {
                                comics.forEach { comic ->
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(10.dp))
                                                .clickable {
                                                    profileViewModel.navigateToComicDetail(
                                                        comic.comicId
                                                    )
                                                }
                                        ) {

                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        width = 2.dp,
                                                        color = MaterialTheme.colorScheme.primary.copy(
                                                            0.2f
                                                        ),
                                                        shape = RoundedCornerShape(10.dp)
                                                    )
                                                    .background(MaterialTheme.colorScheme.background)
                                                    .padding(10.dp)
                                            ) {
                                                AsyncImage(
                                                    model = ImageRequest.Builder(LocalContext.current)
                                                        .data(APIConfig.imageAPIURL.toString() + comic.cover)
                                                        .memoryCachePolicy(CachePolicy.ENABLED)
                                                        .build(),
                                                    placeholder = painterResource(R.drawable.placeholder),
                                                    error = painterResource(R.drawable.placeholder),
                                                    contentDescription = "Comic Thumbnail",
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .height(200.dp)
                                                        .fillMaxWidth()
                                                        .clip(RoundedCornerShape(10.dp))
                                                )
                                                Spacer(modifier = Modifier.height(10.dp))
                                                Text(
                                                    text = comic.name,
                                                    fontSize = 16.sp,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onPrimary.copy(0.8f)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

    }
}
