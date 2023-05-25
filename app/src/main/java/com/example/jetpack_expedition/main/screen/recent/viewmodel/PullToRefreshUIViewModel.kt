package com.example.jetpack_expedition.main.screen.recent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_expedition.main.screen.recent.state.PullToRefreshIdle
import com.example.jetpack_expedition.main.screen.recent.state.PullToRefreshInProgress
import com.example.jetpack_expedition.main.screen.recent.state.PullToRefreshUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PullToRefreshUIViewModel @Inject constructor(): ViewModel() {
    private val _pullToRefreshUIState = MutableStateFlow<PullToRefreshUIState>(PullToRefreshIdle)
    var pullToRefreshUIState: StateFlow<PullToRefreshUIState> = _pullToRefreshUIState.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            _pullToRefreshUIState.update {
                PullToRefreshInProgress
            }
            delay(2000) /// TODO repository.refreshData()
            _pullToRefreshUIState.update {
                PullToRefreshIdle
            }
        }
    }
}
