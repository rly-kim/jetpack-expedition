package com.example.jetpack_expedition.main

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {
    private val _mainTabState = MutableStateFlow(ScreenTab.Recent)
    val mainTabState: StateFlow<ScreenTab> = _mainTabState

    fun tapped(tabIndex: Int) {
        _mainTabState.value = ScreenTab.values()[tabIndex]
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("mainViewmodel","cleared")
    }
}

enum class ScreenTab(val route: String, val icon: ImageVector, val title: String) {
    Recent("recent", Icons.Default.List, "최근기록"),
    Record("record", Icons.Default.Call, "녹음"),
    Settings("settings", Icons.Default.Settings, "설정"),
    ;
}
