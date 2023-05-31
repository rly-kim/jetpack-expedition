package com.example.jetpack_expedition.main.screen.dialer.viewmodel

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel

class DialerViewModel : ViewModel() {
    private val _number = mutableStateOf("")
    val number = _number

    fun keypadTap(key: String) {
        Log.d("DialerViewModel","onKeyTapped $key")
        _number.value += key
    }

    fun delete() {
        Log.d("DialerViewModel","onDeleteTapped")
        _number.value = _number.value.dropLast(1)
    }

    fun checkCallPermission(context: Context): Boolean {
        Log.d("DialerViewModel","isCallPermissionAllowed")
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun call(context: Context) {
        Log.d("DialerViewModel","call")
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:${_number.value}")
        context.startActivity(callIntent)
    }

}