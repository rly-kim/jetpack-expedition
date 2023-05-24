package com.example.jetpack_expedition.main.recent.viewmodel

import androidx.lifecycle.ViewModel
import com.example.jetpack_expedition.main.recent.state.PullToRefreshIdle
import com.example.jetpack_expedition.main.recent.state.PullToRefreshInProgress
import com.example.jetpack_expedition.main.recent.state.PullToRefreshUIState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PullToRefreshUIViewModel : ViewModel() {
    private val _pullToRefreshUIState = MutableStateFlow<PullToRefreshUIState>(PullToRefreshIdle())
    var pullToRefreshUIState: StateFlow<PullToRefreshUIState> = _pullToRefreshUIState.asStateFlow()

    fun refresh() {
        GlobalScope.launch {
            _pullToRefreshUIState.value = PullToRefreshInProgress()
            delay(2000) /// TODO repository.refreshData()
            _pullToRefreshUIState.value = PullToRefreshIdle()
        }
    }
}
