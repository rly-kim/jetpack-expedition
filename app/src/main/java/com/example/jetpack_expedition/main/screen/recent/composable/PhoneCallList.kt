package com.example.jetpack_expedition.main.screen.recent.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpack_expedition.common.ui.DoubleCircleDivider
import com.example.jetpack_expedition.common.ui.noRippleClickable
import com.example.jetpack_expedition.domain.entity.recent.Call
import com.example.jetpack_expedition.main.screen.recent.state.PullToRefreshInProgress
import com.example.jetpack_expedition.main.screen.recent.viewmodel.PullToRefreshUIViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhoneCallList(
    onBottomSheetCall: () -> Unit,
    viewModel: PullToRefreshUIViewModel = hiltViewModel(),
    calls: List<Call>,
) {
    val pullToRefreshUIState by viewModel.pullToRefreshUIState.collectAsStateWithLifecycle()
    val state = rememberPullRefreshState(
        pullToRefreshUIState is PullToRefreshInProgress,
        { viewModel.refresh() })

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .pullRefresh(state)//state 적용
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .height(
                        if (pullToRefreshUIState is PullToRefreshInProgress) { //새로고침 중이면 높이 고정
                            140.dp
                        } else { //당기기 정도에 따라 0~140dp까지 크기가 늘어남
                            lerp(0.dp, 140.dp, state.progress.coerceIn(0f..1f))
                        }
                    ),
            ) {
                if (pullToRefreshUIState is PullToRefreshInProgress) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(70.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colors.onBackground,
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
                    .background(MaterialTheme.colors.surface)
                    .noRippleClickable {
                        isExpanded = !isExpanded
                    }
            ) {
                CallItemRow(
                    onBottomSheetCall = onBottomSheetCall,
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
