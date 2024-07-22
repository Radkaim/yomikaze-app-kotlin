package com.example.yomikaze_app_kotlin.Presentation.Components.Dialog

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable

@Composable
fun ShareDialog(text: String, context: Context) {

    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}
