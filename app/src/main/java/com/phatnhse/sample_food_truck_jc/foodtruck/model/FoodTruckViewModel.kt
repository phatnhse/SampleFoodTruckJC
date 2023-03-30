package com.phatnhse.sample_food_truck_jc.foodtruck.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phatnhse.sample_food_truck_jc.foodtruck.city.City
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Donut
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Flavor
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Dough
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Glaze
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Topping
import com.phatnhse.sample_food_truck_jc.order.Order
import com.phatnhse.sample_food_truck_jc.order.OrderGenerator
import com.phatnhse.sample_food_truck_jc.order.OrderSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class FoodTruckViewModel : ViewModel() {
    var orders = mutableStateListOf<Order>()
    var orderAdded = mutableStateOf(false)
    var donuts = mutableStateListOf<Donut>()

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
        donuts.addAll(Donut.all)

        val orderGenerator = OrderGenerator(knownDonuts = donuts.toList())
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

        viewModelScope.launch(Dispatchers.IO) {
            val generator = OrderGenerator.SeededRandomGenerator(seed = 5)
            repeat(20) {
                delay((3..8).random(generator).toLong() * 1000)
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

    fun findOrderIndex(orderId: String?): Int {
        if (orderId == null) {
            throw Exception("Order id can't be null")
        }

        val orderIndex = orders.indexOfFirst {
            orderId == it.id
        }

        if (orderIndex < 0) {
            throw Exception("Order not found")
        } else {
            return orderIndex
        }
    }

    fun findDonutIndex(donutId: Int): Int {
        if (donutId < 0) {
            throw Exception("Donut id can't smaller than 0")
        }

        val donutIndex = donuts.indexOfFirst {
            donutId == it.id
        }

        if (donutIndex < 0) {
            throw Exception("Donut not found")
        } else {
            return donutIndex
        }
    }


    fun donuts(sortedBy: DonutSortOrder = DonutSortOrder.SortByPopularity(Timeframe.MONTH)): List<Donut> {
        return when (sortedBy) {
            is DonutSortOrder.SortByPopularity -> donutsSortedByPopularity(timeframe = sortedBy.timeframe)
            is DonutSortOrder.SortByName -> donuts
                .sortedWith(compareBy { it.name })

            is DonutSortOrder.SortByFlavor -> donuts
                .sortedByDescending { it.flavors[sortedBy.flavor] }
        }
    }


    private fun donutsSortedByPopularity(timeframe: Timeframe): List<Donut> {
        return combinedOrderSummary(timeframe).sales
            .entries
            .sortedWith(compareByDescending<Map.Entry<Int, Int>> { it.value }.thenBy { it.key })
            .map { donut(id = it.key) }
    }

    fun donut(id: Int): Donut {
        return donuts.first {
            it.id == id
        }
    }

    private fun combinedOrderSummary(timeframe: Timeframe): OrderSummary {
        return when (timeframe) {
            Timeframe.TODAY -> orders.fold(OrderSummary.empty) { partialResult, order ->
                partialResult.formUnion(order)
                partialResult
            }

            Timeframe.WEEK -> dailyOrderSummaries.values.fold(OrderSummary.empty) { partialResult, summaries ->
                summaries.take(7).forEach { day -> partialResult.formUnion(day) }
                partialResult
            }

            Timeframe.MONTH -> dailyOrderSummaries.values.fold(OrderSummary.empty) { partialResult, summaries ->
                summaries.take(30).forEach { day -> partialResult.formUnion(day) }
                partialResult
            }

            Timeframe.YEAR -> monthlyOrderSummaries.values.fold(OrderSummary.empty) { partialResult, summaries ->
                summaries.forEach { month -> partialResult.formUnion(month) }
                partialResult
            }
        }
    }

    companion object {
        val preview = FoodTruckViewModel()
    }
}

sealed class DonutSortOrder {
    data class SortByPopularity(val timeframe: Timeframe) : DonutSortOrder()
    object SortByName : DonutSortOrder()
    data class SortByFlavor(val flavor: Flavor) : DonutSortOrder()
}

enum class Timeframe(val title: String) {
    TODAY("Today"),
    WEEK("Week"),
    MONTH("Month"),
    YEAR("Year");
}