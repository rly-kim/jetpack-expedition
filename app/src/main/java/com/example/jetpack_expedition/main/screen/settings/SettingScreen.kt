package com.example.jetpack_expedition.main.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.jetpack_expedition.R
import com.example.jetpack_expedition.main.screen.settings.composable.SettingBlock
import com.example.jetpack_expedition.main.screen.settings.composable.SettingBlockContent
import com.example.jetpack_expedition.main.screen.settings.composable.SettingsView

@Composable
fun SettingsScreen(
    navigateToAdditionalFunctionsPage: () -> Unit
) {
    val settingsMap: MutableMap<SettingBlock, List<SettingBlockContent>> = mutableMapOf(
        SettingBlock.DataSettings to listOf(
            SettingBlockContent({
                Text(text = stringResource(R.string.settingsRecentSectionContent))
            }, {
                Column {
                    Text(text = "총 통화기록 수: 7") /// TEMP
                }
            })
        ),
        SettingBlock.AdditionalFeatures to listOf(
            SettingBlockContent({
                Row {
                    Icon(Icons.Default.Phone, contentDescription = null)
                    Text(text = stringResource(R.string.settingsDualSectionContent))
                }
            }, {}),
            SettingBlockContent({
                Row {
                    Icon(Icons.Default.Phone, contentDescription = null)
                    Text(text = stringResource(R.string.settingsBlockManagementSectionContent))
                }
            }, {})
        ),
    )
    SettingsView(
        settingsMap,
        navigateToAdditionalFunctionsPage,
    )
}
