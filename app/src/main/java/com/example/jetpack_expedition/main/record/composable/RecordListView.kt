package com.example.jetpack_expedition.main.record.composable

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetpack_expedition.common.ui.DoubleCircleDivider
import com.example.jetpack_expedition.common.util.DateTimeUtil
import com.example.jetpack_expedition.main.record.state.RecordDataState
import com.example.jetpack_expedition.main.record.state.RecordInProgressState
import com.example.jetpack_expedition.main.record.state.RecordProcessState
import com.example.jetpack_expedition.main.record.state.RecordingInTemporalPlayState
import com.example.jetpack_expedition.main.record.viewmodel.RecordingViewModel


@Composable
fun RecordListView(
    recordState: RecordDataState,
    recordingViewModel: RecordingViewModel,
    onRecordToggled: (index: Int, recordPath: Uri) -> Unit
) {

    val recordingState = recordingViewModel.recordingState.collectAsStateWithLifecycle().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        itemsIndexed(recordState.recordList) { index, item ->
            Column {
                Row {
                    Row(modifier = Modifier.weight(1f)) {
                        IconButton(onClick = {
                            onRecordToggled(index, item.path)
                        }) {
                            RecordItemIcon(index, recordingState)
                        }
                        Column {
                            Text(item.title)
                            Row {
                                if (recordingState is RecordInProgressState) {
                                    if (recordingState.whoIsPlaying == index) {
                                        Text(
                                            "${
                                                DateTimeUtil.convertFloatTimeToDurationFormat(
                                                    recordingState.elapsedTime
                                                )
                                            } / "
                                        )
                                    }
                                }
                                Text(DateTimeUtil.changeDurationFormat(item.duration))
                            }
                        }
                    }
                    Text(item.dateTime)
                }
                if (recordingState is RecordInProgressState) {
                    if (recordingState.whoIsPlaying == index) {
                        RecordProgressIndicator(
                            recordingState.elapsedTime,
                            DateTimeUtil.convertStringMicroSecondsDurationToMilliSeconds(item.duration)
                        )
                    }
                }
                if (index != recordState.recordList.size - 1) DoubleCircleDivider()
            }
        }
    }
}

@Composable
fun RecordItemIcon(index: Int, recordingState: RecordProcessState) {
    // 일시정지 시에 아이콘은 다른 파일들과 동일하게 화살표 아이콘 노출, 다른 점은 재생 바 expand로 노출
    if (recordingState is RecordingInTemporalPlayState) {
        if (recordingState.whoIsPlaying == index) {
            Icon(Icons.Outlined.ThumbUp, contentDescription = null)
        } else {
            Icon(Icons.Filled.PlayArrow, contentDescription = null)
        }
    } else {
        Icon(Icons.Filled.PlayArrow, contentDescription = null)
    }
}
