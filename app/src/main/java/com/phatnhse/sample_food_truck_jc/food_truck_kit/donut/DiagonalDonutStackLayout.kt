package com.phatnhse.sample_food_truck_jc.food_truck_kit.donut

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.MultipleDevices
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.SingleDevice
import kotlin.math.min

@Composable
fun DiagonalDonutStackLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val subViews = measurables.take(3)
        val size = min(constraints.maxWidth, constraints.maxHeight)
        val minDonutSize = (size * 0.65).toInt()
        val centerX = minDonutSize / 2
        val centerY = minDonutSize / 2

        layout(constraints.maxWidth, constraints.maxHeight) {
            subViews.forEachIndexed { index, _ ->
                when (subViews.count()) {
                    1 -> {
                        val placeable = subViews[index].measure(Constraints(size))
                        placeable.placeRelative(x = 0, y = 0)
                    }

                    2 -> {
                        val direction = if (index == 0) -1.0 else 1.0
                        val offsetX = (minDonutSize * direction * 0.28).toInt()
                        val offsetY = (minDonutSize * direction * 0.28).toInt()
                        val placeable = subViews[index].measure(Constraints((size * 0.65).toInt()))
                        placeable.placeRelative(
                            x = centerX / 2 + offsetX,
                            y = centerY / 2 + offsetY
                        )
                    }

                    3 -> {
                        if (index == 1) {
                            val placeable = subViews[index].measure(Constraints(minDonutSize))
                            placeable.placeRelative(
                                x = centerX / 2,
                                y = centerY / 2,
                                zIndex = 0.1f
                            )
                        } else {
                            val direction = if (index == 0) -1.0 else 1.0
                            val offsetX = (minDonutSize * direction * 0.28).toInt()
                            val offsetY = (minDonutSize * direction * 0.32).toInt()

                            val placeable = subViews[index].measure(Constraints(minDonutSize))
                            placeable.placeRelative(
                                x = centerX / 2 + offsetX,
                                y = centerY / 2 + offsetY,
                                zIndex = offsetX.toFloat()
                            )
                        }
                    }
                }
            }
        }
    }
}

@MultipleDevices
@Composable
fun DiagonalDonutStackLayout_Preview() {
    Column {
        DiagonalDonutStackLayout(
            Modifier
                .height(120.dp)
                .width(120.dp)
        ) {
            DonutView(donut = Donut.classic)
        }

        DiagonalDonutStackLayout(
            Modifier
                .height(120.dp)
                .width(120.dp)
        ) {
            DonutView(donut = Donut.classic)
            DonutView(donut = Donut.cosmos)
        }

        DiagonalDonutStackLayout(
            Modifier
                .height(120.dp)
                .width(120.dp)
        ) {
            DonutView(donut = Donut.classic)
            DonutView(donut = Donut.cosmos)
            DonutView(donut = Donut.rainbow)
        }

        DiagonalDonutStackLayout(
            Modifier
                .height(120.dp)
                .width(120.dp)
        ) {
            DonutView(donut = Donut.classic)
            DonutView(donut = Donut.cosmos)
            DonutView(donut = Donut.rainbow)
            DonutView(donut = Donut.picnicBasket)
            DonutView(donut = Donut.picnicBasket)
        }
    }
}