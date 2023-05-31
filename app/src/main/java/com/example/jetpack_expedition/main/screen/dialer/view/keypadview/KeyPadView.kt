package com.example.jetpack_expedition.main.screen.dialer.view.keypadview

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.jetpack_expedition.main.screen.dialer.viewmodel.DialerViewModel

@Composable
fun KeypadView(
    dialerViewModel: DialerViewModel,
) {
    Log.d("CompositionTest", "KeypadView")

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("PermissionTest","Manifest.permission.CALL_PHONE GRANTED")
            dialerViewModel.call(context)
        }
        else {
            Log.d("PermissionTest","Manifest.permission.CALL_PHONE DENIED")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            KeyButtonView(
                key = "1",
                onClick = { dialerViewModel.keypadTap("1") }
            )
            KeyButtonView(
                key = "2",
                onClick = { dialerViewModel.keypadTap("2") }
            )
            KeyButtonView(
                key = "3",
                onClick = { dialerViewModel.keypadTap("3") }
            )
        }
        Row {
            KeyButtonView(
                key = "4",
                onClick = { dialerViewModel.keypadTap("4") }
            )
            KeyButtonView(
                key = "5",
                onClick = { dialerViewModel.keypadTap("5") }
            )
            KeyButtonView(
                key = "6",
                onClick = { dialerViewModel.keypadTap("6") }
            )
        }
        Row {
            KeyButtonView(
                key = "7",
                onClick = { dialerViewModel.keypadTap("7") }
            )
            KeyButtonView(
                key = "8",
                onClick = { dialerViewModel.keypadTap("8") }
            )
            KeyButtonView(
                key = "9",
                onClick = { dialerViewModel.keypadTap("9") }
            )
        }
        Row {
            KeyButtonView(
                key = "*",
                onClick = { dialerViewModel.keypadTap("*") }
            )
            KeyButtonView(
                key = "0",
                onClick = { dialerViewModel.keypadTap("0") }
            )
            KeyButtonView(
                key = "#",
                onClick = { dialerViewModel.keypadTap("#") }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            // 전화 걸기
            IconButton(
                onClick = {
                    if (dialerViewModel.checkCallPermission(context)) {
                        dialerViewModel.call(context)
                    }
                    else {
                        permissionLauncher.launch(Manifest.permission.CALL_PHONE)
                    }
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
                onClick = { dialerViewModel.delete() }
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