package com.example.jetpack_expedition.domain.entity.record
import android.net.Uri
import android.os.Build
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class Record(val title: String, val path: Uri, val duration: String, val dateTime: String) {

    private fun changeDateTimeFormat(dateTime: String): String {
        val output: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val pattern = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
            dateTime.format(pattern)
        } else {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm")
            formatter.format(parser.parse(dateTime))
        }
        return output
    }
}
