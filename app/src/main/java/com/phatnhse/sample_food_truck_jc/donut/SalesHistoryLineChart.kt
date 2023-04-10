package com.phatnhse.sample_food_truck_jc.donut

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
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
        lowerBound = 0, upperBound = sortedSales.maxOf { it.sales }, count = yValueCount
    )

    val (yMaxTick, yValues) = yTicks

    Column(
        Modifier.padding(PaddingNormal)
    ) {
        Text(text = "Total Sales", style = typography.titleSmall)
        Text(text = "$totalSales donuts", style = typography.titleMedium)
        Spacer(modifier = Modifier.height(PaddingLarge))
        DonutLineChart(Modifier.height(320.dp),
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
            })
    }
}

enum class IndicatorType {
    CIRCLE, SQUARE, TRIANGLE
}

data class LineMark(
    val values: List<Int>,
    val color: Color,
    val indicatorColor: Color,
    val indicatorType: IndicatorType
)

@OptIn(ExperimentalTextApi::class)
@Composable
fun DonutSaleLine(
    lineMarks: List<LineMark>,
    yTextValues: List<Int>,
    xTextValues: List<String>,
    hideChartContent: Boolean = false
) {
    val upperbound = yTextValues.max()
    val tickHeight = yTextValues.size

    val textMeasurer = rememberTextMeasurer()
    val textPadding = PaddingLarge
    val textStyle = typography.labelSmall.copy(
        color = colorScheme.onBackground.copy(alpha = 0.5F), fontWeight = FontWeight.Normal
    )

    val countIndicator = lineMarks.first().values.size

    Box(
        Modifier.height(300.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val padding = textPadding.toPx()
            val totalHeight = size.height - 2 * padding
            val totalWidth = size.width - padding * 2

            val tickWidth = (totalWidth) / (countIndicator - 1)
            lineMarks.forEach { line ->
                val yAxisOffset = line.values.map { value ->
                    totalHeight - value.toFloat() / upperbound * totalHeight + padding
                }

                val xyAxisIndicators = mutableListOf<Offset>()
                val strokePath = Path().apply {
                    for (i in line.values.indices) {
                        val currentX = i * tickWidth
                        if (i == 0) {
                            moveTo(currentX, yAxisOffset[i])
                        } else {
                            val previousX = (i - 1) * tickWidth

                            val conX1 = previousX + tickWidth / 4f
                            val conY1 = yAxisOffset[i - 1]

                            val conX2 = currentX - tickWidth / 4f
                            val conY2 = yAxisOffset[i]

                            cubicTo(
                                x1 = conX1,
                                y1 = conY1,
                                x2 = conX2,
                                y2 = conY2,
                                x3 = currentX,
                                y3 = yAxisOffset[i]
                            )
                        }

                        xyAxisIndicators.add(Offset(currentX, yAxisOffset[i]))
                    }
                }

                // draw line chart
                drawPath(
                    path = strokePath, color = line.color, style = Stroke(
                        width = 1.dp.toPx()
                    )
                )

                // draw indicators
                xyAxisIndicators.forEach {
                    when (line.indicatorType) {
                        IndicatorType.CIRCLE -> {
                            drawCircle(
                                line.indicatorColor, radius = 4.dp.toPx(), center = it
                            )
                        }

                        IndicatorType.SQUARE -> {
                            drawRect(
                                color = line.indicatorColor, topLeft = it.copy(
                                    x = it.x - 4.dp.toPx(), y = it.y - 4.dp.toPx()
                                ), size = Size(8.dp.toPx(), 8.dp.toPx())
                            )
                        }

                        IndicatorType.TRIANGLE -> {
                            val path = Path()
                            val width = 8.dp.toPx()
                            val height = 8.dp.toPx()

                            val topLeft = it.copy(
                                x = it.x - 4.dp.toPx(), y = it.y - 4.dp.toPx()
                            )

                            // Define the three points of the triangle
                            val point1 = Offset(x = topLeft.x + width / 2, y = topLeft.y)
                            val point2 = Offset(x = topLeft.x + width, y = topLeft.y + height)
                            val point3 = Offset(x = topLeft.x, y = topLeft.y)

                            // Draw the triangle path
                            path.moveTo(point1.x, point1.y)
                            path.lineTo(point2.x, point2.y)
                            path.lineTo(point3.x, point3.y)
                            path.close()

                            // Draw the triangle on the canvas
                            drawPath(
                                path = path, color = line.indicatorColor
                            )
                        }
                    }
                }
            }

            // draw y axis grid
            val xGridLines: MutableList<Pair<Offset, Offset>> = mutableListOf()
            val yGridLines: MutableList<Pair<Offset, Offset>> = mutableListOf()

            // draw x axis grid
            val heightPerXGrid = totalHeight / (tickHeight - 1)
            (0 until tickHeight).forEach { index ->
                val startingPoint = Offset(0f, padding + heightPerXGrid * index)
                val endingPoint = Offset(totalWidth, padding + heightPerXGrid * index)
                xGridLines.add(
                    startingPoint to endingPoint
                )

                drawText(
                    textMeasurer = textMeasurer,
                    text = yTextValues[index].toString(),
                    topLeft = endingPoint.copy(
                        x = endingPoint.x + textPadding.div(2).toPx(),
                        y = endingPoint.y - padding / 2
                    ),
                    style = textStyle
                )
            }

            val xGridPath = Path().apply {
                xGridLines.forEach { (startingPoint, endPoint) ->
                    moveTo(startingPoint.x, startingPoint.y)
                    lineTo(endPoint.x, endPoint.y)
                }
            }

            drawPath(
                path = xGridPath, color = Color.Black.copy(alpha = 0.5F), style = Stroke()
            )


            // draw y grid - dotted lines
            val middleX = (countIndicator / 2 - 1) * tickWidth
            val middleY = padding + totalHeight + textPadding.toPx()

            val endX = totalWidth - tickWidth

            yGridLines.add(
                Offset(middleX, padding) to Offset(middleX, middleY)
            )
            yGridLines.add(
                Offset(endX, padding) to Offset(endX, padding + totalHeight)
            )

            val yGridPath = Path().apply {
                yGridLines.forEachIndexed { index, (startingPoint, endingPoint) ->
                    moveTo(startingPoint.x, startingPoint.y)
                    lineTo(endingPoint.x, endingPoint.y)

                    if (index < xTextValues.size - 1) {
                        drawText(
                            textMeasurer = textMeasurer,
                            text = xTextValues[index],
                            topLeft = endingPoint.copy(
                                x = endingPoint.x + textPadding.div(2).toPx(),
                                y = endingPoint.y - textPadding.toPx()
                            ),
                            style = textStyle
                        )
                    }
                }
            }

            val stroke = Stroke(
                width = 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
            )
            drawPath(
                path = yGridPath, color = Color.Black.copy(alpha = 0.5F), style = stroke
            )
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
        modifier = modifier, contents = listOf(donutBars, donuts, xAxisValues, saleNumbers)
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
                    x = xPosition + barPaddingBetweenItems, y = 0
                )
                donutPlaceables[index].place(
                    x = xPosition + barPaddingBetweenItems, y = totalHeight - donutViewHeight
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
            yTextValues = listOf(400, 300, 200, 100),
            xTextValues = listOf("Hello world", "Hello world 1"),
            lineMarks = listOf(
                LineMark(
                    values = listOf(120, 80, 40, 56, 23, 160, 80, 90, 40, 56, 23, 160),
                    color = Color.Green,
                    indicatorColor = Color.Green,
                    indicatorType = IndicatorType.SQUARE
                ), LineMark(
                    values = listOf(160, 120, 40, 78, 99, 112, 30, 16, 204, 240, 78, 99),
                    color = Color.Red,
                    indicatorColor = Color.Red,
                    indicatorType = IndicatorType.TRIANGLE
                ), LineMark(
                    values = listOf(384, 320, 120, 120, 400, 281, 150, 300, 120, 400, 281, 150),
                    color = Color.Blue,
                    indicatorColor = Color.Blue,
                    indicatorType = IndicatorType.CIRCLE
                )
            )
        )
    }
}