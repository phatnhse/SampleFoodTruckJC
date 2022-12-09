package com.phatnhse.sample_food_truck_jc.general

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.phatnhse.sample_food_truck_jc.R
import com.phatnhse.sample_food_truck_jc.donut.Bitter
import com.phatnhse.sample_food_truck_jc.donut.Flavor
import com.phatnhse.sample_food_truck_jc.donut.Salty
import com.phatnhse.sample_food_truck_jc.donut.Savory
import com.phatnhse.sample_food_truck_jc.donut.Sour
import com.phatnhse.sample_food_truck_jc.donut.Spicy
import com.phatnhse.sample_food_truck_jc.donut.Sweet

@Composable
fun donutSymbol(): Painter {
    return painterResource(id = R.drawable.donut)
}

@Composable
fun flavorSymbol(flavor: Flavor): Painter {
    when (flavor) {
        is Bitter ->
        is Salty -> TODO()
        is Savory -> TODO()
        is Sour -> TODO()
        is Spicy -> TODO()
        is Sweet -> TODO()
    }
}

