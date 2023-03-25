package com.phatnhse.sample_food_truck_jc.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.phatnhse.sample_food_truck_jc.foodtruck.model.FoodTruckViewModel
import com.phatnhse.sample_food_truck_jc.navigation.NavigationHeader
import com.phatnhse.sample_food_truck_jc.ui.composable.SearchView
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingZero
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevice

@Composable
fun OrderView(
    previousViewTitle: String,
    currentViewTitle: String,
    onBackPressed: () -> Unit,
    orderClicked: (Order) -> Unit,
    model: FoodTruckViewModel
) {
    var searchText by remember { mutableStateOf("") }

    val newOrders = model.orders.search(OrderStatus.PLACED, searchText)
    val preparingOrders = model.orders.search(OrderStatus.PREPARING, searchText)
    val readyOrders = model.orders.search(OrderStatus.READY, searchText)
    val completeOrders = model.orders.search(OrderStatus.COMPLETED, searchText)

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

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                SearchView(modifier = Modifier.padding(PaddingNormal), onSearch = {
                    searchText = it
                }, onCancel = {
                    searchText = ""
                })
            }
            orders(status = "New", orders = newOrders, orderClicked)
            orders(status = "Preparing", orders = preparingOrders, orderClicked)
            orders(status = "Ready", orders = readyOrders, orderClicked)
            orders(status = "Completed", orders = completeOrders, orderClicked)
        }
    }
}

fun LazyListScope.orders(
    status: String,
    orders: List<Order>,
    orderClicked: (Order) -> Unit
) {
    if (orders.isNotEmpty()) {
        item {
            Text(
                modifier = Modifier.padding(PaddingNormal),
                text = status,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = colorScheme.onBackground
                )
            )
        }
    }

    itemsIndexed(orders, key = { _, order -> order.id }) { index, order ->
        Card(
            modifier = Modifier.padding(horizontal = PaddingNormal), shape = when (index) {
                0 -> {
                    RoundedCornerShape(
                        topStart = PaddingNormal,
                        topEnd = PaddingNormal,
                        bottomEnd = PaddingZero,
                        bottomStart = PaddingZero
                    )
                }

                orders.lastIndex -> {
                    RoundedCornerShape(
                        topStart = PaddingZero,
                        topEnd = PaddingZero,
                        bottomEnd = PaddingNormal,
                        bottomStart = PaddingNormal
                    )
                }

                else -> {
                    RoundedCornerShape(PaddingZero)
                }
            }, colors = CardDefaults.cardColors(
                containerColor = colorScheme.surface
            )
        ) {
            OrderRow(
                order = order,
                onOrderClicked = {
                    orderClicked(it)
                },
                showDivider = index != orders.lastIndex
            )
        }
    }
}

fun List<Order>.search(status: OrderStatus, searchText: String): List<Order> {
    return this.filter { order ->
        val searchCondition = order.matches(searchText) ||
                order.donuts.any {
                    it.matches(searchText)
                }
        order.status == status && searchCondition
    }
}

@SingleDevice
@Composable
fun OrderView_Preview() {
    PreviewSurface {
        OrderView(
            previousViewTitle = "Food Truck",
            currentViewTitle = "Order",
            onBackPressed = {},
            model = FoodTruckViewModel(),
            orderClicked = {}
        )
    }
}