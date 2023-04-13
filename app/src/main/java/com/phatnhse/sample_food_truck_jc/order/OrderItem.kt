package com.phatnhse.sample_food_truck_jc.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutStackView
import com.phatnhse.sample_food_truck_jc.foodtruck.general.arrowRightPainter
import com.phatnhse.sample_food_truck_jc.ui.composable.CustomDivider
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingSmall
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme
import com.phatnhse.sample_food_truck_jc.ui.theme.ShapeRoundedSmall
import com.phatnhse.sample_food_truck_jc.ui.theme.onBackgroundSecondary

@Composable
fun OrderRow(
    modifier: Modifier = Modifier,
    order: Order,
    showDivider: Boolean = true,
    onOrderClicked: (Order) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onOrderClicked(order) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingNormal),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OrderItem(order = order)

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = order.totalSales.toString(),
                        color = colorScheme.onBackground.copy(
                            alpha = 0.5F
                        )
                    )

                    Spacer(modifier = Modifier.width(PaddingSmall))

                    Image(
                        modifier = Modifier
                            .height(12.dp)
                            .width(6.dp),
                        painter = arrowRightPainter(),
                        contentDescription = "Access Order Details",
                        colorFilter = ColorFilter.tint(
                            color = colorScheme.onBackgroundSecondary()
                        )
                    )
                }
            }

            if (showDivider) {
                CustomDivider(paddingStart = 72.dp)
            }
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        DonutStackView(
            donuts = order.donuts,
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = colorScheme.surface, shape = ShapeRoundedSmall
                )
        )
        Spacer(modifier = Modifier.width(PaddingNormal))
        Text(
            text = order.id,
            color = colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Preview
@Composable
fun OrderRowPreview() {
    SampleFoodTruckJCTheme {
        Box(modifier = Modifier.background(colorScheme.background)) {
            OrderRow(
                order = Order.preview2,
                onOrderClicked = {}
            )
        }
    }
}