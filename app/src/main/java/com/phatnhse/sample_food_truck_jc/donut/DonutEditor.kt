package com.phatnhse.sample_food_truck_jc.donut

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Donut
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutView
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Flavor
import com.phatnhse.sample_food_truck_jc.foodtruck.model.FoodTruckViewModel
import com.phatnhse.sample_food_truck_jc.ui.composable.Section
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevice

@Composable
fun DonutEditor(
    previousViewTitle: String,
    currentViewTitle: String,
    onBackPressed: () -> Unit,
    donut: Donut,
    model: FoodTruckViewModel,
) {
    Column(
        modifier = Modifier
            .background(colorScheme.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingLarge),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = PaddingNormal),
                contentAlignment = Alignment.Center
            ) {
                DonutView(
                    modifier = Modifier.size(80.dp),
                    donut = donut
                )
            }
        }

        Section(
            title = "Donut",
            rows = listOf(donut.name),
            showExpandableIcon = false,
            useArrowTrailingViewAsDefault = false
        )

        Section(
            title = "Flavor Profile",
            rows = Flavor.values().map { it.displayName },
            showExpandableIcon = false,
            useArrowTrailingViewAsDefault = false
        )

        Section(
            title = "Ingredients",
            rows = listOf(
                "Dough",
                "Topping",
                "Glaze"
            ),
            showExpandableIcon = false,
            useArrowTrailingViewAsDefault = false
        )
    }
}


@SingleDevice
@Composable
fun DonutEditor_Preview() {
    PreviewSurface {
        DonutEditor(
            previousViewTitle = "Food Truck",
            currentViewTitle = "Donuts",
            onBackPressed = {},
            donut = Donut.classic,
            model = FoodTruckViewModel(),
        )
    }
}