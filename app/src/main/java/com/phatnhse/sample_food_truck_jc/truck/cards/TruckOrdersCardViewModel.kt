package com.phatnhse.sample_food_truck_jc.truck.cards

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phatnhse.sample_food_truck_jc.order.Order
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class TruckOrdersCardViewModel : ViewModel() {
    private val _orders = mutableStateListOf<Order>()
    val orders: List<Order> = _orders

    private val _state = mutableStateOf(false)
    val state: MutableState<Boolean> = _state

    init {
        _orders.addAll(Order.previews)

        viewModelScope.launch {
            Log.d("nhp", "run delay")
            while (true) {
                delay(3.seconds)
                val generatedOrders = Order.previews[(0 until Order.previews.size).random()]
                _orders.add(generatedOrders)
                _state.value = !_state.value
            }
        }
    }
}