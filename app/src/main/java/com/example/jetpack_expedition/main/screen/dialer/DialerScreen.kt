package com.example.jetpack_expedition.main.screen.dialer

import com.example.jetpack_expedition.main.screen.dialer.view.KeypadView
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import com.example.jetpack_expedition.main.screen.dialer.viewmodel.DialerViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpack_expedition.main.screen.dialer.view.DialerTextFieldView

@Composable
fun DialerScreen(
    dialerViewModel: DialerViewModel = viewModel()
) {
    Log.d("CompositionTest", "DialerScreen")

    Column(
        modifier = Modifier
            .padding(all = 15.dp)
            .background(color = Color.White)
            .fillMaxHeight()
    ) {
        // 연락처 추가, 검색 헤더
//        CommonContactEditHeader()

        Spacer(modifier = Modifier.height(36.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 전화 번호 필드
            DialerTextFieldView(dialerViewModel.number)

            Spacer(modifier = Modifier.height(56.dp))

            // 키패드
            KeypadView(dialerViewModel)
        }
    }
}
