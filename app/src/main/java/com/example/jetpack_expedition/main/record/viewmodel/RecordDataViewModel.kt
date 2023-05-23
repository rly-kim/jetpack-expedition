package com.example.jetpack_expedition.main.record.viewmodel

import android.app.Application
import android.content.pm.PackageManager
import android.media.MediaExtractor
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import com.example.jetpack_expedition.main.record.data.Record
import com.example.jetpack_expedition.main.record.state.RecordDataFetchedState
import com.example.jetpack_expedition.main.record.state.RecordDataInitFetchingState
import com.example.jetpack_expedition.main.record.state.RecordDataInitState
import com.example.jetpack_expedition.main.record.state.RecordDataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.io.FileInputStream


class RecordDataViewModel(
     val context: Application,
    private val filePath: String
): AndroidViewModel(context) {

    private var _recordDataState = MutableStateFlow<RecordDataState>(RecordDataInitState(listOf()))
    val recordDataState = _recordDataState.asStateFlow()

    fun getRecordFiles() {
        _recordDataState.value = RecordDataInitFetchingState()
        val mediaMetadataRetriever = MediaMetadataRetriever()
        val files = File("$filePath/Voice Recorder").listFiles()
        val fileRecords = files.map {
            mediaMetadataRetriever.setDataSource(context, Uri.fromFile(it))
            val duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION) ?: ""
            val dateTime = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE) ?: ""
            Record(title = it.name, it.toUri(), duration, dateTime)
        }
        _recordDataState.value = RecordDataFetchedState(recordList = fileRecords)
    }
}
