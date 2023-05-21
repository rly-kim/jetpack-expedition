package com.example.jetpack_expedition.main.record.viewmodel

import androidx.lifecycle.ViewModel
import com.example.jetpack_expedition.main.record.state.RecordProcessState
import com.example.jetpack_expedition.main.record.state.RecordingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecordingViewModel: ViewModel() {
    var _recordingState = MutableStateFlow<RecordProcessState>(RecordingState())
    val recordingState = _recordingState.asStateFlow()
}
