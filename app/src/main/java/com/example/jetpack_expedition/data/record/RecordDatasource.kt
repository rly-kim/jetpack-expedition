package com.example.jetpack_expedition.data.record

import java.io.File
import javax.inject.Inject

interface RecordDatasource {
    fun getRecordFilesFromLocalStorage(filePath: String): Array<out  File>?
}

class RecordDatasourceImpl @Inject constructor() : RecordDatasource {
    override fun getRecordFilesFromLocalStorage(filePath: String): Array<out File>? {
            return File("$filePath/Voice Recorder").listFiles()
    }
}
