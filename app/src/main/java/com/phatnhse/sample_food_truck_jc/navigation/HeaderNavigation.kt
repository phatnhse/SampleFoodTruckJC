package com.phatnhse.sample_food_truck_jc.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.R
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.SingleDevice
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.shippingSymbol
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme

@Composable
fun HeaderNavigation(
    modifier: Modifier = Modifier,
    title: String,
    symbol: Painter?,
    color: Color? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = PaddingNormal),
        verticalAlignment = Alignment.CenterVertically
    ) {
        symbol?.let {
            Image(
                modifier = Modifier
                    .size(20.dp),
                painter = it,
                contentDescription = "Header Symbol",
                colorFilter = ColorFilter.tint(
                    color = color ?: colorScheme.primary
                )
            )
        }

        Text(
            modifier = Modifier.padding(PaddingNormal),
            text = title,
            color = color ?: colorScheme.primary,
            style = typography.titleMedium
        )

        Image(
            modifier = Modifier
                .height(12.dp)
                .width(6.dp),
            painter = painterResource(id = R.drawable.chevron_right),
            contentDescription = "Chevron Right",
            colorFilter = ColorFilter.tint(
                color = colorScheme.onBackground.copy(alpha = 0.5F)
            )
        )
    }
}

@SingleDevice
@Composable
fun HeaderNavigation_Preview() {
    SampleFoodTruckJCTheme {
        HeaderNavigation(
            title = "Shipping",
            symbol = shippingSymbol()
        )
    }
}