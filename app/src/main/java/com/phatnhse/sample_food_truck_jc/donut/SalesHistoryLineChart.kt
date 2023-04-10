package com.phatnhse.sample_food_truck_jc.donut

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutSales
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingSmall
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview

@Composable
fun SalesHistoryLineChart(
    sales: List<DonutSales>
) {
    val totalSales = sales.sumOf { it.sales }
    val sortedSales = sales.sorted().take(5)
    val yValueCount = 4

    val yTicks = generateSimpleYValues(
        lowerBound = 0,
        upperBound = sortedSales.maxOf { it.sales },
        count = yValueCount
    )

    val (yMaxTick, yValues) = yTicks

    Column(
        Modifier.padding(PaddingNormal)
    ) {
        Text(text = "Total Sales", style = typography.titleSmall)
        Text(text = "$totalSales donuts", style = typography.titleMedium)
        Spacer(modifier = Modifier.height(PaddingLarge))
        DonutLineChart(
            Modifier
                .height(320.dp),
            donutCount = sortedSales.size,
            yValueCount = yValueCount,
            donutBar = {
                val offset = (sortedSales[it].sales.toFloat() / yMaxTick)
                DonutBar(
                    modifier = Modifier.bar(fraction = offset), fraction = offset
                )
            },
            donutView = {
                DonutFooter(donut = sortedSales[it].donut)
            },
            yAxisGridLine = {
                YAxisGridLine(text = yValues.asReversed()[it].toString())
            },
            xAxisValueText = {
                XAxisValueText(text = sortedSales[it].sales.toString())
            }
        )
    }
}

@Composable
fun DonutSaleLine(
    modifier: Modifier = Modifier,
    sales: List<Int> = listOf(
        20, 40, 20, 40, 20, 20, 40, 20, 40, 20, 20, 40, 20, 40, 20
    ),
    maxValue: Int,
    graphColor: Color = Color.Green
) {
    Box {
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            val spacePerHour = (size.width) / (sales.size - 1)
            val xAxisIndicators = mutableListOf<Float>()
            val yAxisIndicators = mutableListOf<Float>()

            val yAxisValues = sales.map {
                size.height - it.toFloat() / maxValue * size.height
            }

            val strokePath = Path().apply {
                for (i in sales.indices) {
                    val currentX = i * spacePerHour
                    if (i == 0) {
                        moveTo(currentX, yAxisValues[i])
                    } else {
                        lineTo(
                            currentX,
                            yAxisValues[i]
                        )
                    }

                    xAxisIndicators.add(currentX)
                    yAxisIndicators.add(yAxisValues[i])
                }
            }

            drawPath(
                path = strokePath,
                color = graphColor,
                style = Stroke(
                    width = 2.dp.toPx()
                )
            )

            (xAxisIndicators.indices).forEach {
                drawCircle(
                    Color.Black,
                    radius = 3.dp.toPx(),
                    center = Offset(xAxisIndicators[it], yAxisIndicators[it])
                )
            }
        }
    }
}

@Composable
fun DonutLineChart(
    modifier: Modifier = Modifier,
    donutCount: Int,
    yValueCount: Int,
    xAxisValueText: @Composable (index: Int) -> Unit,
    yAxisGridLine: @Composable (index: Int) -> Unit,
    donutView: @Composable (index: Int) -> Unit,
    donutBar: @Composable BarScope.(index: Int) -> Unit
) {
    val donutBars = @Composable { repeat(donutCount) { BarScope.donutBar(it) } }
    val donuts = @Composable { repeat(donutCount) { donutView(it) } }
    val xAxisValues = @Composable { repeat(donutCount) { xAxisValueText(it) } }
    val saleNumbers = @Composable { repeat(yValueCount) { yAxisGridLine(it) } }

    Layout(
        modifier = modifier,
        contents = listOf(donutBars, donuts, xAxisValues, saleNumbers)
    ) { (barMeasurables, donutMeasurables, xAxisValueMeasurables, yAxisLineMeasurables), constraints ->
        val totalWidth = constraints.maxWidth
        val totalHeight = constraints.maxHeight

        val barPaddingBetweenItems = PaddingLarge.roundToPx()
        val barPaddingEnd = PaddingLarge.roundToPx()
        val totalBarWidth = totalWidth - barPaddingEnd - barPaddingBetweenItems * (donutCount + 1)
        val barWidth = totalBarWidth / donutCount

        val xValueTextPadding = PaddingSmall.roundToPx()

        val donutPlaceables = donutMeasurables.map { measurable ->
            measurable.measure(
                Constraints.fixedWidth(barWidth)
            )
        }

        val yGridLine = yAxisLineMeasurables.map {
            it.measure(constraints.copy(minHeight = 10.sp.roundToPx()))
        }

        val donutViewHeight = donutPlaceables.first().height
        val yGridLineHeight = yGridLine.first().height

        val barPlaceables = barMeasurables.map { measurable ->
            measurable.measure(
                constraints.copy(
                    minWidth = barWidth,
                    maxWidth = barWidth,
                    minHeight = totalHeight - donutViewHeight - yGridLineHeight / 2,
                    maxHeight = totalHeight - donutViewHeight - yGridLineHeight / 2
                )
            )
        }

        val xAxisValuePlaceables = xAxisValueMeasurables.map { measurable ->
            measurable.measure(Constraints.fixedWidth(barWidth))
        }

        layout(totalWidth, totalHeight) {
            val yGridLineOffset =
                (totalHeight - donutViewHeight - yValueCount * yGridLineHeight) / (yValueCount - 1)

            var xPosition = 0
            var yPosition = 0

            yGridLine.forEach { placeable ->
                placeable.place(x = 0, y = yPosition)
                yPosition += yGridLineOffset + yGridLineHeight
            }

            barPlaceables.forEachIndexed { index, barPlaceable ->
                val barOffset = (barPlaceable.parentData as BarData).fraction
                val barHeight = (barPlaceable.height * barOffset).toInt()

                barPlaceable.place(
                    x = xPosition + barPaddingBetweenItems,
                    y = 0
                )
                donutPlaceables[index].place(
                    x = xPosition + barPaddingBetweenItems,
                    y = totalHeight - donutViewHeight
                )

                xAxisValuePlaceables[index].let {
                    it.place(
                        x = xPosition + barPaddingBetweenItems,
                        y = totalHeight - donutViewHeight - barHeight - it.height - xValueTextPadding - yGridLineHeight / 2
                    )
                }

                xPosition += barPaddingBetweenItems + barPlaceable.width
            }
        }
    }
}

@SingleDevicePreview
@Composable
fun SalesHistoryLineChart_Preview() {
    PreviewSurface {
//        SalesHistoryLineChart(
//            sales = DonutSales.preview
//        )

        DonutSaleLine(
            graphColor = colorScheme.primary,
            maxValue = 100
        )
    }
}