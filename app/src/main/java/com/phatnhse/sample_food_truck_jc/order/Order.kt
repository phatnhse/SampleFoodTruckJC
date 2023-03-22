package com.phatnhse.sample_food_truck_jc.order

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.phatnhse.sample_food_truck_jc.foodtruck.city.City
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Donut
import com.phatnhse.sample_food_truck_jc.foodtruck.general.checkmarkCircleSymbol
import com.phatnhse.sample_food_truck_jc.foodtruck.general.paperplaneSymbol
import com.phatnhse.sample_food_truck_jc.foodtruck.general.shippingSymbol
import com.phatnhse.sample_food_truck_jc.foodtruck.general.timerSymbol
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.format.DateTimeFormatter

data class Order(
    val id: String,
    var status: OrderStatus,
    val donuts: List<Donut>,
    val sales: Map<Int, Int>,
    val grandTotal: Double,
    val city: String,
    val parkingSpot: String,
    val creationDate: LocalDateTime,
    val completionDate: LocalDateTime?,
    val temperature: Temperature,
    val wasRaining: Boolean
) {
    val duration: Long?
        get() = completionDate?.let { ChronoUnit.SECONDS.between(creationDate, it) }
    val totalSales: Int
        get() = sales.values.sum()

    fun markAsComplete(): Order {
        return if (status == OrderStatus.COMPLETED) this
        else copy(status = OrderStatus.COMPLETED, completionDate = LocalDateTime.now())
    }

    fun markAsNextStep(): Order {
        val newStatus = when (status) {
            OrderStatus.PLACED -> OrderStatus.PREPARING
            OrderStatus.PREPARING -> OrderStatus.COMPLETED
            else -> status
        }
        return copy(status = newStatus)
    }

    fun markAsPreparing(): Order {
        return if (status == OrderStatus.PREPARING) this
        else copy(status = OrderStatus.PREPARING)
    }

    val isPreparing: Boolean
        get() = status == OrderStatus.PREPARING

    val isComplete: Boolean
        get() = status == OrderStatus.COMPLETED

    fun matches(searchText: String): Boolean {
        return id.contains(searchText, ignoreCase = true)
    }

    companion object {
        val preview: Order
            get() = Order(
                id = "Order#1203",
                status = OrderStatus.PLACED,
                donuts = listOf(Donut.classic),
                sales = mapOf(Donut.classic.id to 1),
                grandTotal = BigDecimal("4.78").toDouble(),
                city = City.cupertino.id,
                parkingSpot = City.cupertino.parkingSpots[0].id,
                creationDate = LocalDateTime.now(),
                completionDate = null,
                temperature = Temperature(value = 72.0, unit = Temperature.Unit.FAHRENHEIT),
                wasRaining = false
            )

        val preview2: Order
            get() = Order(
                id = "Order#1204",
                status = OrderStatus.READY,
                donuts = listOf(Donut.picnicBasket, Donut.blackRaspberry),
                sales = mapOf(Donut.picnicBasket.id to 2, Donut.blackRaspberry.id to 1),
                grandTotal = BigDecimal("4.78").toDouble(),
                city = City.cupertino.id,
                parkingSpot = City.cupertino.parkingSpots[0].id,
                creationDate = LocalDateTime.now(),
                completionDate = null,
                temperature = Temperature(value = 72.0, unit = Temperature.Unit.FAHRENHEIT),
                wasRaining = false
            )

        val preview3: Order
            get() = Order(
                id = "Order#1205",
                status = OrderStatus.READY,
                donuts = listOf(Donut.classic),
                sales = mapOf(Donut.classic.id to 1),
                grandTotal = BigDecimal("4.78").toDouble(),
                city = City.cupertino.id,
                parkingSpot = City.cupertino.parkingSpots[0].id,
                creationDate = LocalDateTime.now(),
                completionDate = null,
                temperature = Temperature(value = 72.0, unit = Temperature.Unit.FAHRENHEIT),
                wasRaining = false
            )

        val preview4: Order
            get() = Order(
                id = "Order#1206",
                status = OrderStatus.READY,
                donuts = listOf(Donut.fireZest, Donut.superLemon, Donut.daytime),
                sales = mapOf(
                    Donut.fireZest.id to 1, Donut.superLemon.id to 1, Donut.daytime.id to 1
                ),
                grandTotal = BigDecimal("4.78").toDouble(),
                city = City.cupertino.id,
                parkingSpot = City.cupertino.parkingSpots[0].id,
                creationDate = LocalDateTime.now(),
                completionDate = null,
                temperature = Temperature(value = 72.0, unit = Temperature.Unit.FAHRENHEIT),
                wasRaining = false
            )

        val previews = listOf(preview, preview2, preview3, preview4)
    }
}

val LocalDateTime.formattedDate: String
    get() {
        val currentDate = LocalDateTime.now()
        val today = currentDate.truncatedTo(ChronoUnit.DAYS)
        val yesterday = today.minusDays(1)
        val date: String = when {
            truncatedTo(ChronoUnit.DAYS).isEqual(today) -> "Today"
            truncatedTo(ChronoUnit.DAYS).isEqual(yesterday) -> "Yesterday"
            else -> format(DateTimeFormatter.ofPattern("M/d/yyyy"))
        }
        val time = format(DateTimeFormatter.ofPattern("h:mm a"))
        return "$date, $time"
    }

enum class OrderStatus() : Comparable<OrderStatus> {
    PLACED, PREPARING, READY, COMPLETED;

    val title: String
        get() = when (this) {
            PLACED -> "Placed"
            PREPARING -> "Preparing"
            READY -> "Ready"
            COMPLETED -> "Completed"
        }

    val buttonTitle: String
        get() = when (this) {
            PLACED -> "Prepare"
            PREPARING -> "Ready"
            READY -> "Complete"
            COMPLETED -> "Complete"
        }

    @Composable
    fun iconSystemName(fill: Boolean = false): Painter {
        return when (this) {
            PLACED -> paperplaneSymbol()
            PREPARING -> timerSymbol()
            READY -> checkmarkCircleSymbol()
            COMPLETED -> shippingSymbol(fill = fill)
        }
    }

}

data class Temperature(val value: Double, val unit: Unit) {
    enum class Unit {
        FAHRENHEIT,
        CELSIUS,
        KELVIN
    }

    fun toCelsius(): Double = when (unit) {
        Unit.FAHRENHEIT -> (value - 32) * 5 / 9
        Unit.CELSIUS -> value
        Unit.KELVIN -> value - 273.15
    }

    fun toFahrenheit(): Double = when (unit) {
        Unit.FAHRENHEIT -> value
        Unit.CELSIUS -> (value * 9 / 5) + 32
        Unit.KELVIN -> (value - 273.15) * 9 / 5 + 32
    }

    fun toKelvin(): Double = when (unit) {
        Unit.FAHRENHEIT -> (value - 32) * 5 / 9 + 273.15
        Unit.CELSIUS -> value + 273.15
        Unit.KELVIN -> value
    }

    override fun toString(): String = when (unit) {
        Unit.FAHRENHEIT -> "$value°F"
        Unit.CELSIUS -> "$value°C"
        Unit.KELVIN -> "$value K"
    }
}