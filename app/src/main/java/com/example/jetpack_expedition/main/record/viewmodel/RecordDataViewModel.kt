package com.example.jetpack_expedition.main.record.viewmodel

import android.app.Application
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_expedition.domain.entity.record.Record
import com.example.jetpack_expedition.main.record.state.RecordDataFetchedState
import com.example.jetpack_expedition.main.record.state.RecordDataInitFetchingState
import com.example.jetpack_expedition.main.record.state.RecordDataInitState
import com.example.jetpack_expedition.main.record.state.RecordDataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File


class RecordDataViewModel(
     val context: Application,
    private val filePath: String
): AndroidViewModel(context) {

    private var _recordDataState = MutableStateFlow<RecordDataState>(RecordDataInitState(listOf()))
    val recordDataState = _recordDataState.asStateFlow()

    fun getRecordFiles() {
        viewModelScope.launch {
            _recordDataState.update {
                RecordDataInitFetchingState
            }
        }
        val mediaMetadataRetriever = MediaMetadataRetriever()
        val files = File("$filePath/Voice Recorder").listFiles()
        val fileRecords = files
            .map {
                mediaMetadataRetriever.setDataSource(context, Uri.fromFile(it))
                val duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION) ?: ""
                val dateTime = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE) ?: ""
                Record(title = it.name, it.toUri(), duration, dateTime)
            }
        viewModelScope.launch {
            _recordDataState.update {
                RecordDataFetchedState(recordList = fileRecords)
            }
        }
    }
}
