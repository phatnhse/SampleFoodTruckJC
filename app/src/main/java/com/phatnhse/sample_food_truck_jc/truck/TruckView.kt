package com.phatnhse.sample_food_truck_jc.truck

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.SingleDevice
import com.phatnhse.sample_food_truck_jc.truck.cards.TruckDonutCards
import com.phatnhse.sample_food_truck_jc.truck.cards.TruckOrdersCard
import com.phatnhse.sample_food_truck_jc.truck.cards.TruckSocialFeedCard
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme

@Composable
fun TruckView() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(PaddingNormal),
        verticalArrangement = Arrangement.spacedBy(
            space = PaddingNormal,
            alignment = Alignment.CenterVertically
        )
    ) {
        TruckOrdersCard()
        TruckDonutCards()
        TruckSocialFeedCard()
    }
}

@SingleDevice
@Composable
fun TruckView_Preview() {
    SampleFoodTruckJCTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            TruckView()
        }
    }
}