package com.phatnhse.sample_food_truck_jc.order

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutView
import com.phatnhse.sample_food_truck_jc.foodtruck.model.FoodTruckViewModel
import com.phatnhse.sample_food_truck_jc.navigation.NavigationHeader
import com.phatnhse.sample_food_truck_jc.service.CountdownNotificationHelper
import com.phatnhse.sample_food_truck_jc.ui.composable.Section
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeLarge
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface

@Composable
fun OrderDetailView(
    previousViewTitle: String,
    currentViewTitle: String,
    onBackPressed: () -> Unit,
    orderId: String,
    viewModel: FoodTruckViewModel
) {
    val context = LocalContext.current

    val orderIndex = viewModel.findOrderIndex(orderId)
    val order = viewModel.orders[orderIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.background)
    ) {
        NavigationHeader(
            previousViewTitle = previousViewTitle,
            currentViewTitle = currentViewTitle,
            onBackPressed = onBackPressed,
            menuItems = listOf {
                IconButton(
                    enabled = !order.isComplete,
                    onClick = {
                        val updateOrder = order.markAsNextStep()
                        viewModel.orders[orderIndex] = updateOrder
                        when (updateOrder.status) {
                            // TODO Add new view for order completed animation here
                            OrderStatus.PREPARING -> {
                                prepareOrder(context, order)
                            }

                            OrderStatus.COMPLETED -> {
                                completeOrder(context, order)
                            }

                            else -> {
                                // do nothing
                            }
                        }
                    }) {
                    Icon(
                        painter = order.status.iconSystemName(
                            fill = order.isComplete
                        ),
                        contentDescription = null,
                        tint = colorScheme.primary
                    )
                }
            }
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
            "Placed",
            "Order Started"
        ),
        trailingViews = listOf(
            {
                Image(
                    painter = order.status.iconSystemName(),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        color = colorScheme.primary
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
        onItemClicked = {}
    )
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

fun prepareOrder(context: Context, order: Order) {
    CountdownNotificationHelper.showCountdownNotification(
        context = context,
        notificationId = order.id.drop(6).toInt(),
        donuts = order.donuts.size
    )
}

fun completeOrder(context: Context, order: Order) {
    CountdownNotificationHelper.cancelCountdownNotification(
        context = context,
        notificationId = order.id.drop(6).toInt()
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
            orderId = Order.preview.id,
            viewModel = FoodTruckViewModel()
        )
    }
}
