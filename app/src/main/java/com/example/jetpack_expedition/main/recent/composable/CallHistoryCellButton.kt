package com.example.jetpack_expedition.main.recent.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CallHistoryCellButton(onBottomSheetCall: () -> Unit, buttonType: CallHistoryCellButtonType) {
    OutlinedButton(
        onClick = {
            onBottomSheetCall()
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
        border = null,
    ) {
        Row {
            Icon(
                imageVector = buttonType.iconUrl,
                contentDescription = null
            )
            Text(buttonType.buttonName)
        }
    }
}

enum class CallHistoryCellButtonType(val iconUrl: ImageVector, val buttonName: String) {
    Call(Icons.Default.Call, "전화"),
    Text(Icons.Default.MailOutline, "문자"),
    Edit(Icons.Default.Edit, "수정")
    ;
}
