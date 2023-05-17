package com.example.jetpack_expedition.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType


@OptIn(ExperimentalUnitApi::class)
val CallHistorySavedSingleTextStyle = TextStyle(
    color = Color.Black,
    fontFamily = FontFamily.SansSerif,
    fontSize = TextUnit(value = 15F, type = TextUnitType.Unspecified),
)

@OptIn(ExperimentalUnitApi::class)
val CallHistoryUnsavedSingleTextStyle = TextStyle(
    color = Color.Red,
    fontFamily = FontFamily.SansSerif,
    fontSize = TextUnit(value = 22F, type = TextUnitType.Unspecified),
)
