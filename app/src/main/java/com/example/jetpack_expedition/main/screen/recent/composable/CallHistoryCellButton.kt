package com.example.jetpack_expedition.main.screen.recent.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CallHistoryCellButton(onBottomSheetCall: () -> Unit, buttonType: CallHistoryCellButtonType) {
    OutlinedButton(
        onClick = {
            if(buttonType == CallHistoryCellButtonType.Edit) onBottomSheetCall()
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
        border = null,
    ) {
        Row {
            Icon(
                imageVector = buttonType.iconUrl,
                contentDescription = null
            )
            Text(
                buttonType.buttonName,
                color = MaterialTheme.colors.onSecondary,
                style = MaterialTheme.typography.button,
            )
        }
    }
}
