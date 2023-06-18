package com.phatnhse.sample_food_truck_jc.truck

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phatnhse.sample_food_truck_jc.donut.generateYAxisValues
import com.phatnhse.sample_food_truck_jc.foodtruck.city.City
import com.phatnhse.sample_food_truck_jc.foodtruck.general.lockPainter
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeSmaller
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.chartColorBlue
import com.phatnhse.sample_food_truck_jc.ui.theme.chartColorGreen
import com.phatnhse.sample_food_truck_jc.ui.theme.chartColorOrange
import com.phatnhse.sample_food_truck_jc.ui.theme.onBackgroundSecondary
import com.phatnhse.sample_food_truck_jc.ui.theme.withOpacity
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview
import java.time.LocalDateTime

data class SalesByCity(
    val city: City, val entries: List<Entry>
) {
    val id: String get() = city.id

    data class Entry(
        val date: LocalDateTime, val sales: Int
    ) {
        val id: LocalDateTime get() = date
    }
}

enum class IndicatorType {
    CIRCLE, SQUARE, TRIANGLE;
}

data class LineMark(
    val values: List<Int>,
    val lineColor: Color,
    val indicatorSolidColor: Color,
    val indicatorShape: IndicatorType,
    val indicatorSize: Dp = 3.dp,
    val indicatorBorderSize: Dp = 2.dp,
    val indicatorText: String
)

@Composable
fun List<SalesByCity>.toLineMarks(): List<LineMark> {
    return mapIndexed { index, salesByCity ->
        val (shape, color) = when (index) {
            0 -> IndicatorType.CIRCLE to chartColorBlue
            1 -> IndicatorType.SQUARE to chartColorGreen
            2 -> IndicatorType.TRIANGLE to chartColorOrange
            else -> throw UnsupportedOperationException("Not supported chart with more than 3 columns")
        }
        LineMark(
            values = salesByCity.entries.map { it.sales },
            indicatorShape = shape,
            indicatorBorderSize = 2.dp,
            indicatorSize = 4.dp,
            lineColor = color,
            indicatorSolidColor = colorScheme.background,
            indicatorText = "London"
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun SalesHistoryLineChart(
    modifier: Modifier = Modifier,
    totalSales: Int,
    yAxisTickCount: Int,
    xAxisInitialIndex: Int,
    xAxisSpacing: Int,
    lineMarks: List<LineMark>,
    xAxisTextValues: List<String>,
    hideChartContent: Boolean = false
) {
    val textMeasurer = rememberTextMeasurer()
    val textPadding = PaddingLarge
    val gridLineColor = colorScheme.onBackgroundSecondary()
    val textStyle =
        typography.labelSmall.copy(color = gridLineColor, fontWeight = FontWeight.Normal)

    val allValues = lineMarks.flatMap { it.values }
    val (yUpperbound, yAxisValues) = generateYAxisValues(
        lowerBound = 100,
        upperBound = allValues.max(),
        count = yAxisTickCount
    )

    val indicatorCount = lineMarks.first().values.size
    val hideChartContentBg = colorScheme.primary.withOpacity(0.25f)

    Column(modifier) {
        Text(
            text = "Total Sales",
            style = typography.titleSmall,
            color = colorScheme.onBackgroundSecondary()
        )
        Text(text = "$totalSales donuts", style = typography.titleMedium)
        Spacer(modifier = Modifier.height(PaddingNormal))
        Box(
            Modifier
                .height(300.dp)
                .clipToBounds(),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val padding = textPadding.toPx()
                val totalHeight = size.height - 2 * padding
                val totalWidth = size.width - 2 * padding
                val tickWidth = (totalWidth) / (indicatorCount - 1)
                val defaultStroke = Stroke()

                if (hideChartContent) {
                    drawRect(
                        color = hideChartContentBg,
                        topLeft = Offset(0f, padding),
                        size = Size(totalWidth, totalHeight)

                    )
                }

                val xyAxis = this.drawLineMarks(
                    textMeasurer = textMeasurer,
                    textStyle = textStyle,
                    lineMarks = lineMarks,
                    chartHeight = totalHeight,
                    tickWidth = tickWidth,
                    upperBoundValue = yUpperbound,
                    padding = padding,
                    opacity = if (hideChartContent) 0f else 1f
                )

                // draw y axis grid
                val xGridLines: MutableList<Pair<Offset, Offset>> = mutableListOf()

                // draw x axis grid
                val heightPerXGrid = totalHeight / (yAxisTickCount - 1)
                (0 until yAxisTickCount).forEach { index ->
                    val startingPoint = Offset(0f, padding + heightPerXGrid * index)
                    val endingPoint = Offset(totalWidth, padding + heightPerXGrid * index)
                    xGridLines.add(
                        startingPoint to endingPoint
                    )

                    drawText(
                        textMeasurer = textMeasurer,
                        text = yAxisValues.asReversed()[index].toString(),
                        topLeft = endingPoint.copy(
                            x = endingPoint.x + padding / 2, y = endingPoint.y - padding / 2
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
                    path = xGridPath, color = gridLineColor, style = defaultStroke
                )

                // draw y grid - dotted lines
                val yGridLines: MutableList<Triple<Offset, Offset, Int>> = mutableListOf()
                var curIndex = xAxisInitialIndex
                while (curIndex < xyAxis.size) {
                    val startingPoint = Offset(xyAxis[curIndex].x, padding)
                    val endingPoint = Offset(xyAxis[curIndex].x, padding * 2 + totalHeight)
                    yGridLines.add(Triple(startingPoint, endingPoint, curIndex))
                    curIndex += xAxisSpacing
                }

                val yGridPath = Path().apply {
                    yGridLines.forEachIndexed { index, (startingPoint, endingPoint, xAxisIndex) ->
                        moveTo(startingPoint.x, startingPoint.y)
                        if (xAxisIndex == xAxisInitialIndex) {
                            lineTo(endingPoint.x, endingPoint.y)
                        } else {
                            lineTo(endingPoint.x, endingPoint.y - padding)
                        }

                        if (index != yGridLines.lastIndex) {
                            drawText(
                                textMeasurer = textMeasurer,
                                text = xAxisTextValues[xAxisIndex],
                                topLeft = endingPoint.copy(
                                    x = endingPoint.x + padding / 2,
                                    y = endingPoint.y - padding
                                ),
                                style = textStyle
                            )
                        }
                    }
                }

                val dottedLine = Stroke(
                    width = 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
                )
                drawPath(
                    path = yGridPath, color = gridLineColor, style = dottedLine
                )
            }
            if (hideChartContent) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.background,
                        contentColor = colorScheme.onBackground
                    ),
                    contentPadding = PaddingValues(15.dp),
                    onClick = { }
                ) {
                    Row {
                        Image(
                            modifier = Modifier.size(IconSizeSmaller),
                            painter = lockPainter(),
                            contentDescription = "Lock",
                            colorFilter = ColorFilter.tint(
                                color = colorScheme.onBackground.copy(
                                    alpha = 0.5F
                                )
                            )
                        )
                        Spacer(modifier = Modifier.width(PaddingNormal))
                        Text(
                            text = "Premium Feature", style = typography.titleSmall.copy(
                                fontWeight = FontWeight.Normal,
                                color = colorScheme.onBackground.copy(
                                    alpha = 0.5F
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
private fun DrawScope.drawLineMarks(
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    lineMarks: List<LineMark>,
    upperBoundValue: Int,
    chartHeight: Float,
    tickWidth: Float,
    padding: Float,
    opacity: Float
): List<Offset> {
    var xAxisCurIndicator = padding / 2
    val xyAxisIndicators = mutableListOf<Offset>()

    lineMarks.forEach { lineMark ->
        xyAxisIndicators.clear()
        val yAxisOffset = lineMark.values.map { value ->
            chartHeight - value.toFloat() / upperBoundValue * chartHeight + padding
        }

        val path = Path().apply {
            for (i in lineMark.values.indices) {
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

        // draw lines
        val indicatorSize = lineMark.indicatorSize.toPx()
        val indicatorBorderSize = lineMark.indicatorBorderSize.toPx()
        val stroke = Stroke(
            width = indicatorBorderSize
        )
        drawPath(
            path = path, color = lineMark.lineColor.copy(alpha = opacity), style = stroke
        )

        // draw indicators
        xyAxisIndicators.forEach { centerOffset ->
            drawIndicator(
                indicator = lineMark.indicatorShape,
                borderColor = lineMark.lineColor.copy(alpha = opacity),
                solidColor = lineMark.indicatorSolidColor.copy(alpha = opacity),
                indicatorSize = indicatorSize,
                centerOffset = centerOffset,
                stroke = stroke,
                filled = false
            )
        }

        drawIndicator(
            indicator = lineMark.indicatorShape,
            borderColor = lineMark.lineColor,
            solidColor = lineMark.indicatorSolidColor,
            indicatorSize = indicatorSize,
            centerOffset = Offset(xAxisCurIndicator, padding / 2),
            stroke = stroke,
            filled = true
        )

        xAxisCurIndicator += padding / 2

        val paint = Paint().asFrameworkPaint().apply {
            textSize = 8.sp.toPx()
            // set other text style properties if needed
        }
        val textWidth = paint.measureText(lineMark.indicatorText)

        drawText(
            textMeasurer = textMeasurer,
            text = lineMark.indicatorText,
            topLeft = Offset(xAxisCurIndicator, (padding - 14.sp.toPx()) / 2),
            style = textStyle.copy(
                fontSize = 11.sp
            )
        )

        xAxisCurIndicator += textWidth + padding * 2
    }

    return xyAxisIndicators
}

fun DrawScope.drawIndicator(
    indicator: IndicatorType,
    borderColor: Color,
    solidColor: Color,
    indicatorSize: Float,
    centerOffset: Offset,
    stroke: Stroke,
    filled: Boolean
) {
    when (indicator) {
        IndicatorType.CIRCLE -> {
            if (filled) {
                drawCircle(
                    color = borderColor, radius = indicatorSize / 2, center = centerOffset
                )
            } else {
                drawCircle(
                    color = borderColor,
                    radius = indicatorSize / 2,
                    center = centerOffset,
                    style = stroke
                )

                drawCircle(
                    color = solidColor, radius = indicatorSize / 2, center = centerOffset
                )
            }
        }

        IndicatorType.SQUARE -> {
            if (filled) {
                drawRect(
                    color = borderColor,
                    topLeft = centerOffset.copy(
                        x = centerOffset.x - indicatorSize / 2,
                        y = centerOffset.y - indicatorSize / 2
                    ),
                    size = Size(indicatorSize, indicatorSize),
                )
            } else {
                drawRect(
                    color = borderColor, topLeft = centerOffset.copy(
                        x = centerOffset.x - indicatorSize / 2,
                        y = centerOffset.y - indicatorSize / 2
                    ), size = Size(indicatorSize, indicatorSize), style = stroke
                )

                drawRect(
                    color = solidColor,
                    topLeft = centerOffset.copy(
                        x = centerOffset.x - indicatorSize / 2,
                        y = centerOffset.y - indicatorSize / 2
                    ),
                    size = Size(indicatorSize, indicatorSize),
                )
            }
        }

        IndicatorType.TRIANGLE -> {
            val trianglePath = Path()

            // Define the three points of the triangle
            val point1 = Offset(x = centerOffset.x, y = centerOffset.y - indicatorSize / 2)
            val point2 = Offset(
                x = centerOffset.x - indicatorSize / 2, y = centerOffset.y + indicatorSize / 2
            )
            val point3 = Offset(
                x = centerOffset.x + indicatorSize / 2, y = centerOffset.y + indicatorSize / 2
            )

            // Draw the triangle path
            trianglePath.moveTo(point1.x, point1.y)
            trianglePath.lineTo(point2.x, point2.y)
            trianglePath.lineTo(point3.x, point3.y)
            trianglePath.close()

            // Draw the triangle on the canvas
            if (filled) {
                drawPath(
                    path = trianglePath, color = borderColor
                )
            } else {
                drawPath(
                    path = trianglePath, color = borderColor, style = stroke
                )

                drawPath(
                    path = trianglePath, color = solidColor
                )
            }
        }
    }
}

@SingleDevicePreview
@Composable
fun SalesHistoryLineChart_Preview() {
    PreviewSurface {
        SalesHistoryLineChart(
            hideChartContent = false,
            yAxisTickCount = 4,
            xAxisTextValues = listOf("a", "b", "c", "d", "e", "f", "g", "h", "z", "x", "y", "m"),
            totalSales = 100,
            xAxisInitialIndex = 1,
            xAxisSpacing = 4,
            lineMarks = listOf(
                LineMark(
                    values = listOf(120, 80, 32, 56, 23, 160, 80, 90, 40, 56, 23, 160),
                    indicatorShape = IndicatorType.SQUARE,
                    indicatorBorderSize = 2.dp,
                    indicatorSize = 4.dp,
                    lineColor = chartColorGreen,
                    indicatorSolidColor = colorScheme.background,
                    indicatorText = "London"
                ), LineMark(
                    values = listOf(160, 120, 180, 78, 99, 112, 30, 16, 204, 240, 78, 99),
                    indicatorShape = IndicatorType.TRIANGLE,
                    indicatorBorderSize = 2.dp,
                    indicatorSize = 4.dp,
                    lineColor = chartColorBlue,
                    indicatorSolidColor = colorScheme.background,
                    indicatorText = "San Francisco"
                ), LineMark(
                    values = listOf(384, 320, 240, 280, 400, 281, 210, 300, 270, 400, 312, 300),
                    indicatorShape = IndicatorType.CIRCLE,
                    indicatorBorderSize = 2.dp,
                    indicatorSize = 4.dp,
                    lineColor = chartColorOrange,
                    indicatorSolidColor = colorScheme.background,
                    indicatorText = "Cupertino"
                )
            )
        )
    }
}