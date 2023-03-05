package com.phatnhse.sample_food_truck_jc.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme

@Composable
fun PreviewSurface(children: @Composable () -> Unit) {
    SampleFoodTruckJCTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background,
            content = children
        )
    }
}