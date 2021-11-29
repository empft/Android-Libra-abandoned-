package com.example.libraandroid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MilanoRedLight = Color(0xffdb1300)
private val BittersweetDark = Color(0xffff6666)
private val SeaGreenLight = Color(0xff277a4b)
private val ScreamingGreenDark = Color(0xff79ff54)

//TODO: Add colourblind support

@Composable
fun positiveColor(): Color {
    return if (isSystemInDarkTheme()) ScreamingGreenDark else SeaGreenLight
}

@Composable
fun negativeColor(): Color {
    return if (isSystemInDarkTheme()) BittersweetDark else MilanoRedLight
}