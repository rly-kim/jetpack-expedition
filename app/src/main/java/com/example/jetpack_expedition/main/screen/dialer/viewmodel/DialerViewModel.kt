package com.example.jetpack_expedition.main.screen.dialer.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class DialerViewModel : ViewModel() {
    private val _number = mutableStateOf("")
    val number = _number

    fun onKeyTapped(key: String) {
        Log.d("DialerViewModel","onKeyTapped $key")
        _number.value += key
    }

    fun onDeleteTapped() {
        Log.d("DialerViewModel","onDeleteTapped")
        _number.value = _number.value.dropLast(1)
    }
}