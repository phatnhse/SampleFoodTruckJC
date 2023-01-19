package com.phatnhse.sample_food_truck_jc.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    secondary = secondaryDark,
    tertiary = tertiaryDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = cardBackgroundDark,
)

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    secondary = secondaryLight,
    tertiary = tertiaryLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = cardBackgroundLight,
)

@Composable
fun SampleFoodTruckJCTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorSchemeColors = if (!useDarkTheme) {
        LightColorScheme
    } else {
        DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorSchemeColors,
        content = content,
        typography = Typography
    )
}
