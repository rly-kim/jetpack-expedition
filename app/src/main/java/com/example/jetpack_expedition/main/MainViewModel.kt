package com.example.jetpack_expedition.main

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {
    private val _mainTabState = MutableStateFlow(MainRouteItem.Recent)
    val mainTabState: StateFlow<MainRouteItem> = _mainTabState

    fun tapped(tabIndex: Int) {
        _mainTabState.value = MainRouteItem.values()[tabIndex]
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("mainViewmodel","cleared")
    }
}
