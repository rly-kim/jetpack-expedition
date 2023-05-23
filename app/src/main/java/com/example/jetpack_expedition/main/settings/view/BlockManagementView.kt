package com.example.jetpack_expedition.main.settings.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.jetpack_expedition.R

@Composable
fun BlockManagementView(
) {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = stringResource(R.string.blockManagementPageTitle)
    )
}
