package com.example.jetpack_expedition.main.record.state

import com.example.jetpack_expedition.main.record.data.Record

abstract class RecordDataState {
    abstract val recordList: List<Record>
}

class RecordDataInitState(override val recordList: List<Record>) : RecordDataState() {

}

class RecordDataInitFetchingState: RecordDataState() {
    override val recordList: List<Record>
        get() = listOf()

}

class RecordDataFetchedState(override val recordList: List<Record>): RecordDataState() {
}

class RecordDataFetchFailedState(override val recordList: List<Record>): RecordDataState() {
}

enum class PermissionState {
    Granted,
    Denied,
    Ignored,
}
