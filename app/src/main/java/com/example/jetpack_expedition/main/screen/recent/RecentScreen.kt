package com.example.jetpack_expedition.main.screen.recent

import androidx.compose.runtime.Composable
import com.example.jetpack_expedition.data.recent.RecentHistorySampleData
import com.example.jetpack_expedition.main.screen.recent.composable.PhoneCallList

@Composable
fun RecentScreen(
    onBottomSheetCall: () -> Unit
) {
    PhoneCallList(onBottomSheetCall = onBottomSheetCall, calls = RecentHistorySampleData.calls)
}
