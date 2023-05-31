package com.example.jetpack_expedition

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen() {

    var fetched  = remember { mutableStateOf (false) }

    LaunchedEffect(Unit) {
        Log.d("fetched", "start")
        withContext(Dispatchers.Default) {
            Log.d("fetched", "2000 start")
            Thread.sleep(2000)
            Log.d("fetched", "2000 end")
        }
        fetched.value = true
        Log.d("fetched", "end")
    }

    Crossfade(
        fetched.value,
        animationSpec = tween(1000)
    ) { targetState ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painterResource(if (targetState) R.drawable.idle_coloured_phone else R.drawable.idle_phone),
                contentDescription = null,
                modifier = Modifier.background(Color.White)
            )
        }
    }
}