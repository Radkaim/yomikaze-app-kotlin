package com.example.yomikaze_app_kotlin.Presentation.Components.Dialogs

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun NetworkDisconnectedDialog() {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {openDialog.value = false},
            title = { Text(text = "Mất kết nối mạng") },
            text = { Text(text = "Bạn đã mất kết nối mạng. Vui lòng kiểm tra lại.") },
            confirmButton = {
                Button(onClick = {
                    // Hành động khi bấm nút xác nhận, có thể là không làm gì hoặc đóng dialog
                    // close dialog
                    openDialog.value = false

                }) {
                    Text("OK")
                }
            }
        )
    }

}