package com.example.jetpack_expedition.main.screen.record

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetpack_expedition.common.util.OnLifecycleEvent
import com.example.jetpack_expedition.main.screen.record.state.PermissionState
import com.example.jetpack_expedition.main.screen.record.state.RecordDataInitState
import com.example.jetpack_expedition.main.screen.record.composable.PermissionAlertDialog
import com.example.jetpack_expedition.main.screen.record.composable.RecordListView
import com.example.jetpack_expedition.main.screen.record.viewmodel.RecordDataViewModel
import com.example.jetpack_expedition.main.screen.record.viewmodel.RecordingViewModel

@Composable
fun RecordListScreen(
    recordDataViewModel: RecordDataViewModel,
) {
    val context = LocalContext.current

    val recordingViewModel = RecordingViewModel(recordDataViewModel.context)

    val recordState by recordDataViewModel.recordDataState.collectAsStateWithLifecycle()

    var permissionState by rememberSaveable { mutableStateOf(PermissionState.Granted) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionState = if (isGranted) {
            if (recordState is RecordDataInitState) {
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
            Lifecycle.Event.ON_STOP -> {
                recordingViewModel.turnOffMedia()
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
                if (recordState is RecordDataInitState) {
                    recordDataViewModel.getRecordFiles()
                }
            }

            else -> {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    if (permissionState == PermissionState.Denied)
        PermissionAlertDialog(context = context) {
            permissionState = PermissionState.Ignored
        }
    else
        RecordListView(
            recordState = recordState,
            recordingViewModel = recordingViewModel,
            onRecordToggled = { index, recordPath -> recordingViewModel.toggle(index, recordPath) },
        )
}
