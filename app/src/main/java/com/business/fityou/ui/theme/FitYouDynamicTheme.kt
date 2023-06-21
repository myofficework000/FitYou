package com.business.fityou.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.business.fityou.R

/*
* primary: Button default color
* secondary: Radio button selected color
* background: The background
* onBackground: Text and icon of Settings screen
* surface: Bottom bar and radio group's background
* onSurface: Bottom bar icon color, Text and unselected radio color for radio group
* */

private val theme_light = lightColors(
    primary = blue,
    primaryVariant = lightBlue,
    secondary = white,
    onSecondary = Color.Black,
    background = darkBlue,
    onBackground = white,
    surface = holoGreen,
    onSurface = Color.Black
)

private val theme_dark = darkColors()

private val theme_green = lightColors(
    primary = darkGreen,
    primaryVariant = lightGreen,
    secondary = holoGreen
)

private val theme_red = lightColors(
    primary = darkRed,
    primaryVariant = lightRed,
    secondary = holoRed
)

@Composable
fun FitYouDynamicTheme(themeMode: DynamicThemes, content: @Composable () -> Unit) {
    val colors = when (themeMode) {
        DynamicThemes.DEFAULT_LIGHT -> theme_light
        DynamicThemes.DEFAULT_DARK -> theme_dark
        DynamicThemes.GREEN -> theme_green
        DynamicThemes.RED -> theme_red
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

private val light = "Light"
private val dark = "Dark"
private val green = "Casual"
private val red = "Hardcore"

// If there is a renaming

enum class DynamicThemes {
    DEFAULT_LIGHT,
    DEFAULT_DARK,
    GREEN,
    RED
}

fun DynamicThemes.toName() = when (this) {
    DynamicThemes.DEFAULT_LIGHT -> light
    DynamicThemes.DEFAULT_DARK -> dark
    DynamicThemes.GREEN -> green
    DynamicThemes.RED -> red
}

fun String.toDynamicTheme(): DynamicThemes? = when (this) {
    light -> DynamicThemes.DEFAULT_LIGHT
    dark -> DynamicThemes.DEFAULT_DARK
    green -> DynamicThemes.GREEN
    red -> DynamicThemes.RED
    else -> null
}

/* // For future use
fun DynamicThemes.toStringRes() = when (this) {
    DynamicThemes.DEFAULT_LIGHT -> R.string.test
    DynamicThemes.DEFAULT_DARK -> R.string.test
    DynamicThemes.GREEN -> R.string.test
    DynamicThemes.RED -> R.string.test
}*/
