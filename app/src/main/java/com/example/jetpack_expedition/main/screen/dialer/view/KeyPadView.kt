package com.example.jetpack_expedition.main.screen.dialer.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpack_expedition.main.screen.dialer.viewmodel.DialerViewModel

@Composable
fun KeypadView(
    dialerViewModel: DialerViewModel,
) {
    Log.d("CompositionTest", "KeypadView")
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            KeyButtonView(
                key = "1",
                onClick = { dialerViewModel.onKeyTapped("1") }
            )
            KeyButtonView(
                key = "2",
                onClick = { dialerViewModel.onKeyTapped("2") }
            )
            KeyButtonView(
                key = "3",
                onClick = { dialerViewModel.onKeyTapped("3") }
            )
        }
        Row {
            KeyButtonView(
                key = "4",
                onClick = { dialerViewModel.onKeyTapped("4") }
            )
            KeyButtonView(
                key = "5",
                onClick = { dialerViewModel.onKeyTapped("5") }
            )
            KeyButtonView(
                key = "6",
                onClick = { dialerViewModel.onKeyTapped("6") }
            )
        }
        Row {
            KeyButtonView(
                key = "7",
                onClick = { dialerViewModel.onKeyTapped("7") }
            )
            KeyButtonView(
                key = "8",
                onClick = { dialerViewModel.onKeyTapped("8") }
            )
            KeyButtonView(
                key = "9",
                onClick = { dialerViewModel.onKeyTapped("9") }
            )
        }
        Row {
            KeyButtonView(
                key = "*",
                onClick = { dialerViewModel.onKeyTapped("*") }
            )
            KeyButtonView(
                key = "0",
                onClick = { dialerViewModel.onKeyTapped("0") }
            )
            KeyButtonView(
                key = "#",
                onClick = { dialerViewModel.onKeyTapped("#") }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            // 전화 걸기
            IconButton(
                onClick = {
//                    phoneCall(
//                        context = context,
//                        phoneNumber = dialerViewModel.numberState.number,
//                        permissionsLauncher = permissionsLauncher,
//                    )
                }
            ) {
                Icon(
                    modifier = Modifier.size(44.dp),
                    imageVector = Icons.Default.Call,
                    contentDescription = "com.example.jetpack_expedition.main.contact.domain.Call",
                )
            }

            Spacer(modifier = Modifier.width(28.dp))

            // 전화번호 필드 지우기
            IconButton(
                onClick = { dialerViewModel.onDeleteTapped() }
            ) {
                Icon(
                    modifier = Modifier.size(44.dp),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "ArrowBack",
                )
            }

        }
    }
}