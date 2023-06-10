package com.example.jetpack_expedition.main.screen.recent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_expedition.main.screen.recent.state.PullToRefreshFetched
import com.example.jetpack_expedition.main.screen.recent.state.PullToRefreshIdle
import com.example.jetpack_expedition.main.screen.recent.state.PullToRefreshInProgress
import com.example.jetpack_expedition.main.screen.recent.state.PullToRefreshUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class PullToRefreshUIViewModel @Inject constructor(): ViewModel() {
    private val _pullToRefreshUIState = MutableStateFlow<PullToRefreshUIState>(PullToRefreshIdle)
    var pullToRefreshUIState: StateFlow<PullToRefreshUIState> = _pullToRefreshUIState.asStateFlow()
    fun initialFetch() {
            _pullToRefreshUIState.update {
                PullToRefreshFetched
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _pullToRefreshUIState.update {
                PullToRefreshInProgress
            }
            withContext(Dispatchers.Default) {
                Thread.sleep(2000) /// TODO repository.refreshData()
            }
            _pullToRefreshUIState.update {
                PullToRefreshIdle
            }
        }
    }
}
