package com.example.yomikaze_app_kotlin.Presentation.Components.FlipPage

import androidx.compose.ui.graphics.Shape

internal sealed class PageFlapType(val shape: Shape) {
     object Top : PageFlapType(TopShape)
     object Bottom : PageFlapType(BottomShape)
     object Left : PageFlapType(LeftShape)
     object Right : PageFlapType(RightShape)
}