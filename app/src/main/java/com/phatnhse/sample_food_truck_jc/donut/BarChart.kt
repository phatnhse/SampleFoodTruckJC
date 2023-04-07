package com.phatnhse.sample_food_truck_jc.donut

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Donut
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutSales
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutView
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.bottomBarColor
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface


@Composable
fun TopDonutSalesChart(
    sales: List<DonutSales>
) {
    val totalSales = sales.sumOf { it.sales }
    val y = listOf(0, 1000, 2000, 3000)

    DonutChart(
        Modifier
            .height(300.dp)
            .padding(horizontal = PaddingNormal),
        donutCount = 5,
        saleCount = 4,
        donutBar = {
            println("nhp" + sales[it].sales / totalSales)
            DonutBar(
                value = (sales[it].sales.toFloat() / totalSales), text = sales[it].sales.toString()
            )
        },
        donutView = {
            DonutView(donut = sales[it].donut)
        },
        saleNumber = {
            Text(text = y[it].toString())
        }
    )
}


@Composable
fun DonutFooter(donut: Donut) {
    return Column {
        DonutView(
            Modifier.size(56.dp), donut = donut
        )

        Spacer(modifier = Modifier.height(PaddingNormal))
        Text(donut.name)
    }
}

@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    donutCount: Int,
    saleCount: Int,
    saleNumber: @Composable (index: Int) -> Unit,
    donutView: @Composable (index: Int) -> Unit,
    donutBar: @Composable (index: Int) -> Unit
) {
    val donutBars = @Composable { repeat(donutCount) { donutBar(it) } }
    val donuts = @Composable { repeat(donutCount) { donutView(it) } }
    val saleNumbers = @Composable { repeat(saleCount) { saleNumber(it) } }

    val textSize = 15.sp

    Layout(
        modifier = modifier, contents = listOf(donutBars, donuts, saleNumbers)
    ) { (barMeasurables, donutMeasurables, textMeasurables), constraints ->
        val totalWidth = constraints.maxWidth
        val totalHeight = constraints.maxHeight

        val barWidth = (totalWidth - 100) / 9
        val textHeight = totalHeight / 3

        val barPlaceables = barMeasurables.map {
            it.measure(
                Constraints.fixed(
                    width = barWidth, height = totalHeight
                )
            )
        }

        val donutPlaceables = donutMeasurables.map {
            it.measure(
                Constraints.fixed(
                    width = barWidth,
                    height = barWidth
                )
            )
        }

        val textPlaceables = textMeasurables.map {
            it.measure(Constraints(2000))
        }

        layout(totalWidth, totalHeight) {
//            val xPosition = barPlaceables.first().width



            textPlaceables.forEachIndexed { index, placeable ->
                placeable.place(
                    x = totalWidth,
                    y = 0 + index * (textHeight - textSize.roundToPx())
                )
            }

            barPlaceables.forEachIndexed { index, placeable ->
                placeable.place(x = 0 + index * (placeable.width + barWidth), y = 0 - 100)
                donutPlaceables[index].place(
                    x = 0 + index * (placeable.width + barWidth), y = totalHeight - 100
                )
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun DonutBar(
    value: Float, text: String
) {
    val skyGradient = listOf(colorScheme.primary, bottomBarColor)
    val textMeasure = rememberTextMeasurer()
    val paddingValue = PaddingNormal
    val textStyle = typography.labelLarge
    val textSize = textStyle.fontSize
    val textBackgroundSize = 21.dp
    val corner = 4.dp

    Spacer(modifier = Modifier.drawWithCache {
        onDrawBehind {
            val topLeft = Offset(
                x = 0f, y = size.height - (size.height * value)
            )

            val textSize = Size(
                width = textSize.toPx(), height = textSize.toPx()
            )

            val padding = paddingValue.toPx()

            val textOffset = Offset(
                x = (size.width - textSize.width) / 2,
                y = topLeft.y - textSize.height - padding
            )

            val textBackGroundOffset = Offset(
                x = (size.width - textBackgroundSize.toPx()) / 2,
                y = topLeft.y - textBackgroundSize.toPx() - padding / 2
            )

            clipRect {
                drawRoundRect(
                    brush = Brush.linearGradient(colors = skyGradient),
                    size = size,
                    topLeft = topLeft,
                    cornerRadius = CornerRadius(corner.toPx())
                )

                drawRoundRect(
                    brush = SolidColor(Color.Red),
                    size = Size(textBackgroundSize.toPx(), textBackgroundSize.toPx()),
                    topLeft = textBackGroundOffset,
                    cornerRadius = CornerRadius(20.dp.toPx())
                )


                drawText(
                    textMeasurer = textMeasure,
                    text = text,
                    topLeft = textOffset,
                    style = textStyle
                )
            }
        }
    })
}

@Preview
@Composable
fun Chart_Prev() {
    PreviewSurface {
        TopDonutSalesChart(sales = DonutSales.preview)
    }
}