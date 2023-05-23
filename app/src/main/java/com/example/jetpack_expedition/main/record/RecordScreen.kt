package com.example.jetpack_expedition.main.record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun RecordScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.weight(1f),
        ) {
            RecordTopButton("일반")
            RecordTopButton("인터뷰")
            RecordTopButton("텍스트 변환")
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Color.LightGray)
                .weight(2f)
        ) {
            Text("녹음amplitude")
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.Bottom,
        ) {
            IconButton(
                onClick = {
                          navController.navigate("recording")
                },
            ) {
                Icon(Icons.Default.AddCircle, contentDescription = null)
            }
        }
    }
}

@Composable
fun RecordTopButton(
    buttonText: String,
) {

    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black),
        shape = RoundedCornerShape(8.dp),
        onClick = { /*TODO*/ },
    ) {
        Text(
            text = buttonText,
            style = TextStyle(Color.Black)
        )
    }
}
