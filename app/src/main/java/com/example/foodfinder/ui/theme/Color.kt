package com.example.foodfinder.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val MidnightBlue = Color(0xFF1B1F3B)
val AnthraciteGray = Color(0xFF2E2E38)
val SoftGold = Color(0xFFE1B07E)
val LightGray = Color(0xFFF5F5F5)
val DesaturatedRed = Color(0xFFB23A48)

val LightColors = lightColorScheme(
    primary = SoftGold,
    secondary = SoftGold,
    background = MidnightBlue,
    surface = AnthraciteGray,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = LightGray,
    onSurface = LightGray,
    error = DesaturatedRed
)

val DarkColors = darkColorScheme(
    primary = SoftGold,
    secondary = SoftGold,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = LightGray,
    onSurface = LightGray,
    error = DesaturatedRed
)

