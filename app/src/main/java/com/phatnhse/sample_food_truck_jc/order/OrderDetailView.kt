package com.phatnhse.sample_food_truck_jc.order

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutView
import com.phatnhse.sample_food_truck_jc.foodtruck.model.FoodTruckViewModel
import com.phatnhse.sample_food_truck_jc.navigation.NavigationHeader
import com.phatnhse.sample_food_truck_jc.service.CountdownNotificationHelper
import com.phatnhse.sample_food_truck_jc.ui.composable.Section
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.onBackgroundSecondary
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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

    val bottomSheetState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    var startAnimation by remember {
        mutableStateOf(false)
    }
    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetContent = {
            OrderCompleteView(
                order = order,
                onOrderDone = {
                    scope.launch {
                        bottomSheetState.bottomSheetState.partialExpand()
                    }
                },
                startAnimation = startAnimation
            )
        },
        sheetPeekHeight = 0.dp,
        sheetDragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorScheme.background)
        ) {
            NavigationHeader(
                previousViewTitle = previousViewTitle,
                currentViewTitle = currentViewTitle,
                onBackPressed = onBackPressed,
                menuItems = {
                    Row {
                        IconButton(
                            enabled = !order.isComplete,
                            onClick = {
                                val updateOrder = order.markAsNextStep()
                                viewModel.orders[orderIndex] = updateOrder
                                when (updateOrder.status) {
                                    OrderStatus.PREPARING -> {
                                        prepareOrder(context, order)
                                    }

                                    OrderStatus.COMPLETED -> {
                                        completeOrder(context, order)
                                        scope.launch {
                                            bottomSheetState.bottomSheetState.expand()
                                            startAnimation = true
                                        }
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
                                tint = if (order.isComplete) {
                                    colorScheme.onBackground.copy(
                                        alpha = 0.5F
                                    )
                                } else {
                                    colorScheme.primary
                                }
                            )
                        }
                    }
                }
            )

            StatusSection(order)
            DonutsSection(order)
            TotalDonutsSection(order)
        }
    }
}

@Composable
fun StatusSection(order: Order) {
    Section(title = "status",
        rows = listOf(
            order.status.title,
            "Order Started"
        ),
        trailingViews = listOf(
            {
                Image(
                    painter = order.status.iconSystemName(),
                    contentDescription = "Order status ${order.status.title}",
                    colorFilter = ColorFilter.tint(
                        color = if (order.isComplete) {
                            colorScheme.onBackgroundSecondary()
                        } else {
                            colorScheme.primary
                        }
                    )
                )
            },
            {
                Text(
                    text = order.creationDate.formattedDate(),
                    color = colorScheme.onBackgroundSecondary()
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
                    color = colorScheme.onBackgroundSecondary()
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
                color = colorScheme.onBackgroundSecondary()
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
            viewModel = FoodTruckViewModel.preview
        )
    }
}
