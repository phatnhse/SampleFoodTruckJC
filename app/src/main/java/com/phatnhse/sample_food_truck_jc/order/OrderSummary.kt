package com.phatnhse.sample_food_truck_jc.order

data class OrderSummary(
    var sales: MutableMap<Int, Int>,
    var totalSales: Int
) {
    constructor(sales: MutableMap<Int, Int>) : this(
        sales,
        sales.values.sum()
    )
}

fun List<List<OrderSummary>>.toOrderSummary(days: Int): OrderSummary {
    val newSales = mutableMapOf<Int, Int>()
    var newTotalSales = 0
    forEach { orderSummaries ->
        val summary = orderSummaries.take(days).unionOrderSummaries()
        for (donutId in summary.sales.keys) {
            newSales[donutId] =
                newSales.getOrDefault(donutId, 0) + (summary.sales[donutId] ?: 0)
        }

        newTotalSales += summary.totalSales
    }

    return OrderSummary(newSales, newTotalSales)
}

private fun List<OrderSummary>.unionOrderSummaries(): OrderSummary {
    val newSales = mutableMapOf<Int, Int>()
    var newTotalSales = 0
    forEach { orderSummary ->
        for (donutId in orderSummary.sales.keys) {
            newSales[donutId] =
                newSales.getOrDefault(donutId, 0) + (orderSummary.sales[donutId] ?: 0)
        }
        newTotalSales += orderSummary.totalSales
    }

    return OrderSummary(newSales, newTotalSales)
}

fun List<Order>.unionOrders(): OrderSummary {
    val newSales = mutableMapOf<Int, Int>()
    var newTotalSales = 0
    forEach { order ->
        for (donutId in order.sales.keys) {
            newSales[donutId] = newSales.getOrDefault(donutId, 0) + (order.sales[donutId] ?: 0)
        }
        newTotalSales += order.totalSales
    }

    return OrderSummary(newSales, newTotalSales)
}