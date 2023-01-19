package com.phatnhse.sample_food_truck_jc.food_truck_kit.general

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun ingredientColor(colorResourceName: String): Color {
    val context = LocalContext.current
    val colorResId: Int = context.resources
        .getIdentifier(colorResourceName, "color", context.packageName)
    return Color(colorResId)
}

