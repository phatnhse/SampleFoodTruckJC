package com.phatnhse.sample_food_truck_jc.donut

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Donut
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutSales
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutView
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingSmall
import com.phatnhse.sample_food_truck_jc.ui.theme.ShapeRoundedLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.bottomBarColor
import com.phatnhse.sample_food_truck_jc.utils.MultipleDevices
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.roundToInt

fun roundUpToNearest(n: Int): Int {
    val numDigits = n.toString().length
    val multipleOfTen = 10.0.pow(numDigits - 1).toInt()
    return (ceil(n.toDouble() / multipleOfTen) * multipleOfTen).toInt()
}

fun generateSimpleYValues(
    lowerBound: Int,
    upperBound: Int,
    count: Int
): Pair<Int, List<Int>> {
    val maxY = roundUpToNearest(upperBound) * 3 / 2
    val interval = (maxY.toFloat() - lowerBound) / (count - 1)
    return maxY to List(count) {
        (interval * it).roundToInt()
    }
}

@Composable
fun TopDonutSalesChart(
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
        DonutChart(
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
fun DonutChart(
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


@Composable
fun XAxisValueText(text: String) {
    Box(
        Modifier
            .wrapContentSize(Alignment.TopCenter)
            .background(
                color = colorScheme.surfaceVariant, shape = ShapeRoundedLarge
            )
    ) {
        Text(
            modifier = Modifier.padding(horizontal = PaddingSmall, vertical = 2.dp),
            text = text,
            fontSize = 10.sp
        )
    }
}

@Composable
fun YAxisGridLine(
    text: String
) {
    Row(
        Modifier
            .wrapContentSize(Alignment.TopCenter),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            Modifier
                .weight(1F)
                .padding(end = PaddingSmall), thickness = 0.5.dp
        )
        Text(
            text = text,
            color = colorScheme.onBackground.copy(alpha = 0.5F),
            fontSize = 8.sp
        )
    }
}

@Composable
fun DonutFooter(donut: Donut) {
    Column(
        Modifier.wrapContentHeight()
    ) {
        DonutView(Modifier.padding(PaddingSmall), donut = donut)
        Text(
            donut.name,
            maxLines = 2,
            fontSize = 9.sp,
            textAlign = TextAlign.Center,
            lineHeight = 12.sp,
            color = colorScheme.onBackground.copy(alpha = 0.5F)
        )
    }
}

@LayoutScopeMarker
@Immutable
object BarScope {
    @Stable
    fun Modifier.bar(
        fraction: Float
    ): Modifier {
        return then(
            BarData(fraction)
        )
    }
}

class BarData(
    val fraction: Float
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = this@BarData
}

@Composable
fun DonutBar(
    modifier: Modifier = Modifier,
    fraction: Float
) {
    val gradientColor = listOf(colorScheme.primary, bottomBarColor)

    Spacer(modifier = modifier.drawWithCache {
        onDrawBehind {
            val topLeft = Offset(
                x = 0f,
                y = size.height - (size.height * fraction)
            )

            clipRect {
                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = gradientColor, start = topLeft
                    ),
                    size = size,
                    topLeft = topLeft,
                    cornerRadius = CornerRadius(4.dp.toPx())
                )
            }
        }
    })
}

@MultipleDevices
@Composable
fun Chart_Prev() {
    PreviewSurface {
        TopDonutSalesChart(
            sales = DonutSales.preview
        )
    }
}