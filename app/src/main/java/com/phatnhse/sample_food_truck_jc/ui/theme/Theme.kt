package com.phatnhse.sample_food_truck_jc.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Tertiary,
    background = backgroundDark,
    surface = cardBackgroundDark,
    onBackground = onBackgroundDark
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Tertiary,
    background = backgroundLight,
    surface = cardBackgroundLight,
    onBackground = onBackgroundLight
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
