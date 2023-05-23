package com.example.jetpack_expedition.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class ScreenTab(val route: String, val icon: ImageVector, val title: String) {
    Recent("recent", Icons.Default.List, "최근기록"),
    Record("record", Icons.Default.Call, "녹음"),
    Settings("settings", Icons.Default.Settings, "설정"),
    ;
}
