package com.example.jetpack_expedition.main.screen.settings.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.jetpack_expedition.R

@Composable
fun BlockManagementView(
    onPopStack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.blockManagementPageTitle)) },
                navigationIcon = {
                    IconButton(onClick = {
                        onPopStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Text(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            text = stringResource(R.string.blockManagementPageTitle)
        )
    }
}
