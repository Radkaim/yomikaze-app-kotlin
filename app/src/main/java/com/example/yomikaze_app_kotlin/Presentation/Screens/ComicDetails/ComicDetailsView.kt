package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.DropdownMenu.MenuOptions
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomeAppBar
import com.example.yomikaze_app_kotlin.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ComicDetailsView(
    comicId: Int,
    navController: NavController
) {
    /**
     * for menu option
     */

    var showPopupMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf<Int?>(null) }

    val listTitlesOfComicMenuOption = listOf(
        MenuOptions("Add to Library", "add_to_library_dialog_route", R.drawable.ic_library),
        MenuOptions("Download", "download_dialog_route", R.drawable.ic_download),
        MenuOptions("Rating", "rating_dialog_route", R.drawable.ic_star_fill),
        MenuOptions("Report", "report_dialog_route", R.drawable.ic_report),
        MenuOptions("Share", "share_dialog_route", R.drawable.ic_share),

        )
    Scaffold(
        topBar = {
            CustomeAppBar(
                title = "",
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Icon",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showPopupMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }

                    DropdownMenu(
                        expanded = showPopupMenu,
                        onDismissRequest = { showPopupMenu = false },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onSurface)

                    ) {
                        listTitlesOfComicMenuOption.forEachIndexed { index, menuOptions ->
                            DropdownMenuItem(
                                onClick = {
                                    showPopupMenu = false
                                    when (menuOptions.route) {
                                        "add_to_library_dialog_route" -> showDialog = 1
                                        "download_dialog_route" -> showDialog = 2
                                        "rating_dialog_route" -> showDialog = 3
                                        "report_dialog_route" -> showDialog = 4
                                        "share_dialog_route" -> showDialog = 5
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
                                        tint = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f),
                                        modifier = Modifier
                                            .width(17.dp)
                                            .height(17.dp),
                                    )
                                    Text(
                                        text = menuOptions.title,
                                        color = MaterialTheme.colorScheme.inverseSurface,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                            if (index < listTitlesOfComicMenuOption.size - 1) {
                                Divider(
                                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                    thickness = 1.dp
                                )
                            }
                        }
                    }
                }
            )

        },
        // drawerBackgroundColor = MaterialTheme.colorScheme.onBackground,
    )
    {
        Text(text = "Dialog ${comicId}")
        // Nội dung của ComicDetailScreen
//        if (showDialog != null) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                //  .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
//            )
//            {
//                Text(text = "Dialog ${comicId}")
//
//            }
//        }

        if (showDialog != null) {
            when (showDialog) {
                1 -> CustomDialog1(onDismiss = { showDialog = null })
                2 -> CustomDialog2(onDismiss = { showDialog = null })
                3 -> CustomDialog1(onDismiss = { showDialog = null })
                4 -> CustomDialog2(onDismiss = { showDialog = null })
                5 -> CustomDialog1(onDismiss = { showDialog = null })
            }
        }
    }
}

@Composable
fun CustomDialog1(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.7f)) // Màu xám với độ mờ
                .offset(y = (100).dp)
                .clickable { onDismiss() }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Dialog 1")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onDismiss) {
                        Text("OK")
                    }
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                }
            }
        }
    }


}

@Composable
fun CustomDialog2(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Dialog 2")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onDismiss) {
                    Text(Int.MAX_VALUE.toString())
                }
            }
        }
    }
}

