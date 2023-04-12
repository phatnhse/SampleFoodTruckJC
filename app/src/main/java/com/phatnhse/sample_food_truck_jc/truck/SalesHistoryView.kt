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
import com.phatnhse.sample_food_truck_jc.ui.composable.TabLayout
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingExtraLarge
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
    var timeframe by remember { mutableStateOf(Timeframe.WEEK) }
    val tabTitles = listOf("2 Weeks", "Months", "Year")
    val hideChartContent by remember(timeframe) {
        derivedStateOf { timeframe != Timeframe.WEEK }
    }

    val salesByCity by remember(timeframe) {
        derivedStateOf {
            model.getSalesByCity(timeframe = timeframe)
        }
    }

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
            titles = tabTitles,
            tabContent = {
                Column {
                    Spacer(modifier = Modifier.height(PaddingExtraLarge))
                    SalesHistoryLineChart(
                        hideChartContent = hideChartContent,
                        yAxisTextValues = listOf(
                            400, 300, 200, 100
                        ),
                        xAxisTextValues = listOf("Hello world", "Hello world 1"),
                        lineMarks = listOf(
                            LineMark(
                                values = salesByCity[0].entries.map { it.sales },
                                indicatorType = IndicatorType.SQUARE,
                                indicatorBorderSize = 2.dp,
                                indicatorSize = 4.dp,
                                lineColor = chartColorBlue,
                                indicatorSolidColor = MaterialTheme.colorScheme.background,
                                indicatorText = "London"
                            ), LineMark(
                                values = listOf(
                                    160,
                                    120,
                                    180,
                                    78,
                                    99,
                                    112,
                                    30,
                                    16,
                                    204,
                                    240,
                                    78,
                                    99
                                ),
                                indicatorType = IndicatorType.TRIANGLE,
                                indicatorBorderSize = 2.dp,
                                indicatorSize = 4.dp,
                                lineColor = chartColorGreen,
                                indicatorSolidColor = MaterialTheme.colorScheme.background,
                                indicatorText = "San Francisco"
                            ), LineMark(
                                values = listOf(
                                    384,
                                    320,
                                    240,
                                    280,
                                    400,
                                    281,
                                    210,
                                    300,
                                    270,
                                    400,
                                    312,
                                    300
                                ),
                                indicatorType = IndicatorType.CIRCLE,
                                indicatorBorderSize = 2.dp,
                                indicatorSize = 4.dp,
                                lineColor = chartColorOrange,
                                indicatorSolidColor = MaterialTheme.colorScheme.background,
                                indicatorText = "Cupertino"
                            )
                        )
                    )
                }
            },
            defaultSelected = timeframe.ordinal,
            onTabSelected = {
                timeframe = Timeframe.values()[it]
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