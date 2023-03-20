package com.phatnhse.sample_food_truck_jc.foodtruck.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.phatnhse.sample_food_truck_jc.foodtruck.city.City
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Donut
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Dough
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Glaze
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Topping
import com.phatnhse.sample_food_truck_jc.order.Order
import com.phatnhse.sample_food_truck_jc.order.OrderGenerator
import com.phatnhse.sample_food_truck_jc.order.OrderSummary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class FoodTruckViewModel {
    var orders = mutableStateListOf<Order>()
    var orderAdded = mutableStateOf(false)
    var donuts by mutableStateOf(Donut.all)

    var newDonut by mutableStateOf(
        Donut(
            id = Donut.all.size,
            name = "New Donut",
            dough = Dough.plain,
            glaze = Glaze.chocolate,
            topping = Topping.sprinkles
        )
    )

    private var dailyOrderSummaries: Map<String, List<OrderSummary>> = emptyMap()
    private var monthlyOrderSummaries: Map<String, List<OrderSummary>> = emptyMap()

    init {
        val orderGenerator = OrderGenerator(knownDonuts = donuts)
        orders.addAll(orderGenerator.todaysOrders())
        dailyOrderSummaries = City.all.associate { city ->
            city.id to orderGenerator.historicalDailyOrders(
                since = LocalDateTime.now(), citiId = city.id
            )
        }
        monthlyOrderSummaries = City.all.associate { city ->
            city.id to orderGenerator.historicalMonthlyOrders(
                since = LocalDateTime.now(), cityId = city.id
            )
        }

        CoroutineScope(Dispatchers.IO).launch {
            val generator = OrderGenerator.SeededRandomGenerator(seed = 5)
            repeat(20) {
                delay((3..8).random(generator).toLong() * 1000)
                withContext(Dispatchers.Main) {
                    orders.add(
                        orderGenerator.generateOrder(
                            number = orders.size + 1,
                            dateTime = LocalDateTime.now(),
                            generator = generator
                        )
                    )
                    orderAdded.value = true
                }
            }
        }
    }

    fun findOrder(orderId: String?): Order {
        if (orderId == null) {
            throw RuntimeException("Order id can't be null")
        }
        return orders.firstOrNull {
            orderId == it.id
        } ?: throw Exception("Order not found")
    }
}