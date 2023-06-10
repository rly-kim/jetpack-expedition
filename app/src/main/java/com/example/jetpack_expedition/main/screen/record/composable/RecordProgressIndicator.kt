package com.example.jetpack_expedition.main.screen.record.composable

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun RecordProgressIndicator(indicatorProgress: Float, progressAnimDuration: Int) {
    var progress by remember { mutableStateOf(0f) }
//    val progressAnimation by animateFloatAsState(
//        targetValue = (indicatorProgress / progressAnimDuration),
//        animationSpec = tween(durationMillis = progressAnimDuration, easing = LinearEasing)
//    )
    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(),
        progress = progress
    )
    LaunchedEffect(indicatorProgress) {
        Log.d("launched progress", (indicatorProgress / progressAnimDuration).toString() + " " + progressAnimDuration.toString() + " " + indicatorProgress.toString())
        progress = (indicatorProgress / progressAnimDuration)
    }
}
