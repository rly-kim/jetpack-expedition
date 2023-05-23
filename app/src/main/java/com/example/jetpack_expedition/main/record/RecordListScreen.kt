package com.example.jetpack_expedition.main.record

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetpack_expedition.R
import com.example.jetpack_expedition.common.ui.DoubleCircleDivider
import com.example.jetpack_expedition.common.util.DateTimeUtil
import com.example.jetpack_expedition.main.record.state.PermissionState
import com.example.jetpack_expedition.main.record.state.RecordDataInitState
import com.example.jetpack_expedition.main.record.state.RecordInProgressState
import com.example.jetpack_expedition.main.record.state.RecordProcessState
import com.example.jetpack_expedition.main.record.state.RecordingInTemporalPlayState
import com.example.jetpack_expedition.main.record.viewmodel.RecordDataViewModel
import com.example.jetpack_expedition.main.record.viewmodel.RecordingViewModel

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordListScreen(
    recordDataViewModel: RecordDataViewModel,
) {
    val context = LocalContext.current
    val recordingViewModel = RecordingViewModel(recordDataViewModel.context)
    val recordingState = recordingViewModel.recordingState.collectAsStateWithLifecycle()
    val recordState = recordDataViewModel.recordDataState.collectAsStateWithLifecycle()
    var permissionState by rememberSaveable { mutableStateOf(PermissionState.Granted) }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionState = if (isGranted) {
            if (recordState.value is RecordDataInitState) {
                recordDataViewModel.getRecordFiles()
            }
            PermissionState.Granted
        } else {
            PermissionState.Denied
        }
    }

    OnLifecycleEvent { _, event ->
        Log.d("lifecycle", event.toString())
        when (event) {
            Lifecycle.Event.ON_START -> {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                if (recordState.value is RecordDataInitState) {
                    recordDataViewModel.getRecordFiles()
                }
            }

            else -> {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }
    if (permissionState == PermissionState.Denied)
        AlertDialog(
            onDismissRequest = {},
            text = {
                Text(stringResource(R.string.permissionGuideBodyText))
            },
            buttons = { AlertButtons(context) { permissionState = PermissionState.Ignored } },
            properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = false,
            )
        )
    else
        LazyColumn {
            itemsIndexed(recordState.value.recordList) { index, item ->
                Column {
                    Row {
                        Row(modifier = Modifier.weight(1f)) {
                            IconButton(onClick = {
                                recordingViewModel.toggle(index, item.path)
                            }) {
                                RecordItemIcon(index, recordingState.value)
                            }
                            Column {
                                Text(item.title)
                                Row {
                                    if (recordingState.value is RecordInProgressState) {
                                        val recordInProgressState =
                                            recordingState.value as RecordInProgressState
                                        if (recordingState.value.whoIsPlaying == index) {
                                            Text(
                                                "${
                                                    DateTimeUtil.convertFloatTimeToDurationFormat(
                                                        recordInProgressState.elapsedTime
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
                    if (recordingState.value is RecordInProgressState) {
                        val recordInProgressState = recordingState.value as RecordInProgressState
                        if (recordInProgressState.whoIsPlaying == index) {
                            RecordProgressIndicator(
                                recordInProgressState.elapsedTime,
                                DateTimeUtil.convertStringMicroSecondsDurationToMilliSeconds(item.duration)
                            )
                        }
                    }
                    if (index != recordState.value.recordList.size - 1) DoubleCircleDivider()
                }
            }
        }
}

@Composable
fun RecordProgressIndicator(indicatorProgress: Float, progressAnimDuration: Int) {
    var progress by remember { mutableStateOf(0f) }
    val progressAnimation by animateFloatAsState(
        targetValue = indicatorProgress,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing)
    )
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .clip(RoundedCornerShape(20.dp)), // Rounded edges
        progress = progressAnimation
    )
    LaunchedEffect(indicatorProgress) {
        Log.d("launched progress", indicatorProgress.toString())
        progress = indicatorProgress
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlertButtons(context: Context, permissionIgnore: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(onClick = {
            permissionIgnore()
        }) {
            Text(stringResource(R.string.permissionIgnoreText))
        }
        Button(onClick = {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", "com.example.jetpack_expedition", null)
            intent.data = uri

            // Add settings key to navigate to a specific setting page
            intent.putExtra(
                Settings.EXTRA_APP_PACKAGE,
                "com.example.jetpack_expedition"
            )
            intent.putExtra(
                Settings.EXTRA_CHANNEL_ID,
                "channelId"
            ) // Replace with your setting key
            context.startActivity(intent)
        }) {
            Text(stringResource(R.string.permissionSettingText))
        }
    }
}
