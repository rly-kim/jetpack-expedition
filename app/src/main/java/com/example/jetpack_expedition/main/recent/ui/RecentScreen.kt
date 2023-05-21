package com.example.jetpack_expedition.main.recent.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.jetpack_expedition.common.ui.DoubleCircleDivider
import com.example.jetpack_expedition.main.recent.domain.Call
import com.example.jetpack_expedition.main.recent.data.RecentHistorySampleData
import com.example.jetpack_expedition.common.ui.noRippleClickable
import com.example.jetpack_expedition.main.recent.ui.state.PullToRefreshInProgress
import com.example.jetpack_expedition.main.recent.ui.viewmodel.PullToRefreshUIViewModel

@Composable
fun RecentScreen(
    navController: NavController
) {
    val pullToRefreshUIViewModel = PullToRefreshUIViewModel()
    PhoneCallList(pullToRefreshUIViewModel, RecentHistorySampleData.calls)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhoneCallList(viewModel: PullToRefreshUIViewModel, calls: List<Call>) {
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val pullToRefreshUIState = viewModel.pullToRefreshUIState.collectAsStateWithLifecycle()
    val state = rememberPullRefreshState(
        pullToRefreshUIState.value is PullToRefreshInProgress,
        { viewModel.refresh() })

        LazyColumn(
            modifier = Modifier
                .background(Color.LightGray)
                .pullRefresh(state)//state 적용
        ) {
            item {
                Box(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .height(
                            if (pullToRefreshUIState.value is PullToRefreshInProgress) { //새로고침 중이면 높이 고정
                                140.dp
                            } else { //당기기 정도에 따라 0~140dp까지 크기가 늘어남
                                lerp(0.dp, 140.dp, state.progress.coerceIn(0f..1f))
                            }
                        ),
                ) {
                    if (pullToRefreshUIState.value is PullToRefreshInProgress) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(70.dp)
                                .align(Alignment.Center),
                            color = Color.Red,
                            strokeWidth = 3.dp,
                        )
                    }
                }
            }
            itemsIndexed(calls) { index, item ->

                var isExpanded by rememberSaveable { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .clip(getCellRoundShape(index, calls.size - 1))
                        .background(Color.White)
                        .noRippleClickable {
                            isExpanded = !isExpanded
                        }
                ) {
                    CallItemRow(
                        bottomSheetState = modalBottomSheetState,
                        call = item,
                        expanded = isExpanded
                    )
                    if (index != calls.size - 1)
                        DoubleCircleDivider()
                }
            }
        }
}

fun getCellRoundShape(index: Int, maxIndex: Int): RoundedCornerShape {
    return when (index) {
        0 -> RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
        maxIndex -> RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
        else -> RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)
    }
}
