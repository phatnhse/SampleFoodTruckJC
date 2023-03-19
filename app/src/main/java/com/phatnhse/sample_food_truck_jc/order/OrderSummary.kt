package com.phatnhse.sample_food_truck_jc.order

data class OrderSummary(
    val sales: MutableMap<Int, Int>,
    var totalSales: Int
) {
    constructor(sales: MutableMap<Int, Int>) : this(
        sales,
        sales.values.sum()
    )

    fun union(other: OrderSummary): OrderSummary {
        val newSales = sales.toMutableMap()
        for (donutID in (sales.keys + other.sales.keys)) {
            newSales[donutID] = (sales[donutID] ?: 0) + (other.sales[donutID] ?: 0)
        }
        val newTotalSales = totalSales + other.totalSales
        return OrderSummary(newSales, newTotalSales)
    }

    fun formUnion(other: OrderSummary) {
        this.sales.putAll(other.sales)
        this.totalSales += other.totalSales
    }

    fun union(order: Order): OrderSummary {
        val newSales = sales.toMutableMap()
        for (donutID in (sales.keys + order.sales.keys)) {
            newSales[donutID] = (sales[donutID] ?: 0) + (order.sales[donutID] ?: 0)
        }
        val newTotalSales = totalSales + order.totalSales
        return OrderSummary(newSales, newTotalSales)
    }

    fun formUnion(order: Order) {
        this.sales.putAll(order.sales)
        this.totalSales += order.totalSales
    }

    companion object {
        val empty = OrderSummary(mutableMapOf())
    }
}