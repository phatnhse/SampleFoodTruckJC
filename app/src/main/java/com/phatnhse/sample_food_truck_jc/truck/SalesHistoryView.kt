package com.phatnhse.sample_food_truck_jc.truck

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.foodtruck.model.FoodTruckViewModel
import com.phatnhse.sample_food_truck_jc.foodtruck.model.Timeframe
import com.phatnhse.sample_food_truck_jc.navigation.NavigationHeader
import com.phatnhse.sample_food_truck_jc.order.formattedDate
import com.phatnhse.sample_food_truck_jc.ui.composable.TabLayout
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingExtraLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.chartColorBlue
import com.phatnhse.sample_food_truck_jc.ui.theme.chartColorGreen
import com.phatnhse.sample_food_truck_jc.ui.theme.chartColorOrange
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview

@Composable
fun SalesHistoryView(
    previousViewTitle: String = "Food Truck",
    currentViewTitle: String = "Sales History",
    onBackPressed: () -> Unit,
    model: FoodTruckViewModel
) {
    var tabIndex by remember { mutableStateOf(1) }
    val timeframe = when (tabIndex) {
        0 -> Timeframe.WEEK
        1 -> Timeframe.MONTH
        2 -> Timeframe.YEAR
        else -> throw UnsupportedOperationException("Unsupported")
    }
    val hideChartContent = timeframe != Timeframe.WEEK
    val tabTitles = listOf("2 Weeks", "Months", "Year")

    val salesByCity = model.getSalesByCity(timeframe = timeframe)

    val totalSales by remember(salesByCity) {
        derivedStateOf {
            salesByCity.flatMap { it.entries }
                .reduce { acc, entry ->
                    acc.copy(
                        sales = acc.sales + entry.sales
                    )
                }
        }
    }

    val dates by remember(salesByCity) {
        derivedStateOf {
            salesByCity.flatMap { it.entries }
                .map { it.date }
                .map {
                    it.formattedDate(
                        pattern = "yyyy-MM-dd",
                        withTime = false
                    )
                }.distinct()
        }
    }


    Column(
        modifier = Modifier
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
            titles = tabTitles,
            tabContent = {
                Column {
                    Spacer(modifier = Modifier.height(PaddingExtraLarge))
                    SalesHistoryLineChart(
                        Modifier.padding(PaddingNormal),
                        totalSales = totalSales.sales,
                        hideChartContent = hideChartContent,
                        yAxisTickCount = 4,
                        xAxisTextValues = dates,
                        xAxisInitialIndex = 2,
                        xAxisSpacing = when (timeframe) {
                            Timeframe.WEEK -> 8 // 8 days
                            Timeframe.MONTH -> 7 // 7 days
                            Timeframe.YEAR -> 4 // 4 months
                            else -> 0
                        },
                        lineMarks = listOf(
                            LineMark(
                                values = salesByCity[0].entries.map { it.sales },
                                indicatorType = IndicatorType.CIRCLE,
                                indicatorBorderSize = 2.dp,
                                indicatorSize = 6.dp,
                                lineColor = chartColorBlue,
                                indicatorSolidColor = MaterialTheme.colorScheme.background,
                                indicatorText = salesByCity[0].city.name
                            ),
                            LineMark(
                                values = salesByCity[1].entries.map { it.sales },
                                indicatorType = IndicatorType.SQUARE,
                                indicatorBorderSize = 2.dp,
                                indicatorSize = 6.dp,
                                lineColor = chartColorGreen,
                                indicatorSolidColor = MaterialTheme.colorScheme.background,
                                indicatorText = salesByCity[1].city.name
                            ), LineMark(
                                values = salesByCity[2].entries.map { it.sales },
                                indicatorType = IndicatorType.TRIANGLE,
                                indicatorBorderSize = 2.dp,
                                indicatorSize = 6.dp,
                                lineColor = chartColorOrange,
                                indicatorSolidColor = MaterialTheme.colorScheme.background,
                                indicatorText = salesByCity[2].city.name
                            )
                        )
                    )
                }
            },
            defaultSelected = tabIndex,
            onTabSelected = {
                tabIndex = it
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