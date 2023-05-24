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
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.io.FileDescriptor
import java.io.FileInputStream
import java.net.URI
import java.util.Timer
import java.util.TimerTask

class RecordingViewModel(
    private val context: Application
) : AndroidViewModel(context) {

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

    private fun play(path: Uri) {
        /// main thread에서 실행하지만 block하지 않음
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
                Log.d("currentPosition", currentPosition.toString())
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
            }
            else if (recordingState.value is RecordingInTemporalPlayState) {
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
            play(path)
        }
    }

}
