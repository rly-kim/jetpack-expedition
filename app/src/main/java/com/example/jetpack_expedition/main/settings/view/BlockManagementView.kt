package com.example.jetpack_expedition.main.settings.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BlockManagementView(
    navController: NavController
) {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "차단 관리 페이지"
    )
}
