package com.example.jetpack_expedition.main.record.state

import com.example.jetpack_expedition.domain.entity.record.Record

sealed class RecordDataState {
    abstract val recordList: List<Record>
}

class RecordDataInitState(override val recordList: List<Record>) : RecordDataState() {

}

object RecordDataInitFetchingState : RecordDataState() {
    override val recordList: List<Record>
        get() = listOf()
}

class RecordDataFetchedState(override val recordList: List<Record>): RecordDataState() {
}

class RecordDataFetchFailedState(override val recordList: List<Record>): RecordDataState() {
}
