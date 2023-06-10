package com.example.jetpack_expedition

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpack_expedition.main.screen.recent.viewmodel.PullToRefreshUIViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(
    pullToRefreshUIViewModel: PullToRefreshUIViewModel = hiltViewModel(),
    onFetchComplete: () -> Unit,
) {

    val fetched = remember { mutableStateOf(false) }
    val duration = 1000

    LaunchedEffect(Unit) {
        val execution = GlobalScope.async {
            Thread.sleep(1000)
            pullToRefreshUIViewModel.initialFetch()
        }
        execution.await()
        fetched.value = true
    }

    Crossfade(
        fetched.value,
        animationSpec = tween(duration),
    ) { targetState ->
        LaunchedEffect(targetState) {
            if (targetState) {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(duration.toLong() * 2)
                    onFetchComplete()
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painterResource(if (targetState) R.drawable.idle_coloured_phone else R.drawable.idle_phone),
                contentDescription = null,
                modifier = Modifier.background(MaterialTheme.colors.background)
            )
        }
    }
}
