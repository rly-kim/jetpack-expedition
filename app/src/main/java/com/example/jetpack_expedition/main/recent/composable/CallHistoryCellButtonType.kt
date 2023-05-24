package com.example.jetpack_expedition.main.recent.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.ui.graphics.vector.ImageVector

enum class CallHistoryCellButtonType(val iconUrl: ImageVector, val buttonName: String) {
    Call(Icons.Default.Call, "전화"),
    Text(Icons.Default.MailOutline, "문자"),
    Edit(Icons.Default.Edit, "수정")
    ;
}
