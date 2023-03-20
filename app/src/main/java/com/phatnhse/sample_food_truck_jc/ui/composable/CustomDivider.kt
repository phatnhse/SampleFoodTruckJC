package com.phatnhse.sample_food_truck_jc.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomDivider(
    modifier: Modifier = Modifier,
    paddingStart: Dp = 0.dp,
) {
    Divider(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = paddingStart),
        thickness = (0.5F).dp,
        color = MaterialTheme.colorScheme.surfaceVariant
    )
}