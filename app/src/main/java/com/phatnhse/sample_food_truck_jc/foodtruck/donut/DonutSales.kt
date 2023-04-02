package com.phatnhse.sample_food_truck_jc.foodtruck.donut

data class DonutSales(
    val donut: Donut,
    val sales: Int
) : Comparable<DonutSales> {
    val id: Int = donut.id

    override fun compareTo(other: DonutSales): Int {
        return if (sales == other.sales) {
            id.compareTo(other.id)
        } else {
            sales.compareTo(other.sales)
        }
    }

    companion object {
        val preview = listOf(
            DonutSales(donut = Donut.classic, sales = 5),
            DonutSales(donut = Donut.picnicBasket, sales = 3),
            DonutSales(donut = Donut.strawberrySprinkles, sales = 10),
            DonutSales(donut = Donut.nighttime, sales = 4),
            DonutSales(donut = Donut.blackRaspberry, sales = 12)
        )
    }
}