package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.DropdownMenu.ChooseOptionState
import com.example.yomikaze_app_kotlin.Presentation.Components.DropdownMenu.DropdownMenuCustom
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
    var chooseOptionState by remember { mutableStateOf(ChooseOptionState("")) } // Khởi tạo chooseOptionState

    // Callback để cập nhật chooseOptionState
    val onOptionSelected: (chooseOptionState: ChooseOptionState) -> Unit = { newChooseOptionState ->
        chooseOptionState = newChooseOptionState
    }

    val listTitlesOfComicMenuOption = listOf(
        MenuOptions("Add to Library", "add_to_library_dialog_route", R.drawable.ic_library),
        MenuOptions("Download", "download_dialog_route", R.drawable.ic_download),
//        MenuOptions("Rating", "rating", R.drawable.ic_star_fill),
//        MenuOptions("Report", "report", R.drawable.ic_report),
//        MenuOptions("Share", "settings", R.drawable.ic_share),

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
                    // Creating Icon button for dropdown menu
                    var isDisplayMenu by remember { mutableStateOf(false) }

                    IconButton(onClick = { isDisplayMenu = !isDisplayMenu }) {
                        Icon(Icons.Default.MoreVert, "Icon for dropdown menu")
                    }
                    if (isDisplayMenu) {
                        DropdownMenuCustom(
                            listTitles = listTitlesOfComicMenuOption,
                            isExpanded = isDisplayMenu,
                            onDismiss = { isDisplayMenu = false },
                            navController = navController,
                            onOptionSelected = onOptionSelected // Truyền callback vào DropdownMenuCustom
                        )
                    }
                    Log.d("ComicDetailsView", "chooseOptionState: ${chooseOptionState.route}")

                    when (chooseOptionState.route) {
                        "add_to_library_dialog_route" -> CustomDialog(
                            title = "${chooseOptionState.route}",
                            message = ""
                        )

                        "download_dialog_route" -> CustomDialog(
                            title = "${chooseOptionState.route}",
                            message = ""
                        )

                        else -> {}
                    }
                }
            )
        })
    {
        Column {
            Text(text = "$comicId")
        }
    }
}

@Composable
fun CustomDialog(title: String, message: String) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = title) },
            text = { Text(text = message) },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text("OK")
                }
            }
        )
    }
}

