package com.example.jetpack_expedition.common.util

object DateTimeUtil {

    fun convertStringMicroSecondsDurationToMilliSeconds(duration: String): Int {
        return duration.toInt() / 1000
    }

    fun changeDurationFormat(duration: String): String {
        val longDuration = duration.toLong()
        val h: Long = (longDuration / 3600000)
        val m: Long = (longDuration - h * 3600000) / 60000
        val s: Long = (longDuration - (h * 3600000 + m * 60000)) / 1000
        return "$h:$m:$s"
    }

    fun convertFloatTimeToDurationFormat(time: Float): String {
        val intTime = time.toInt()
        val h: Int = (intTime / 3600000)
        val m: Int = (intTime - h * 3600000) / 60000
        val s: Int = (intTime - (h * 3600000 + m * 60000)) / 1000
        return "$h:$m:$s"
    }
}
