package com.example.libraandroid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColors(
    primary = RadicalRedLight,
    primaryVariant = RadicalRed,
    onPrimary = Color.Black,
    secondary = BrilliantRoseLight,
    secondaryVariant = BrilliantRose,
    onSecondary = Color.Black
)

private val DarkColors = darkColors(
    primary = RadicalRedDark,
    primaryVariant = RadicalRed,
    onPrimary = Color.White,
    secondary = BrilliantRoseDark,
    secondaryVariant = BrilliantRose,
    onSecondary = Color.White
)

@Composable
fun VanillaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}