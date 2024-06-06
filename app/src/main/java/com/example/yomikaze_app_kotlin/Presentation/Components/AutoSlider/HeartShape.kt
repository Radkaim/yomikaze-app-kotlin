package com.example.yomikaze_app_kotlin.Presentation.Components.AutoSlider

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class HeartShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(width / 2, height / 5)
            cubicTo(
                width * 5 / 14, 0f,
                0f, height / 15,
                width / 28, height * 2 / 5
            )
            cubicTo(
                width / 14, height * 9 / 10,
                width * 3 / 7, height * 11 / 10,
                width / 2, height
            )
            cubicTo(
                width * 4 / 7, height * 11 / 10,
                width * 13 / 14, height * 9 / 10,
                width * 27 / 28, height * 2 / 5
            )
            cubicTo(
                width, height / 15,
                width * 9 / 14, 0f,
                width / 2, height / 5
            )
            close()
        }

        return Outline.Generic(path)
    }
}
