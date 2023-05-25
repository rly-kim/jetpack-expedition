package com.example.jetpack_expedition.main.screen.record.viewmodel

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_expedition.main.screen.record.state.RecordInProgressState
import com.example.jetpack_expedition.main.screen.record.state.RecordProcessState
import com.example.jetpack_expedition.main.screen.record.state.RecordingInPauseState
import com.example.jetpack_expedition.main.screen.record.state.RecordingInTemporalPlayState
import com.example.jetpack_expedition.main.screen.record.state.RecordingInitState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@HiltViewModel
class RecordingViewModel @Inject constructor() : ViewModel() {

    private var _recordingState = MutableStateFlow<RecordProcessState>(RecordingInitState)
    val recordingState = _recordingState.asStateFlow()

    private val mediaPlayer = MediaPlayer()

    private var timer: Timer? = null
    private var task: TimerTask? = null

    private val delay = 0L
    private val period = 1000L

    private fun makeTimerTask(): TimerTask {
        return object : TimerTask() {
            override fun run() {
                streamCurrentPosition()
            }
        }
    }

    fun turnOffMedia() {
        pause()
    }

    private fun play(context: Context, path: Uri) {
        viewModelScope.launch {
            timer = Timer()
            task = makeTimerTask()
            timer!!.scheduleAtFixedRate(task, delay, period)
            mediaPlayer.apply {
                setDataSource(context, path)
                prepare()
                start()
            }
        }
    }

    private fun pause() {
        viewModelScope.launch {
            timer?.cancel()
            timer = null
            mediaPlayer.apply {
                pause()
            }
        }
    }

    private fun reset() {
        viewModelScope.launch {
            timer?.cancel()
            timer = null
            timer = Timer()
            task = makeTimerTask()
            timer!!.scheduleAtFixedRate(task, delay, period)
            mediaPlayer.apply {
                reset()
            }
        }
    }

    private fun resume(path: Uri) {
        viewModelScope.launch {
            timer?.cancel()
            timer = null
            timer = Timer()
            task = makeTimerTask()
            timer!!.scheduleAtFixedRate(task, delay, period)
            mediaPlayer.apply {
                start()
            }
        }
    }

    private fun streamCurrentPosition() {
        if (recordingState.value is RecordingInTemporalPlayState) {
            if ((recordingState.value as RecordingInTemporalPlayState).elapsedTime.toInt() != mediaPlayer.currentPosition)
                viewModelScope.launch {
                    _recordingState.update {
                        (recordingState.value as RecordingInTemporalPlayState).copyState(mediaPlayer.currentPosition.toFloat())
                    }
                }
        }
    }

    fun toggle(
        context: Context,
        fileIndex: Int,
        path: Uri,
    ) {
        if (recordingState.value is RecordInProgressState && recordingState.value.whoIsPlaying == fileIndex) {
            if (recordingState.value is RecordingInPauseState) {
                viewModelScope.launch {
                    _recordingState.update {
                        RecordingInTemporalPlayState(
                            fileIndex, (recordingState.value as RecordingInPauseState).elapsedTime
                        )
                    }
                }
                resume(path)
            } else if (recordingState.value is RecordingInTemporalPlayState) {
                pause()
                viewModelScope.launch {
                    _recordingState.update {
                        RecordingInPauseState(
                            fileIndex,
                            (recordingState.value as RecordingInTemporalPlayState).elapsedTime
                        )
                    }
                }
            }
        } else {
            if (mediaPlayer.isPlaying || recordingState.value is RecordInProgressState) {
                reset()
            }
            viewModelScope.launch {
                _recordingState.update {
                    RecordingInTemporalPlayState(fileIndex)
                }
            }
            play(context, path)
        }
    }

}
