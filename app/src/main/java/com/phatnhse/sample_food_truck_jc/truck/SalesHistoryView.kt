package com.phatnhse.sample_food_truck_jc.truck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.phatnhse.sample_food_truck_jc.foodtruck.model.FoodTruckViewModel
import com.phatnhse.sample_food_truck_jc.foodtruck.model.Timeframe
import com.phatnhse.sample_food_truck_jc.navigation.NavigationHeader
import com.phatnhse.sample_food_truck_jc.order.formattedDate
import com.phatnhse.sample_food_truck_jc.ui.composable.TabLayout
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingExtraLarge
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview


@Composable
fun SalesHistoryView(
    previousViewTitle: String = "Food Truck",
    currentViewTitle: String = "Sales History",
    onBackPressed: () -> Unit,
    model: FoodTruckViewModel
) {
    var timeframe by remember { mutableStateOf(Timeframe.WEEK) }
    val supportedTimeframes = Timeframe.values().toList() - Timeframe.TODAY
    val salesByCity = model.getSalesByCity(timeframe)
    val totalSales = salesByCity.flatMap { it.entries }.sumOf { it.sales }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavigationHeader(
            previousViewTitle = previousViewTitle,
            currentViewTitle = currentViewTitle,
            onBackPressed = onBackPressed
        )

        Spacer(modifier = Modifier.height(PaddingExtraLarge))
        TabLayout(
            tabItems = supportedTimeframes.map { it.title },
            tabContent = {
                Column {
                    Spacer(modifier = Modifier.height(PaddingExtraLarge))
                    SalesHistoryLineChart(
                        hideChartContent = timeframe != Timeframe.WEEK,
                        xAxisInitialIndex = 1,
                        xAxisSpacing = 7,
                        xAxisTextValues = salesByCity.flatMap { it.entries }
                            .map { it.date.formattedDate(withTime = false) },
                        lineMarks = salesByCity.toLineMarks(),
                        yAxisTickCount = 4,
                        totalSales = totalSales
                    )
                }
            },
            initialPage = supportedTimeframes.indexOf(timeframe),
            onTabSelected = {
                timeframe = supportedTimeframes[it]
            }
        )
    }
}


@SingleDevicePreview
@Composable
fun TopDonutSalesChart_Preview() {
    PreviewSurface {
        SalesHistoryView(
            model = FoodTruckViewModel.preview,
            onBackPressed = {},
            previousViewTitle = "Food Truck",
            currentViewTitle = "Sales History"
        )
    }
}