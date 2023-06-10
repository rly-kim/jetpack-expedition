package com.example.jetpack_expedition.ui.cuteTheme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val cuteColorPalette = cuteColors()

fun cuteColors(
    primary: Color = Color(0xFF7F2662),
    primaryVariant: Color = Color(0xFFAB3384),
    secondary: Color = Color(0xFF982D48),
    secondaryVariant: Color = Color(0xFFCC3D61),
    background: Color = Color(0xFF2A0D21),
    surface: Color = Color(0xFF330F18),
    error: Color = Color(0xFF9A41C3),
    onPrimary: Color = Color.White,
    onSecondary: Color = Color.White,
    onBackground: Color = Color.White,
    onSurface: Color = Color.White,
    onError: Color = Color.White
): Colors = Colors(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    background,
    surface,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    onSurface,
    onError,
    true,
)
