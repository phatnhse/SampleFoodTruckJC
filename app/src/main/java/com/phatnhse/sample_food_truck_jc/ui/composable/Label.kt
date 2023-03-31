package com.phatnhse.sample_food_truck_jc.ui.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.foodtruck.general.shippingPainter
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme
import com.phatnhse.sample_food_truck_jc.utils.SingleDevice

@Composable
fun Label(
    modifier: Modifier = Modifier,
    title: String,
    symbol: Painter?,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = PaddingNormal),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(PaddingNormal),
            text = title,
            color = color,
            style = MaterialTheme.typography.titleMedium
        )

        symbol?.let {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = it,
                contentDescription = "Label icon",
                tint = color
            )
        }
    }
}

@SingleDevice
@Composable
fun HeaderNavigation_Preview() {
    SampleFoodTruckJCTheme {
        Label(
            title = "Shipping",
            symbol = shippingPainter(),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}