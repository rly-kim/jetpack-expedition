package com.example.jetpack_expedition.main.record.viewmodel

import android.app.Application
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_expedition.main.record.state.RecordInProgressState
import com.example.jetpack_expedition.main.record.state.RecordProcessState
import com.example.jetpack_expedition.main.record.state.RecordingInPauseState
import com.example.jetpack_expedition.main.record.state.RecordingInTemporalPlayState
import com.example.jetpack_expedition.main.record.state.RecordingInitState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.FileDescriptor
import java.io.FileInputStream
import java.net.URI
import java.util.Timer
import java.util.TimerTask

class RecordingViewModel(
    private val context: Application
) : AndroidViewModel(context) {
    private var _recordingState = MutableStateFlow<RecordProcessState>(RecordingInitState())
    val recordingState = _recordingState.asStateFlow()
    private val mediaPlayer = MediaPlayer()

    private val timer = Timer()

    private val task = object : TimerTask() {
        override fun run() {
            // Call your event or function here
            println("Event called every second")
            streamCurrentPosition()
        }
    }

    private val delay = 0L // No initial delay
    private val period = 1000L // 1 second interval

    private fun play(path: Uri) {
        viewModelScope.launch {
            mediaPlayer.apply {
                setDataSource(context, path)
                prepare()
                start()
                timer.scheduleAtFixedRate(task, delay, period)
            }
        }
    }

    private fun pause() {
        viewModelScope.launch {
            mediaPlayer.apply {
                Log.d("currentPosition", currentPosition.toString())
                pause()
                timer.cancel()
            }
        }
    }

    private fun reset() {
        viewModelScope.launch {
            mediaPlayer.apply {
                reset()
                timer.cancel()
            }
        }
    }

    private fun resume(path: Uri) {
        viewModelScope.launch {
            mediaPlayer.apply {
                start()
                timer.scheduleAtFixedRate(task, delay, period)
            }
        }
    }

    private fun streamCurrentPosition() {
        if (recordingState.value is RecordingInTemporalPlayState && (recordingState.value as RecordingInTemporalPlayState).elapsedTime.toInt() != mediaPlayer.currentPosition) {
            _recordingState.value =
                (_recordingState.value as RecordingInTemporalPlayState).copyState(mediaPlayer.currentPosition.toFloat())
        }
    }

    fun toggle(
        fileIndex: Int,
        path: Uri,
    ) {
        if (recordingState.value is RecordInProgressState && recordingState.value.whoIsPlaying == fileIndex) {
            if (recordingState.value is RecordingInPauseState) {
                _recordingState.value = RecordingInTemporalPlayState(
                    fileIndex,
                    (recordingState.value as RecordingInPauseState).elapsedTime
                )
                resume(path)
            }
            if (recordingState.value is RecordingInTemporalPlayState) {
                pause()
                _recordingState.value = RecordingInPauseState(
                    fileIndex,
                    (recordingState.value as RecordingInTemporalPlayState).elapsedTime
                )
            }
        } else {
            if (mediaPlayer.isPlaying || recordingState.value is RecordInProgressState) {
                reset()
            }
            _recordingState.value = RecordingInTemporalPlayState(fileIndex)
            play(path)
        }
    }

}
