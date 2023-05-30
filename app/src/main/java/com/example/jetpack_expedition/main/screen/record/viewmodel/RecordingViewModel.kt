package com.example.jetpack_expedition.main.screen.record.viewmodel

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_expedition.main.screen.record.state.RecordInProgressState
import com.example.jetpack_expedition.main.screen.record.state.RecordProcessState
import com.example.jetpack_expedition.main.screen.record.state.RecordingInPauseState
import com.example.jetpack_expedition.main.screen.record.state.RecordingInTemporalPlayState
import com.example.jetpack_expedition.main.screen.record.state.RecordingInitState
import com.example.jetpack_expedition.main.screen.record.state.RecordingTerminatedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordingViewModel @Inject constructor() : ViewModel() {

    private var _recordingState = MutableStateFlow<RecordProcessState>(RecordingInitState)
    val recordingState = _recordingState.asStateFlow()

    private val mediaPlayer = MediaPlayer()
    private val handler = Handler(Looper.getMainLooper())

    private val period = 1000L

    private val positionFinderTask = object : Runnable {
        override fun run() {
            viewModelScope.launch {
                if (recordingState.value is RecordingInTemporalPlayState) {
                    if ((recordingState.value as RecordingInTemporalPlayState).elapsedTime.toInt() != mediaPlayer.currentPosition) {
                        _recordingState.update {
                            (recordingState.value as RecordingInTemporalPlayState).copyState(mediaPlayer.currentPosition.toFloat())
                        }
                    }
                }
            }
            handler.postDelayed(this, period)
        }
    }

    fun turnOffMedia() {
        handler.removeCallbacks(positionFinderTask)
        with(mediaPlayer) {
            pause()
        }
    }

    private fun terminate() {
        handler.removeCallbacks(positionFinderTask)
        with(mediaPlayer) {
            stop()
            reset()
        }
    }

    private fun play(context: Context, path: Uri) {
        handler.post(positionFinderTask)
        with(mediaPlayer) {
            setOnCompletionListener {
                Log.d("completion listener", "started!!")
                if(it.currentPosition / 1000 >= it.duration / 1000) {
                    Log.d("completion end", "invoked!!")
                    terminate()
                    _recordingState.update {
                        RecordingTerminatedState(recordingState.value.whoIsPlaying)
                    }
                } else {
                    Log.d("completion else listener", "...!! ${it.currentPosition / 1000} ${it.duration / 1000}")
                }
            }
            setDataSource(context, path)
            prepare()
            start()
        }
    }

    private fun pause() {
        handler.removeCallbacks(positionFinderTask)
        with(mediaPlayer) {
            pause()
        }
    }

    private fun reset() {
        handler.post(positionFinderTask)
        with(mediaPlayer) {
            reset()
        }
    }

    private fun resume(path: Uri) {
        handler.post(positionFinderTask)
        with(mediaPlayer) {
            start()
        }
    }

    fun toggle(
        context: Context,
        fileIndex: Int,
        path: Uri,
    ) {
        if (recordingState.value is RecordInProgressState && recordingState.value.whoIsPlaying == fileIndex) {
            if (recordingState.value is RecordingInPauseState) {
                _recordingState.update {
                    RecordingInTemporalPlayState(
                        fileIndex, (recordingState.value as RecordingInPauseState).elapsedTime
                    )
                }
                resume(path)
            } else if (recordingState.value is RecordingInTemporalPlayState) {
                pause()
                _recordingState.update {
                    RecordingInPauseState(
                        fileIndex,
                        (recordingState.value as RecordingInTemporalPlayState).elapsedTime
                    )
                }
            }
        } else {
            if (mediaPlayer.isPlaying || recordingState.value is RecordInProgressState) {
                reset()
            }
            _recordingState.update {
                RecordingInTemporalPlayState(fileIndex)
            }
            play(context, path)
        }
    }
}
