package com.example.jetpack_expedition.main.screen.dialer.view.keypadview

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun KeyButtonView(
    key: String,
    onClick: () -> Unit
) {
    TextButton(
        modifier = Modifier
            .padding(horizontal = 22.dp),
        onClick = onClick
    ) {
        Text(
            text = key,
            fontWeight = FontWeight.Light,
            fontSize = 44.sp,
        )
    }
}