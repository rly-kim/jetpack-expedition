package com.example.jetpack_expedition.main.screen.recent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetpack_expedition.common.ui.DoubleCircleDivider
import com.example.jetpack_expedition.domain.entity.recent.Call
import com.example.jetpack_expedition.data.recent.RecentHistorySampleData
import com.example.jetpack_expedition.common.ui.noRippleClickable
import com.example.jetpack_expedition.main.screen.recent.composable.CallItemRow
import com.example.jetpack_expedition.main.screen.recent.composable.PhoneCallList
import com.example.jetpack_expedition.main.screen.recent.state.PullToRefreshInProgress
import com.example.jetpack_expedition.main.screen.recent.viewmodel.PullToRefreshUIViewModel

@Composable
fun RecentScreen(
    onBottomSheetCall: () -> Unit
) {
    val pullToRefreshUIViewModel = PullToRefreshUIViewModel()
    PhoneCallList(onBottomSheetCall, pullToRefreshUIViewModel, RecentHistorySampleData.calls)
}
