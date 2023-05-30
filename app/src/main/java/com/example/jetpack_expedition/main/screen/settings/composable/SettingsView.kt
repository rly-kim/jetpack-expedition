package com.example.jetpack_expedition.main.screen.settings.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SettingsView(
    settingsMap: MutableMap<SettingBlock, List<SettingBlockContent>>,
    navigateToAdditionalFunctionsPage: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        settingsMap.forEach { entry ->
            Column(
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                Box(
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    entry.key.sectionView()
                }
                entry.value.forEach { content ->
                    Column {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            onClick = { navigateToAdditionalFunctionsPage() }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    content.tabView()
                                }
                                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                            }
                        }
                        content.displayView()
                    }
                }
            }

        }
    }
}
