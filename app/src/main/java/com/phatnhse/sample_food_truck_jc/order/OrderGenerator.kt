package com.phatnhse.sample_food_truck_jc.order

import com.phatnhse.sample_food_truck_jc.foodtruck.city.City
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Donut
import java.lang.Math.pow
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.pow
import kotlin.random.Random

private const val exponentialDonutCountFalloff = 0.15
private val mostPopularDonutCountPerDayCount = 80.0..120.0

data class RandomInfo(val multiplier: Double, val seed: Int)

class OrderGenerator(private val knownDonuts: List<Donut>) {
    val seeds: Map<String, RandomInfo> = mapOf(
        City.cupertino.id to RandomInfo(multiplier = 0.5, seed = 1),
        City.sanFrancisco.id to RandomInfo(multiplier = 1.0, seed = 2),
        City.london.id to RandomInfo(multiplier = 0.75, seed = 3)
    )

    fun todaysOrders(): List<Order> {
        val startingDateTime = LocalDateTime.now()
        val generator = SeededRandomGenerator(1)
        var previousOrderDateTime = startingDateTime.minusMinutes(4 * 60)
        val totalOrders = 24
        return (0 until totalOrders).map { index ->
            previousOrderDateTime = previousOrderDateTime.minusMinutes(Random.nextLong(60, 180))
            val order = generateOrder(
                number = totalOrders - index,
                dateTime = previousOrderDateTime,
                generator = generator
            )
            val isReady = index > 8
            order.copy(
                status = if (isReady) OrderStatus.READY else OrderStatus.PLACED,
                completionDate = if (isReady) previousOrderDateTime.plusMinutes(14 * 60)
                    .coerceAtMost(LocalDateTime.now()) else null
            )
        }
    }

    fun generateOrder(
        number: Int,
        dateTime: LocalDateTime,
        generator: SeededRandomGenerator
    ): Order {
        val donuts = knownDonuts.shuffled(generator).take(generator.nextInt(1, 6))
        val sales: Map<Int, Int> = donuts.associateBy({ it.id }, { generator.nextInt(1, 6) })
        val totalSales = sales.values.sum()
        return Order(
            id = "Order#${12}${String.format("%02d", number)}",
            status = OrderStatus.PLACED,
            donuts = donuts,
            sales = sales,
            grandTotal = (totalSales.toBigDecimal() * 5.78.toBigDecimal()).toDouble(),
            city = City.cupertino.id,
            parkingSpot = City.cupertino.parkingSpots[0].id,
            creationDate = dateTime,
            completionDate = null,
            temperature = Temperature(value = 72.0, unit = Temperature.Unit.FAHRENHEIT),
            wasRaining = false
        )
    }

    fun historicalDailyOrders(since: LocalDateTime, citiId: String): List<OrderSummary> {
        val randomInfo = seeds[citiId] ?: error("No random info found for City ID $citiId")
        val generator = SeededRandomGenerator(randomInfo.seed)
        var previousSales: MutableMap<Int, Int>? = null
        val donuts = knownDonuts.shuffled(generator)
        return (1..60).reversed().map { daysAgo ->
            val startDate = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)
            val day = startDate.minusDays(daysAgo.toLong())
            val dayMultiplier = if (isWeekend(day)) 1.25 else 1.0
            val orderCount = generator.nextDouble(
                mostPopularDonutCountPerDayCount.start,
                mostPopularDonutCountPerDayCount.endInclusive
            )
            val maxDonutCount = orderCount * generator.nextDouble(0.75, 1.1)
            val sales = donuts.indices.associate { index ->
                val donut = donuts[index]
                val percent = 1.0 - (index.toDouble() / knownDonuts.size.toDouble()).pow(
                    exponentialDonutCountFalloff
                )
                val variance = generator.nextDouble(0.9, 1.0)
                val result =
                    (maxDonutCount * percent * variance * randomInfo.multiplier * dayMultiplier).toInt()
                        .coerceAtLeast(0)
                donut.id to result
            }.toMutableMap()
            previousSales?.let {
                sales.forEach { (donutID, count) ->
                    it[donutID]?.let { previousSaleCount ->
                        sales[donutID] =
                            ((count.toDouble() + previousSaleCount.toDouble()) / 2).toInt()
                    }
                }
            }
            previousSales = sales
            OrderSummary(sales)
        }
    }

    fun historicalMonthlyOrders(since: LocalDateTime, cityId: String): List<OrderSummary> {
        val randomInfo = seeds[cityId] ?: error("No random info found for City ID $cityId")
        val generator = SeededRandomGenerator(randomInfo.seed)
        var previousSales: MutableMap<Int, Int>? = null
        val donuts = knownDonuts.shuffled(generator)
        return (0..12).reversed().map { monthsAgo ->
            val orderCount = generator.nextDouble(
                mostPopularDonutCountPerDayCount.start,
                mostPopularDonutCountPerDayCount.endInclusive
            ) * 30
            val maxDonutCount = orderCount * generator.nextDouble(0.75, 1.1)
            val sales = donuts.indices.associate { index ->
                val donut = donuts[index]
                val percent = 1.0 - pow(
                    index.toDouble() / knownDonuts.size.toDouble(),
                    exponentialDonutCountFalloff
                )
                val variance = generator.nextDouble(0.9, 1.0)
                val result = (maxDonutCount * percent * variance * randomInfo.multiplier).toInt()
                    .coerceAtLeast(0)
                donut.id to result
            }.toMutableMap()
            previousSales?.let {
                sales.forEach { (donutID, count) ->
                    it[donutID]?.let { previousSaleCount ->
                        sales[donutID] =
                            ((count.toDouble() + previousSaleCount.toDouble()) / 4).toInt()
                    }
                }
            }
            previousSales = sales
            OrderSummary(sales)
        }
    }

    private fun isWeekend(date: LocalDateTime): Boolean {
        val dayOfWeek = date.dayOfWeek
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY
    }

    class SeededRandomGenerator(seed: Int) : Random() {
        private val random = Random(seed)

        override fun nextBits(bitCount: Int): Int {
            return random.nextBits(bitCount)
        }
    }

}
