package com.example.jetpack_expedition.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DoubleCircleDivider() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 15.dp)
    ) {
        Box(modifier = Modifier
            .size(4.dp)
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f), shape = CircleShape)
        )
        Divider()
        Box(modifier = Modifier
            .size(4.dp)
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f), shape = CircleShape)
        )
    }
}
