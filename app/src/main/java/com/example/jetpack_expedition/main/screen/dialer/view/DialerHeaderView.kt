package com.example.jetpack_expedition.main.screen.dialer.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DialerHeader() {
    Log.d("CompositionTest", "DialerHeader")

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        // 연락처 추가
        IconButton(
            onClick = {}
        ) {
            Icon(
                modifier = Modifier.size(37.dp),
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
            )
        }
        // 연락처 검색
        IconButton(
            onClick = {}
        ) {
            Icon(
                modifier = Modifier.size(37.dp),
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
            )
        }
        // 더보기
//        IconButton(
//            onClick = {}
//        ) {
//            Icon(
//                modifier = Modifier.size(32.dp),
//                imageVector = Icons.Default.MoreVert,
//                contentDescription = "MoreVert",
//            )
//        }
    }
}