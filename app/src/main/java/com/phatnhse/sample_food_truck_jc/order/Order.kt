package com.phatnhse.sample_food_truck_jc.order

import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.Donut

data class Order(
    val donuts: List<Donut>
) {
    companion object {
        private val preview = Order(
            donuts = listOf(Donut.classic)
        )

        private val preview1 = Order(
            donuts = listOf(Donut.picnicBasket, Donut.blackRaspberry)
        )

        private val preview2 = Order(
            donuts = listOf(Donut.classic)
        )

        private val preview3 = Order(
            donuts = listOf(Donut.fireZest, Donut.superLemon, Donut.daytime)
        )

        private val preview4 = Order(
            donuts = Donut.all
        )

        val previews = listOf(
            preview,
            preview1,
            preview2,
            preview3,
            preview4
        )
    }
}