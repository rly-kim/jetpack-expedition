package com.example.jetpack_expedition.main.screen.settings.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.jetpack_expedition.R

data class SettingBlockContent(
    val tabView: @Composable () -> Unit,
    val displayView: @Composable () -> Unit,
)

enum class SettingBlock(
    val sectionView: @Composable () -> Unit,
) {
    DataSettings({
        Row {
            Icons.Default.ThumbUp
            Text(text = stringResource(R.string.settingsDataSectionText))
        }
    }),
    AdditionalFeatures({
        Row {
            Text(text = stringResource(R.string.settingsAdditionalFunctionsSectionText))
        }
    })
}
