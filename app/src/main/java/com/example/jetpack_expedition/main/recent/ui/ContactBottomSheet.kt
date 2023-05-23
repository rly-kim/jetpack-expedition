package com.example.jetpack_expedition.main.recent.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ContactBottomSheetContent(
    onClose: suspend () -> Unit,
    onSave: suspend () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
      //  modifier = Modifier.statusBarsPadding().navigationBarsPadding().imePadding()
    ) {
        Text(text = "연락처 추가")
        ContactNickNameField()
        ContactNumberField()
        Row {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    coroutineScope.launch {
                            onClose()
                    }
                }
            ) {
                Text("닫기")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    coroutineScope.launch {
                        onSave()
                    }
                }
            ) {
                Text("저장")
            }
        }
    }
}

@Composable
fun ContactNickNameField() {
    val textState = rememberSaveable {
        mutableStateOf("")
    }
    Column {
        Text(text = "연락처 별칭")
        BasicTextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
            },
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .border(
                            border = BorderStroke(1.dp, Color.LightGray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .fillMaxWidth()
                        .padding(start = 10.dp)
                ) {
                    innerTextField()
                    Divider()
                }
            }
        )
    }
}

@Composable
fun ContactNumberField() {
    val textState = rememberSaveable {
        mutableStateOf("")
    }
    Column {
        Text(text = "번호")
        TextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
            ),
        )
    }
}
