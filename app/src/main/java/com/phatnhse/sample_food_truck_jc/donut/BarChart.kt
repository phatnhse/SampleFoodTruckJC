package com.phatnhse.sample_food_truck_jc.donut

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Donut
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutSales
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutView
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface


@Composable
fun TopDonutSalesChart(
    sales: List<DonutSales>
) {
    DonutChart(
        donutCount = 5
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
//    saleCount: Int,
//    donutFooter: @Composable (index: Int) -> Unit,
//    saleGridLine: @Composable (index: Int) -> Unit,
//    donutBar: @Composable (index: Int) -> Unit
) {

    val bars = @Composable {
        repeat(donutCount) {
            DonutBar(text = "25")
        }
    }

    Layout(
        contents = listOf(bars),
        modifier = modifier
    ) {
            (barMeasurables),
            constraints,
        ->


        val totalWidth = constraints.maxWidth
        val totalHeight = constraints.maxHeight

        val barPlaceables = barMeasurables.map {
            it.measure(
                Constraints.fixed(
                    width = 48.dp.roundToPx(),
                    height = 280.dp.roundToPx()
                )
            )
        }

        layout(totalWidth, totalHeight) {
            barPlaceables.forEachIndexed { index, placeable ->
                placeable.place(
                    x = 0 + index * placeable.width,
                    y = 0
                )
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun DonutBar(text: String) {
    val skyGradient = listOf(colorScheme.primary, colorScheme.background)
    val textMeasure = rememberTextMeasurer()
    val paddingValue = PaddingNormal
    val textStyle = typography.labelLarge
    val textSize = textStyle.fontSize
    val corner = 4.dp

    Spacer(modifier = Modifier
        .drawWithCache {
            onDrawBehind {
                val topLeft = Offset(
                    x = 0f, y = (size.height - 120.dp.toPx())
                )

                val textSize = Size(
                    width = textSize.toPx(), height = textSize.toPx()
                )

                val padding = paddingValue.toPx()

                val textOffset = Offset(
                    x = (size.width - textSize.width) / 2,
                    y = topLeft.y - textSize.height - padding
                )

                clipRect {
                    drawRoundRect(
                        brush = Brush.radialGradient(colors = skyGradient, radius = 600f),
                        size = size,
                        topLeft = topLeft,
                        cornerRadius = CornerRadius(corner.toPx())
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
        TopDonutSalesChart(sales = listOf())
    }
}