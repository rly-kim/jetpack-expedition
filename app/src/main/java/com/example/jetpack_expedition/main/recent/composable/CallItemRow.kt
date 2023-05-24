package com.example.jetpack_expedition.main.recent.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jetpack_expedition.R
import com.example.jetpack_expedition.domain.entity.recent.Call
import com.example.jetpack_expedition.ui.theme.CallHistorySavedSingleTextStyle
import com.example.jetpack_expedition.ui.theme.CallHistoryUnsavedSingleTextStyle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CallItemRow(onBottomSheetCall: () -> Unit, call: Call, expanded: Boolean) {

    val callIcon = Icons.Default.Call
    val arrowDownIcon = Icons.Default.ArrowDropDown
    val arrowUpIcon = Icons.Default.KeyboardArrowUp

    Row(
        modifier = Modifier.padding(vertical = 15.dp, horizontal = 15.dp)
    ) {
        Icon(
            imageVector = callIcon,
            contentDescription = null
        )
        Column {
            Row(
                Modifier.padding()
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(PaddingValues(start = 20.dp)),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    if (call.name.isEmpty()) unsavedCallNumber(number = call.phoneNumber) else savedCallNumber(
                        name = call.name
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(2f),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    Text(call.callTime)
                }
                Icon(
                    imageVector = if(expanded) arrowUpIcon else arrowDownIcon,
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier
                    .padding(PaddingValues(start = 20.dp)),
                text = stringResource(R.string.incomingCall),
            )
            if(expanded) {
                Row(
                    modifier = Modifier
                        .padding(PaddingValues(start = 20.dp)),
                ) {
                    CallHistoryCellButtonType.values().forEach {
                        CallHistoryCellButton(onBottomSheetCall = onBottomSheetCall, buttonType = it)
                    }
                }
            }
        }
    }
}

@Composable
fun savedCallNumber(name: String) {
    Text(
        text = name,
        style = CallHistorySavedSingleTextStyle,

        )
}

@Composable
fun unsavedCallNumber(number: String) {
    Text(
        text = number,
        style = CallHistoryUnsavedSingleTextStyle,

        )
}
