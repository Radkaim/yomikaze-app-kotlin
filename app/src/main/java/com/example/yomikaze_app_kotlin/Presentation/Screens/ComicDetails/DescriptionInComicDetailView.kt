package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.TagComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Comment.CommentCard
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.ButtonReadingShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.DescriptionShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.LineLongShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.LineShortShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.NormalComicCardShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.TagShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.ComicComment.ComicCommentViewModel
import com.example.yomikaze_app_kotlin.R
import kotlinx.coroutines.withContext

/**
 * TODO: Description and List Chapter view
 */

@Composable
fun DescriptionInComicDetailView(
    state: ComicDetailState,
    comicDetailViewModel: ComicDetailViewModel,
    comicId: Long,
    comicName: String,
    comicCommentViewModel: ComicCommentViewModel = hiltViewModel()
) {
    var isExpanded by remember { mutableStateOf(false) }
    val description = state.comicResponse?.description

    val comicCommentState by comicCommentViewModel.state.collectAsState()

    val context1 = LocalContext.current
    val appPreference = AppPreference(context1)
    LaunchedEffect(
        key1 = comicCommentState.isDeleteCommentSuccess,
        key2 = comicCommentState.isUpdateCommentSuccess
    ) {
        comicDetailViewModel.getAllComicCommentByComicId(comicId = comicId)
    }

    LaunchedEffect(Unit) {
        withContext(coroutineContext) {
            if (state.listChapters.isNullOrEmpty()) {
                comicDetailViewModel.getListChapterByComicId(comicId = comicId)
            }
            Log.d(
                "DescriptionInComicDetailView",
                "ListChapterInComicDetailView: ${state.listChapters}"
            )
        }

    }

//for tag ls
    val listTag = state.listTagGenres ?: emptyList()
//    val ofsety = if (state.listComicComment!!.isNotEmpty()) 0.dp else -70.dp
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
//            .wrapContentHeight()
//            .offset(y = ofsety)
            .padding(top = 10.dp, start = 2.dp, end = 8.dp, bottom = 10.dp),
    ) {
        //TODO: Description
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .clickable { isExpanded = !isExpanded }
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (state.isLoading) {
                        DescriptionShimmerLoading()
                    } else {
                        val maxLine: Int = if (isExpanded) 25 else 3
                        Text(
                            text = description ?: "Description",
                            maxLines = maxLine,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        if (isExpanded) {
                            Text(
                                text = "Less",
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                                fontSize = 14.sp,
                            )
                        } else {
                            if (description != null) {
                                if (description.length > 70) {
                                    Text(
                                        text = "More",
                                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                                        fontSize = 14.sp,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(start = 2.dp, top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (state.isLoading) {
                    item {
                        repeat(3) {
                            TagShimmerLoading()
                        }
                    }
                } else {
                    items(listTag) { tag ->
                        TagComponent(status = tag.name)
                    }
                }
            }
        }

        item {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                if (state.isLoading) {
                    ButtonReadingShimmerLoading()
                } else {
                    Button(
                        modifier = Modifier
                            .width(250.dp)
                            .height(40.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(20.dp)),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colorScheme.onSecondary,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        onClick = {
                            //TODO
                            //    comicDetailViewModel.downloadComic(state.comicResponse!!)
                            if (state.comicResponse?.isRead == true) {
                                comicDetailViewModel.getContinueReading(
                                    comicId = comicId,
                                )
                            } else {
                                if (state.listChapters!!.isNotEmpty()) {
                                    comicDetailViewModel.onStartReading(
                                        comicId = comicId,
                                    )
                                } else {
                                    Toast.makeText(
                                        context1,
                                        "This comic has no chapter",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }) {
                        if (state.comicResponse?.isRead ?: false) {
                            Text(
                                text = "Continue Reading",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        } else {
                            Log.d(
                                "DescriptionInComicDetailView",
                                "DescriptionInComicDetailView: ${state.comicResponse?.isRead}"
                            )
                            Text(
                                text = "Start Reading",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )

                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }
        if (state.listComicComment!!.isNotEmpty()) {
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    if (state.isLoading) {
                        LineShortShimmerLoading()
                    } else {
                        Text(
                            text = "New Comments",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                    }
                    if (state.isLoading) {
                        LineShortShimmerLoading()
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                comicDetailViewModel.navigateToComicComment(
                                    comicId = comicId,
                                    comicName = comicName
                                )
                            }
                        ) {
                            Text(
                                text = "More",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Icon(
                                painterResource(id = R.drawable.ic_next),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(10.dp)
                                    .height(10.dp),
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            )
                        }
                    }
                }
            }
        } else {
            if (state.isLoading && !state.isListComicCommentLoading && state.listComicComment!!.isEmpty()) {
                item {
                    LineLongShimmerLoading()
                }
            } else {
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-20).dp)
                            .clickable {
                                comicDetailViewModel.navigateToComicComment(
                                    comicId = comicId,
                                    comicName = comicName
                                )
                            }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_comment),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp),
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Drop the first comment on this comic!",
                            fontSize = 15.sp,
                            fontStyle = FontStyle.Italic,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(30.dp)) }
        //   for commment
        if (!state.isListComicCommentLoading && state.listComicComment!!.isNotEmpty()) {
            if (state.isLoading) {
                item {
                    repeat(3) {
                        NormalComicCardShimmerLoading()
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            } else {
                item {
                    state.listComicComment.forEach { comment ->
                        CommentCard(
                            comicId = comicId,
                            commentId = comment.id,
                            content = comment.content,
                            authorName = comment.author?.name ?: "",
                            authorImage = (APIConfig.imageAPIURL.toString() + comment.author?.avatar)
                                ?: "",
                            roleName = comment.author?.roles?.get(0) ?: "",
                            creationTime = comment.creationTime,
                            isOwnComment = comicCommentViewModel.checkIsOwnComment(comment.author!!.id),
                            isUserLogin = appPreference.isUserLoggedIn,
                            isAdmin = comicCommentViewModel.checkIsAdmin(),
                            totalLikes = comment.totalLikes.toLong(),
                            totalDislikes = comment.totalDislikes.toLong(),
                            totalReplies = comment.totalReplies.toLong(),
                            myReaction = comment.myReaction,
                            isReacted = comment.isReacted,
                            onClicked = {
                                comicDetailViewModel.onNavigateToReplyCommentDetail(
                                    comicId = comicId,
                                    commentId = comment.id,
                                    authorName = comment.author?.name ?: "",
                                )
                            },
                            onLikeClick = {
                                if (appPreference.isUserLoggedIn) {
                                    comicDetailViewModel.reactComicCommentByComicId(
                                        commentId = comment.id,
                                        comicId = comicId,
                                        reactionType = "Like"
                                    )
                                } else {
                                    Toast.makeText(
                                        context1,
                                        "Please login to like",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            },
                            onDislikeClick = {
                                if (appPreference.isUserLoggedIn) {
                                    comicDetailViewModel.reactComicCommentByComicId(
                                        commentId = comment.id,
                                        comicId = comicId,
                                        reactionType = "Dislike"
                                    )
                                } else {
                                    Toast.makeText(
                                        context1,
                                        "Please login to dislike",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            listCommonCommentReportResponse = state.listCommonCommentReportResponse,
                            onReportClick = { commentId, reasonId, reportContent ->
                                comicDetailViewModel.reportComment(
                                    commentId = commentId,
                                    reportReasonId = reasonId,
                                    reportContent = reportContent
                                )
                            },
                            comicCommentViewModel = comicCommentViewModel
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
