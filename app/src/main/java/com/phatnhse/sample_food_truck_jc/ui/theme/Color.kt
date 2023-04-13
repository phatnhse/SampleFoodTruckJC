package com.phatnhse.sample_food_truck_jc.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

val md_theme_light_primary = Color(0xFF4F4CCD)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFE2DFFF)
val md_theme_light_onPrimaryContainer = Color(0xFF0C006A)
val md_theme_light_secondary = Color(0xFF5D5C71)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFE3E0F9)
val md_theme_light_onSecondaryContainer = Color(0xFF1A1A2C)
val md_theme_light_tertiary = Color(0xFF795369)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFFFD8EB)
val md_theme_light_onTertiaryContainer = Color(0xFF2F1124)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFF5F5FA)
val md_theme_light_onBackground = Color(0xFF1C1B1F)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1C1B1F)
val md_theme_light_surfaceVariant = Color(0xFFE4E1EC)
val md_theme_light_onSurfaceVariant = Color(0xFF47464F)
val md_theme_light_outline = Color(0xFF787680)
val md_theme_light_inverseOnSurface = Color(0xFFF3EFF4)
val md_theme_light_inverseSurface = Color(0xFF313034)
val md_theme_light_inversePrimary = Color(0xFFC2C1FF)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF4F4CCD)
val md_theme_light_outlineVariant = Color(0xFFC8C5D0)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFC2C1FF)
val md_theme_dark_onPrimary = Color(0xFF1C0B9F)
val md_theme_dark_primaryContainer = Color(0xFF3631B4)
val md_theme_dark_onPrimaryContainer = Color(0xFFE2DFFF)
val md_theme_dark_secondary = Color(0xFFC6C4DD)
val md_theme_dark_onSecondary = Color(0xFF2F2F42)
val md_theme_dark_secondaryContainer = Color(0xFF454559)
val md_theme_dark_onSecondaryContainer = Color(0xFFE3E0F9)
val md_theme_dark_tertiary = Color(0xFFE9B9D2)
val md_theme_dark_onTertiary = Color(0xFF47263A)
val md_theme_dark_tertiaryContainer = Color(0xFF5F3C50)
val md_theme_dark_onTertiaryContainer = Color(0xFFFFD8EB)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF1C1B1F)
val md_theme_dark_onBackground = Color(0xFFE5E1E6)
val md_theme_dark_surface = Color(0xFF1C1B1F)
val md_theme_dark_onSurface = Color(0xFFE5E1E6)
val md_theme_dark_surfaceVariant = Color(0xFF47464F)
val md_theme_dark_onSurfaceVariant = Color(0xFFC8C5D0)
val md_theme_dark_outline = Color(0xFF918F9A)
val md_theme_dark_inverseOnSurface = Color(0xFF1C1B1F)
val md_theme_dark_inverseSurface = Color(0xFFE5E1E6)
val md_theme_dark_inversePrimary = Color(0xFF4F4CCD)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFC2C1FF)
val md_theme_dark_outlineVariant = Color(0xFF47464F)
val md_theme_dark_scrim = Color(0xFF000000)

val SkyStart = Color(0xFF7ACFED)
val SkyEnd = Color(0xFF7897EC)

val bottomBarColor = Color(0xFFCCE0FF)

val chartColorBlue = Color(0xFF3478F6)
val chartColorGreen = Color(0xFF65C466)
val chartColorOrange = Color(0xFFF19A37)

// Donut
val donutSugarDark = Color(0xFFCEF9FF)
val donutSugarLight = Color(0xFFD9EEFF)

// Dough
val doughBlueBgDark = Color(0xFF4E6D80)
val doughBlueBgLight = Color(0xFF9CAEBF)

val doughBrownBgDark = Color(0xFF5B4E41)
val doughBrownBgLight = Color(0xFF998069)

val doughGreenBgDark = Color(0xFF46694D)
val doughGreenBgLight = Color(0xFF8CC096)

val doughPinkBgDark = Color(0xFF7B4F4D)
val doughPinkBgLight = Color(0xFFBE9A99)

val doughPlainBgDark = Color(0xFF6F604E)
val doughPlainBgLight = Color(0xFFC2A27B)

val doughWhiteBgDark = Color(0xFF586777)
val doughWhiteBgLight = Color(0xFFAEB5BD)

val doughYellowBgDark = Color(0xFF8D7D55)
val doughYellowBgLight = Color(0xFFD9C794)

fun Color.withOpacity(opacity: Float = 0.5F): Color {
    return copy(alpha = opacity)
}

fun ColorScheme.onBackgroundSecondary(opacity: Float = 0.5F): Color {
    return onBackground.withOpacity(opacity)
}