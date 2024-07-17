package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.AboutUs

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.R


@Composable
fun AboutUsView(navController: NavController) {
    var listAboutUs = listOf(
        AboutUsObject(title = "Facebook", titleEnd = "@Yomikaze", iconEnd = R.drawable.ic_next),
        AboutUsObject(
            title = "Email",
            titleEnd = "yomikaze@gmail.com",
            iconEnd = R.drawable.ic_next
        ),
        AboutUsObject(title = "Term of Service", titleEnd = "", iconEnd = R.drawable.ic_next),
        AboutUsObject(title = "Privacy Policy", titleEnd = "", iconEnd = R.drawable.ic_next)
    )
    AboutUsContent(navController = navController, listAboutUs = listAboutUs)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsContent(
    navController: NavController,
    listAboutUs: List<AboutUsObject>
) {
    var url by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = "About Us",
                navigationIcon = {
                    androidx.compose.material.IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Icon"
                        )
                    }
                }
            )
        }
    )
    {
        if (url == null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    // Image
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(top = 70.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .height(150.dp)
                                .width(150.dp),
                            contentScale = ContentScale.Fit
                        )

                        Text(
                            text = "Yomikaze",
                            fontSize = 30.sp,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.outline,
                                        MaterialTheme.colorScheme.outlineVariant
                                    ),
                                )
                            ),
                            modifier = Modifier.padding(top = 180.dp)
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }

                item {
                    listAboutUs.forEach { aboutUsObject ->
                        AboutUsItem(
                            title = aboutUsObject.title.orEmpty(),
                            titleEnd = aboutUsObject.titleEnd.orEmpty(),
                            iconEnd = aboutUsObject.iconEnd ?: 0,
                            onClicked = {
                                url = when (aboutUsObject.title) {
                                    "Facebook" -> "https://www.facebook.com/people/Yomikaze-Comic/61559960255105/"
                                    "Term of Service" -> "https://yomikaze.org/terms-of-service"
                                    "Privacy Policy" -> "https://yomikaze.org/privacy-policy"
                                    else -> null
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item { WebViewScreen(url = url!!) }
            }
        }
    }
}

@Composable
fun WebViewScreen(
    url: String
) {
    val context = LocalContext.current
    Log.d("WebViewScreen", "url: $url")
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()


                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
                settings.builtInZoomControls = true
                settings.displayZoomControls = false
            }
        },
        update = { webView ->
            if (url.isNotEmpty() && url.startsWith("https://yomikaze.org/")) {
                webView.loadUrl(url)
            }else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
        }
    )


}