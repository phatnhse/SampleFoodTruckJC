package com.phatnhse.sample_food_truck_jc.food_truck_kit.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phatnhse.sample_food_truck_jc.order.Order
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class FoodTruckViewModel : ViewModel() {
    private val _orders = mutableStateListOf<Order>()
    val orders: List<Order> = _orders

    private val _orderAdded = mutableStateOf(false)
    val orderAdded: MutableState<Boolean> = _orderAdded

    init {
        viewModelScope.launch {
            _orders.addAll(Order.previews)
            while (true) {
                delay((3 until 8).random().seconds)
                val generatedOrders = Order.previews[(0 until Order.previews.size).random()]
                _orders.add(generatedOrders)
                _orderAdded.value = true
            }
        }
    }
}