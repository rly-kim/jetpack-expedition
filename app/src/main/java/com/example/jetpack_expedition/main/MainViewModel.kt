package com.example.jetpack_expedition.main

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel: ViewModel() {
    private val _mainTabState = MutableStateFlow(ScreenTab.Recent)
    val mainTabState: StateFlow<ScreenTab> = _mainTabState

    fun tapped(tabIndex: Int) {
        _mainTabState.value = ScreenTab.values()[tabIndex]
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("mainViewmodel","cleared")
    }
}
