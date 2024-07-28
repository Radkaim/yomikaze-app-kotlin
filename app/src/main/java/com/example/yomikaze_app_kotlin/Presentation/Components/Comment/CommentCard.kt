package com.example.yomikaze_app_kotlin.Presentation.Components.Comment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.changeDateTimeFormat2
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.IconAndNumbers
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialog.DeleteConfirmDialogComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialog.EditCommentDialogComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.DropdownMenu.MenuOptions
import com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.ChapterComment.ChapterCommentViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.ComicComment.ComicCommentViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.RelyCommentDetail.ReplyCommentDetailViewModel
import com.example.yomikaze_app_kotlin.R

@Composable
fun CommentCard(
    comicId: Long,
    commentId: Long,
    content: String,
    authorName: String,
    authorImage: String,
    creationTime: String,
    roleName: String,

    isOwnComment: Boolean,
    isAdmin: Boolean,

    totalLikes: Long? = 0,
    onLikeClick: () -> Unit? = {},
    totalDislikes: Long? = 0,
    onDislikeClick: () -> Unit? = {},
    totalReplies: Long? = 0,

    isReacted: Boolean = false,
    myReaction: String? = null,


    onClicked: () -> Unit? = {},
    comicCommentViewModel: ComicCommentViewModel? = null,
    replyCommentDetailViewModel: ReplyCommentDetailViewModel? = null,
    chapterCommentViewModel: ChapterCommentViewModel? = null,

    ) {
    var showPopupMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf<Int?>(null) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small, clip = true)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.small
            ),
//            .clickable { onClicked() },
        color = MaterialTheme.colorScheme.onErrorContainer,
    ) {

        Box(
//            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = (-1).dp)
                .clickable {
                    onClicked()
                }
        ) {


            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-110).dp, y = (-12).dp)
            ) {
                val iconLike = if (isReacted && myReaction == "Like") {
                    R.drawable.ic_like_fill
                } else {
                    R.drawable.ic_like
                }
                IconAndNumbers(
                    icon = iconLike,
                    iconColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.36f),
                    number = totalLikes ?: 0,
                    numberColor = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                    numberWeight = FontWeight.Normal,
                    iconWidth = 15,
                    iconHeight = 15,
                    numberSize = 10,
                    onClick = { onLikeClick() }
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-65).dp, y = (-12).dp)
            ) {
                val iconDislike = if (isReacted && myReaction == "Dislike") {
                    R.drawable.ic_dislike_fill
                } else {
                    R.drawable.ic_dislike
                }
                IconAndNumbers(
                    icon = iconDislike,
                    iconColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.36f),
                    number = totalDislikes ?: 0,
                    numberColor = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                    numberWeight = FontWeight.Normal,
                    iconWidth = 15,
                    iconHeight = 15,
                    numberSize = 10,
                    onClick = { onDislikeClick() }
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-20).dp, y = (-12).dp)
            ) {
                IconAndNumbers(
                    icon = R.drawable.ic_comment,
                    iconColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.36f),
                    number = totalReplies ?: 0,
                    numberColor = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                    numberWeight = FontWeight.Normal,
                    iconWidth = 15,
                    iconHeight = 15,
                    numberSize = 10,
                    onClick = { onClicked() }
                )
            }
            0

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)

            ) {
                //image and role
                Column(
                    modifier = Modifier
                        .padding(5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //Author Image
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(authorImage)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        placeholder = painterResource(R.drawable.ic_profile),
                        error = painterResource(R.drawable.ic_profile),
                        contentDescription = "Comic Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(100.dp)
                            )
                    )

                    Text(
                        text = roleName,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }

                //name and content
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    //Author Name
                    Text(
                        text = authorName,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    //Content
                    Text(
                        text = content,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 15.sp,
                        modifier = Modifier
                            .width(265.dp)
                            .padding(top = 10.dp)
                    )

                    // created at and likes
                    Row(
                        modifier = Modifier.padding(top = 20.dp),
                    ) {

                        Text(
                            text = changeDateTimeFormat2(creationTime),
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.error.copy(alpha = 0.36f),
                        )

                    }
                }

            }


            //Options Icon
            val listTitlesOfComicMenuOptionAdmin = listOf(
                MenuOptions("Report", "report_comment_route", R.drawable.ic_report),
                MenuOptions("Delete", "delete_comment_route", R.drawable.ic_delete),
            )

            val listTitlesOfComicMenuOptionUserNotLogin = listOf(
                MenuOptions("Report", "report_comment_route", R.drawable.ic_report),
            )

            val listTitlesOfComicMenuOptionOwnUserLogin = listOf(
                MenuOptions("Edit", "edit_comment_route", R.drawable.ic_edit),
                MenuOptions("Report", "report_comment_route", R.drawable.ic_report),
                MenuOptions("Delete", "delete_comment_route", R.drawable.ic_delete),
            )
            val finalListTitlesOfComicMenuOption = if (isOwnComment) {
                listTitlesOfComicMenuOptionOwnUserLogin
            } else {
                if (isAdmin) {
                    listTitlesOfComicMenuOptionAdmin
                } else {
                    Log.d("CommentCard", "CommentCard: $isAdmin")
                    listTitlesOfComicMenuOptionUserNotLogin
                }
            }

            Box {
//            var iconOffset by remember { mutableStateOf(IntOffset.Zero) }
                Row {

                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
//                        .padding(10.dp)
                    ) {
                        val (iconButton, popUpMenu) = createRefs()
                        Box(
                            modifier = Modifier
//                                .padding(start = 350.dp, bottom = 80.dp)

                                .constrainAs(iconButton) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                }
                        ) {


                            IconButton(
                                onClick = { showPopupMenu = true },
                                modifier = Modifier
                                    .padding(start = 350.dp)
//                                    .offset(y = (-110).dp)
                                    .align(Alignment.TopEnd)
//                                    .constrainAs(iconButton) {
//                                        top.linkTo(parent.top)
//                                        start.linkTo(parent.start)
//                                    }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_more),
                                    contentDescription = "More option menu",
                                    tint = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f),
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
//                            .height(120.dp)
//                        .offset(y = (-100).dp)
                                .padding(start = 180.dp)
//                            .offset(y = (-100).dp)
                                .constrainAs(popUpMenu) {
                                    top.linkTo(iconButton.top)
                                    start.linkTo(parent.end)

                                }
                        ) {
                            DropdownMenu(
                                expanded = showPopupMenu,
                                onDismissRequest = { showPopupMenu = false },
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.onErrorContainer)
                            ) {
                                finalListTitlesOfComicMenuOption.forEachIndexed { index, menuOptions ->
                                    DropdownMenuItem(
                                        onClick = {
                                            showPopupMenu = false
                                            when (menuOptions.route) {
                                                "edit_comment_route" -> showDialog = 1
                                                "report_comment_route" -> showDialog = 2
                                                "delete_comment_route" -> showDialog = 3

                                            }
                                        }) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                                            modifier = Modifier
                                                .height(15.dp)
                                                .width(125.dp)
                                                .align(Alignment.CenterVertically)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = menuOptions.icon),
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onSecondary.copy(
                                                    alpha = 0.8f
                                                ),
                                                modifier = Modifier
                                                    .width(17.dp)
                                                    .height(17.dp),
                                            )
                                            Text(
                                                text = menuOptions.title,
                                                color = MaterialTheme.colorScheme.inverseSurface,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                    }
                                    if (index < finalListTitlesOfComicMenuOption.size - 1) {
                                        Divider(
                                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                            thickness = 1.dp
                                        )
                                    }
                                }
                            }

                            if (showDialog != null) {
                                if (comicCommentViewModel != null) {
                                    when (showDialog) {

                                        1 -> {
                                            EditCommentDialogComponent(
                                                key = comicId,
                                                key2 = commentId,
                                                value = content,
                                                title = "Edit Comment",
                                                onDismiss = { showDialog = 0 },
                                                viewModel = comicCommentViewModel
                                            )
                                        }

                                        3 -> {
                                            DeleteConfirmDialogComponent(
                                                key = comicId,
                                                key2 = commentId,
                                                value = "",
                                                title = "Are you sure you want to delete this comment?",
                                                onDismiss = { showDialog = 0 },
                                                viewModel = comicCommentViewModel
                                            )

                                        }
                                    }
                                } else {
                                    when (showDialog) {
                                        1 -> {
                                            EditCommentDialogComponent(
                                                key = comicId,
                                                key2 = commentId,
                                                value = content,
                                                title = "Edit Comment",
                                                onDismiss = { showDialog = 0 },
                                                viewModel = replyCommentDetailViewModel!!
                                            )
                                        }

                                        3 -> {
                                            DeleteConfirmDialogComponent(
                                                key = comicId,
                                                key2 = commentId,
                                                value = "",
                                                title = "Are you sure you want to delete this comment?",
                                                onDismiss = { showDialog = 0 },
                                                viewModel = replyCommentDetailViewModel!!
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

