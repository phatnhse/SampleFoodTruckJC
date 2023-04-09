package com.phatnhse.sample_food_truck_jc.donut

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.phatnhse.sample_food_truck_jc.foodtruck.model.FoodTruckViewModel
import com.phatnhse.sample_food_truck_jc.foodtruck.model.Timeframe
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface

@Composable
fun TopFiveDonutChart(
    model: FoodTruckViewModel,
    timeframe: Timeframe
) {
    val topSales = model.donutSales(timeframe)

    TopDonutSalesChart(
        sales = topSales
    )
}

@Preview
@Composable
fun TopFiveDonutChart_Preview() {
    PreviewSurface {
        TopFiveDonutChart(
            model = FoodTruckViewModel.preview,
            timeframe = Timeframe.WEEK
        )
    }
}