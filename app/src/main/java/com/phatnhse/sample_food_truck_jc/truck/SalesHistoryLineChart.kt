package com.phatnhse.sample_food_truck_jc.truck

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.phatnhse.sample_food_truck_jc.foodtruck.city.City
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.chartColorBlue
import com.phatnhse.sample_food_truck_jc.ui.theme.chartColorGreen
import com.phatnhse.sample_food_truck_jc.ui.theme.chartColorOrange
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview
import java.time.LocalDateTime

data class SalesByCity(
    val city: City,
    val entries: List<Entry>
) {
    val id: String get() = city.id

    data class Entry(
        val date: LocalDateTime,
        val sales: Int
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
    val indicatorType: IndicatorType,
    val indicatorSize: Dp = 3.dp,
    val indicatorBorderSize: Dp = 1.dp,
    val indicatorText: String
)

@OptIn(ExperimentalTextApi::class)
@Composable
fun SalesHistoryLineChart(
    lineMarks: List<LineMark>,
    yAxisTextValues: List<Int>,
    xAxisTextValues: List<String>,
    hideChartContent: Boolean = false
) {
    val textMeasurer = rememberTextMeasurer()
    val textPadding = PaddingLarge
    val gridLineColor = colorScheme.onBackground.copy(alpha = 0.5F)
    val textStyle = typography.labelSmall.copy(
        color = gridLineColor, fontWeight = FontWeight.Normal
    )

    val upperBoundValue = yAxisTextValues.max()
    val tickHeight = yAxisTextValues.size
    val indicatorCount = lineMarks.first().values.size

    val totalSales = 100

    Column {
        Text(text = "Total Sales", style = typography.titleSmall)
        Text(text = "$totalSales donuts", style = typography.titleMedium)
        Spacer(modifier = Modifier.height(PaddingLarge))

        Text(text = "Hide chart content $hideChartContent", style = typography.titleSmall)

        Box(
            Modifier
                .height(300.dp)
                .background(color = colorScheme.background)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val topPadding = textPadding.toPx()
                val totalHeight = size.height - 2 * topPadding
                val totalWidth = size.width - topPadding * 2
                val tickWidth = (totalWidth) / (indicatorCount - 1)
                val defaultStroke = Stroke()

                this.drawLineMarks(
                    textMeasurer = textMeasurer,
                    textStyle = textStyle,
                    lineMarks = lineMarks,
                    chartHeight = totalHeight,
                    tickWidth = tickWidth,
                    upperBoundValue = upperBoundValue,
                    padding = topPadding
                )

                // draw y axis grid
                val xGridLines: MutableList<Pair<Offset, Offset>> = mutableListOf()
                val yGridLines: MutableList<Pair<Offset, Offset>> = mutableListOf()

                // draw x axis grid
                val heightPerXGrid = totalHeight / (tickHeight - 1)
                (0 until tickHeight).forEach { index ->
                    val startingPoint = Offset(0f, topPadding + heightPerXGrid * index)
                    val endingPoint = Offset(totalWidth, topPadding + heightPerXGrid * index)
                    xGridLines.add(
                        startingPoint to endingPoint
                    )

                    drawText(
                        textMeasurer = textMeasurer,
                        text = yAxisTextValues[index].toString(),
                        topLeft = endingPoint.copy(
                            x = endingPoint.x + topPadding / 2, y = endingPoint.y - topPadding / 2
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
                val middleX = (indicatorCount / 2 - 1) * tickWidth
                val middleY = topPadding + totalHeight + topPadding

                val endX = totalWidth - tickWidth

                yGridLines.add(
                    Offset(middleX, topPadding) to Offset(middleX, middleY)
                )
                yGridLines.add(
                    Offset(endX, topPadding) to Offset(endX, topPadding + totalHeight)
                )

                val yGridPath = Path().apply {
                    yGridLines.forEachIndexed { index, (startingPoint, endingPoint) ->
                        moveTo(startingPoint.x, startingPoint.y)
                        lineTo(endingPoint.x, endingPoint.y)

                        if (index < xAxisTextValues.size - 1) {
                            drawText(
                                textMeasurer = textMeasurer,
                                text = xAxisTextValues[index],
                                topLeft = endingPoint.copy(
                                    x = endingPoint.x + topPadding / 2,
                                    y = endingPoint.y - topPadding
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
    padding: Float
) {
    var xAxisCurIndicator = padding / 2

    lineMarks.forEach { lineMark ->
        val yAxisOffset = lineMark.values.map { value ->
            chartHeight - value.toFloat() / upperBoundValue * chartHeight + padding
        }

        val xyAxisIndicators = mutableListOf<Offset>()
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
            path = path, color = lineMark.lineColor, style = stroke
        )

        // draw indicators
        xyAxisIndicators.forEach { centerOffset ->
            drawIndicator(
                indicator = lineMark.indicatorType,
                borderColor = lineMark.lineColor,
                solidColor = lineMark.indicatorSolidColor,
                indicatorSize = indicatorSize,
                centerOffset = centerOffset,
                stroke = stroke,
                filled = false
            )
        }

        drawIndicator(
            indicator = lineMark.indicatorType,
            borderColor = lineMark.lineColor,
            solidColor = lineMark.indicatorSolidColor,
            indicatorSize = indicatorSize,
            centerOffset = Offset(xAxisCurIndicator, padding / 2),
            stroke = stroke,
            filled = true
        )

        xAxisCurIndicator += padding / 2

        val paint = androidx.compose.ui.graphics.Paint().asFrameworkPaint().apply {
            textSize = 8.sp.toPx()
            // set other text style properties if needed
        }
        val textWidth = paint.measureText(lineMark.indicatorText)

        drawText(
            textMeasurer = textMeasurer,
            text = lineMark.indicatorText,
            topLeft = Offset(xAxisCurIndicator, (padding - 11.sp.toPx()) / 2),
            style = textStyle.copy(
                fontSize = 8.sp
            )
        )

        xAxisCurIndicator += textWidth + padding
    }
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
                    color = borderColor,
                    radius = indicatorSize / 2,
                    center = centerOffset
                )
            } else {
                drawCircle(
                    color = borderColor,
                    radius = indicatorSize / 2,
                    center = centerOffset,
                    style = stroke
                )

                drawCircle(
                    color = solidColor,
                    radius = indicatorSize / 2,
                    center = centerOffset
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
            yAxisTextValues = listOf(400, 300, 200, 100),
            xAxisTextValues = listOf("Hello world", "Hello world 1"),
            lineMarks = listOf(
                LineMark(
                    values = listOf(120, 80, 32, 56, 23, 160, 80, 90, 40, 56, 23, 160),
                    indicatorType = IndicatorType.SQUARE,
                    indicatorBorderSize = 2.dp,
                    indicatorSize = 4.dp,
                    lineColor = chartColorBlue,
                    indicatorSolidColor = colorScheme.background,
                    indicatorText = "London"
                ), LineMark(
                    values = listOf(160, 120, 180, 78, 99, 112, 30, 16, 204, 240, 78, 99),
                    indicatorType = IndicatorType.TRIANGLE,
                    indicatorBorderSize = 2.dp,
                    indicatorSize = 4.dp,
                    lineColor = chartColorGreen,
                    indicatorSolidColor = colorScheme.background,
                    indicatorText = "San Francisco"
                ), LineMark(
                    values = listOf(384, 320, 240, 280, 400, 281, 210, 300, 270, 400, 312, 300),
                    indicatorType = IndicatorType.CIRCLE,
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