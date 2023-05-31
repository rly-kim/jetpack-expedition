package com.example.jetpack_expedition.main.screen.dialer.view

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.sp

@Composable
fun DialerTextFieldView(
    number: MutableState<String>
) {
    Log.d("CompositionTest", "DialerTextFieldView")
    Text(
        text = number.value,
        fontSize = 40.sp
    )
}