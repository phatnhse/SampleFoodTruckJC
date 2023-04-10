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

@OptIn(ExperimentalTextApi::class)
@Composable
fun DonutSaleLine(
    modifier: Modifier = Modifier,
    sales: List<Int> = listOf(
        20, 30, 40, 30, 20, 10, 20, 30, 40, 30, 20, 10, 20
    ),
    maxValue: Int,
    xGridLineText: List<Int>,
    bottomText: String,
    graphColor: Color = Color.Green
) {
    require(sales.size > 2) {
        "The input array should have more than 2 items"
    }

    require(xGridLineText.size == 4) {
        "The grid is by default has 4 ticks"
    }

    val textMeasurer = rememberTextMeasurer()
    val textPadding = PaddingLarge
    val xGriLinesCount = xGridLineText.size
    val textStyle = typography.labelSmall.copy(
        color = colorScheme.onBackground.copy(alpha = 0.5F),
        fontWeight = FontWeight.Normal
    )

    Box(
        Modifier
            .height(300.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val totalHeight = size.height - 2 * textPadding.toPx()
            val totalWidth = size.width - textPadding.times(2).toPx()
            val yTopOffset = textPadding.toPx()

            val tickWidth = (totalWidth) / (sales.size - 1)
            val yAxisOffset = sales.map {
                totalHeight - it.toFloat() / maxValue * totalHeight
            }

            val xyAxisIndicators = mutableListOf<Offset>()

            val strokePath = Path().apply {
                for (i in sales.indices) {
                    val currentX = i * tickWidth
                    if (i == 0) {
                        moveTo(currentX, yAxisOffset[i])
                    } else {
                        val previousX = (i - 1) * tickWidth

                        val conX1 = (previousX + currentX) / 2f
                        val conX2 = (previousX + currentX) / 2f

                        val conY1 = yAxisOffset[i - 1]
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
                path = strokePath,
                color = graphColor,
                style = Stroke(
                    width = 2.dp.toPx()
                )
            )

            // draw indicators
            xyAxisIndicators.forEach {
                drawCircle(
                    Color.Black.copy(alpha = 0.5F),
                    radius = 3.dp.toPx(),
                    center = it
                )
            }

            // draw y axis grid
            val xGridLines: MutableList<Pair<Offset, Offset>> = mutableListOf()
            val yGridLines: MutableList<Pair<Offset, Offset>> = mutableListOf()

            // draw x axis grid
            val heightPerXGrid = totalHeight / (xGriLinesCount - 1)
            (0 until xGriLinesCount).forEach { index ->
                val startingPoint = Offset(0f, yTopOffset + heightPerXGrid * index)
                val endingPoint = Offset(totalWidth, yTopOffset + heightPerXGrid * index)
                xGridLines.add(
                    startingPoint to endingPoint
                )

                drawText(
                    textMeasurer = textMeasurer,
                    text = xGridLineText[index].toString(),
                    topLeft = endingPoint.copy(
                        x = endingPoint.x + textPadding.div(2).toPx(),
                        y = endingPoint.y - yTopOffset / 2
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
                path = xGridPath,
                color = Color.Black.copy(alpha = 0.5F),
                style = Stroke()
            )


            // draw y grid - dotted lines
            val yMiddlePoint = xyAxisIndicators[xyAxisIndicators.size / 2 - 1]
            val yEndPoint = xyAxisIndicators[xyAxisIndicators.lastIndex - 1]
            yGridLines.add(
                Offset(yMiddlePoint.x, yTopOffset) to
                        Offset(yMiddlePoint.x, yTopOffset + totalHeight + textPadding.toPx())
            )
            yGridLines.add(
                Offset(yEndPoint.x, yTopOffset) to Offset(yEndPoint.x, yTopOffset + totalHeight)
            )

            val yGridPath = Path().apply {
                yGridLines.forEachIndexed { index, (startingPoint, endingPoint) ->
                    moveTo(startingPoint.x, startingPoint.y)
                    lineTo(endingPoint.x, endingPoint.y)

                    if (index != yGridLines.lastIndex) {
                        drawText(
                            textMeasurer = textMeasurer,
                            text = bottomText,
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
                width = 2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
            )
            drawPath(
                path = yGridPath,
                color = Color.Black.copy(alpha = 0.5F),
                style = stroke
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
            maxValue = 100,
            bottomText = "Hello world",
            xGridLineText = listOf(400, 300, 200, 100)
        )
    }
}