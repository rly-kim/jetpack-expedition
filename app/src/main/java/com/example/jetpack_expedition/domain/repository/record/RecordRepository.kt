package com.example.jetpack_expedition.domain.repository.record

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.example.jetpack_expedition.data.record.RecordDatasource
import com.example.jetpack_expedition.domain.entity.record.Record
import javax.inject.Inject

interface RecordRepository {
    fun getRecordFilesFromLocalStorage(filePath: String): List<Record>
}
class RecordRepositoryImpl @Inject constructor(
    private val context: Context,
    private val recordDatasource: RecordDatasource,
) : RecordRepository {
    override fun getRecordFilesFromLocalStorage(filePath: String): List<Record> {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        Log.d("record filePath", "?? $filePath")
        recordDatasource.getRecordFilesFromLocalStorage(filePath)?.let {
            return it.map { file ->
                Log.d("record file result", "?? $file")
                mediaMetadataRetriever.setDataSource(context, Uri.fromFile(file))
                val duration =
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                        ?: ""
                val dateTime =
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE)
                        ?: ""
                Record(title = file.name, file.toUri(), duration, dateTime)
            }
        }
        return listOf()
    }
}
