package com.example.jetpack_expedition.main.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpack_expedition.Greeting

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val settingsMap: MutableMap<SettingBlock, List<SettingBlockContent>> = mutableMapOf(
        SettingBlock.DataSettings to listOf(
            SettingBlockContent({
                Text(text = "최근기록")
            }, {
                Column {
                    Text(text = "총 통화기록 수: 7")
                }
            })
        ),
        SettingBlock.AdditionalFeatures to listOf(
            SettingBlockContent({
                Row {
                    Icon(Icons.Default.Phone, contentDescription = null)
                    Text(text = "듀얼번호")
                }
            }, {

            }),
            SettingBlockContent({
                Row {
                    Icon(Icons.Default.Phone, contentDescription = null)
                    Text(text = "차단관리")
                }
            }, {

            })
        ),
    )
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 20.dp)
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
                            onClick = { navController.navigate("부가기능") }
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

class SettingBlockContent(
    val tabView: @Composable () -> Unit,
    val displayView: @Composable () -> Unit,
)

enum class SettingBlock(
    val sectionView: @Composable () -> Unit,
) {
    DataSettings({
        Row {
            Icons.Default.ThumbUp
            Text(text = "데이터")
        }
    }),
    AdditionalFeatures({
        Row {
            Text(text = "부가기능")
        }
    })
}
