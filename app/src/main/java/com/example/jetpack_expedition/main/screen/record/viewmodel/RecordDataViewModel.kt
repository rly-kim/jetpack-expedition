package com.example.jetpack_expedition.main.screen.record.viewmodel

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_expedition.di.FilePath
import com.example.jetpack_expedition.domain.entity.record.Record
import com.example.jetpack_expedition.main.screen.record.state.RecordDataFetchedState
import com.example.jetpack_expedition.main.screen.record.state.RecordDataInitFetchingState
import com.example.jetpack_expedition.main.screen.record.state.RecordDataInitState
import com.example.jetpack_expedition.main.screen.record.state.RecordDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RecordDataViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    @FilePath val filePath: String
): ViewModel() {

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
