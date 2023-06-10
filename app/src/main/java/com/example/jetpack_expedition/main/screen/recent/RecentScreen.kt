package com.example.jetpack_expedition.main.screen.recent

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.jetpack_expedition.data.recent.RecentHistorySampleData
import com.example.jetpack_expedition.main.screen.recent.composable.PhoneCallList
import com.example.jetpack_expedition.main.screen.recent.viewmodel.PullToRefreshUIViewModel

@Composable
fun RecentScreen(
    onBottomSheetCall: () -> Unit,
    pullToRefreshUIViewModel: PullToRefreshUIViewModel //= hiltViewModel()
) {
    PhoneCallList(onBottomSheetCall = onBottomSheetCall, calls = RecentHistorySampleData.calls, viewModel = pullToRefreshUIViewModel)
}
