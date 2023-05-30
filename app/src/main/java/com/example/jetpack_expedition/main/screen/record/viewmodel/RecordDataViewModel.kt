package com.example.jetpack_expedition.main.screen.record.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_expedition.di.FilePath
import com.example.jetpack_expedition.domain.repository.record.RecordRepository
import com.example.jetpack_expedition.main.screen.record.state.RecordDataFetchFailedState
import com.example.jetpack_expedition.main.screen.record.state.RecordDataFetchedState
import com.example.jetpack_expedition.main.screen.record.state.RecordDataInitFetchingState
import com.example.jetpack_expedition.main.screen.record.state.RecordDataInitState
import com.example.jetpack_expedition.main.screen.record.state.RecordDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecordDataViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    @FilePath val filePath: String,
) : ViewModel() {

    private var _recordDataState = MutableStateFlow<RecordDataState>(RecordDataInitState(listOf()))
    val recordDataState = _recordDataState.asStateFlow()

    fun getRecordFiles() {
        viewModelScope.launch {
            _recordDataState.update {
                RecordDataInitFetchingState
            }
            withContext(Dispatchers.IO) {
                Log.d("record io thread", "start")
                Thread.sleep(1000)
                Log.d("record io thread", "finish")
                recordRepository.getRecordFilesFromLocalStorage(filePath)
            }.let {
                Log.d("record fetched", "?? $it")
                val recordList = it
                if(recordList.isNotEmpty()) {
                    _recordDataState.update {
                        RecordDataFetchedState(recordList = recordList)
                    }
                } else {
                    _recordDataState.update {
                        RecordDataFetchFailedState(recordList = recordList)
                    }
                }
            }
        }
    }
}
