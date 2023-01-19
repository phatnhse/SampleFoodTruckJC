package com.phatnhse.sample_food_truck_jc.truck

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.Donut
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.SingleDevice
import com.phatnhse.sample_food_truck_jc.truck.cards.TruckDonutCards
import com.phatnhse.sample_food_truck_jc.truck.cards.TruckOrdersCard
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme

@Composable
fun TruckView() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        TruckOrdersCard(modifier = Modifier.padding(all = 12.dp))
        TruckDonutCards(modifier = Modifier.padding(all = 12.dp), donuts = Donut.all)
    }
}

@SingleDevice
@Composable
fun TruckView_Preview() {
    SampleFoodTruckJCTheme {
        TruckView()
    }
}