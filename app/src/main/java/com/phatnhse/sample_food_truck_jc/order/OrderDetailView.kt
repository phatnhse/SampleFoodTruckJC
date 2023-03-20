package com.phatnhse.sample_food_truck_jc.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutView
import com.phatnhse.sample_food_truck_jc.foodtruck.general.arrowRightSymbol
import com.phatnhse.sample_food_truck_jc.navigation.NavigationHeader
import com.phatnhse.sample_food_truck_jc.ui.composable.Section
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeLarge
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface

@Composable
fun OrderDetailView(
    previousViewTitle: String,
    currentViewTitle: String,
    onBackPressed: () -> Unit,
    order: Order
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.background)
    ) {
        NavigationHeader(
            previousViewTitle = previousViewTitle,
            currentViewTitle = currentViewTitle,
            onBackPressed = onBackPressed
        )

        StatusSection(order)
        DonutsSection(order)
        TotalDonutsSection(order)
    }
}

@Composable
fun StatusSection(order: Order) {
    Section(title = "status",
        rows = listOf(
            "Placed", "Order Started"
        ),
        trailingViews = listOf(
            {
                Image(
                    painter = order.status.iconSystemName(),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        color = colorScheme.onBackground.copy(alpha = 0.5F)
                    )
                )
            },
            {
                Text(
                    text = order.creationDate.formattedDate,
                    color = colorScheme.onBackground.copy(alpha = 0.5F)
                )
            }
        ),
        onItemClicked = {})
}

@Composable
fun DonutsSection(order: Order) {
    Section(
        title = "donuts",
        rows = order.donuts.map { it.name },
        leadingViews = order.donuts.map {
            {
                DonutView(
                    modifier = Modifier.size(IconSizeLarge),
                    donut = it
                )
            }
        },
        trailingViews = order.donuts.map {
            {
                Text(
                    text = order.sales[it.id].toString(),
                    color = colorScheme.onBackground.copy(alpha = 0.5F)
                )
            }
        },
        onItemClicked = {}
    )
}

@Composable
fun TotalDonutsSection(order: Order) {
    Section(
        title = "",
        rows = listOf("Total Donuts"),
        trailingViews = listOf {
            Text(
                text = order.totalSales.toString(),
                color = colorScheme.onBackground.copy(alpha = 0.5F)
            )
        },
        onItemClicked = {}
    )
}

@Preview
@Composable
fun OrderDetailViewPreview() {
    PreviewSurface {
        OrderDetailView(
            previousViewTitle = "Orders",
            currentViewTitle = "Order#1223",
            onBackPressed = {},
            order = Order.preview
        )
    }
}
