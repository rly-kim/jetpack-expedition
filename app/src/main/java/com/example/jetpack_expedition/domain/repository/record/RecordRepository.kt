package com.example.jetpack_expedition.domain.repository.record

import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.core.net.toUri
import com.example.jetpack_expedition.domain.entity.record.Record
import java.io.File

//interface RecordRepository {
//    fun getRecordFilesFromLocalStorage(filePath: String): List<Record>
//}
//
//class RecordRepositoryImpl: RecordRepository {
//    override fun getRecordFilesFromLocalStorage(filePath: String): List<Record> {
//        val files = File("$filePath/Voice Recorder").listFiles()
//        return files.map {
//            Record(title = it.name, it.toUri())
//        }
//    }
//}
