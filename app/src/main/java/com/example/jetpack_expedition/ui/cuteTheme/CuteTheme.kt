package com.example.jetpack_expedition.ui.cuteTheme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable

@Composable
fun CuteTheme(
    colors: Colors = cuteColorPalette,
    typo: Typography = CuteType,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = colors,
        typography = typo,
        content = content
    )
}
