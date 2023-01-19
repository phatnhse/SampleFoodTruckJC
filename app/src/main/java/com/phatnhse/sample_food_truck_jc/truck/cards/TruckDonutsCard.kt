package com.phatnhse.sample_food_truck_jc.truck.cards

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.Donut
import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.DonutView
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.SingleDevice
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.donutSymbol
import com.phatnhse.sample_food_truck_jc.navigation.HeaderNavigation
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme
import java.lang.Integer.min

@Composable
fun TruckDonutCards(
    modifier: Modifier = Modifier,
    donuts: List<Donut>
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        HeaderNavigation(
            title = "Donut",
            symbol = donutSymbol()
        )

        DonutLatticeLayout(
            donuts = donuts.take(14)
        )
    }
}


@Composable
fun DonutLatticeLayout(
    modifier: Modifier = Modifier,
    columns: Int = 5,
    rows: Int = 3,
    spacing: Dp = PaddingNormal,
    donuts: List<Donut>
) {
    Layout(
        modifier = modifier,
        content = {
            donuts.map {
                DonutView(
                    modifier = Modifier.padding(all = spacing.div(2)),
                    donut = it
                )
            }
        }) { measurables, constraints ->
        val cellLength = min((constraints.maxWidth / columns), (constraints.maxHeight / rows))
        val size = IntSize(
            width = columns * (cellLength),
            height = rows * (cellLength)
        )
        val origin = IntOffset.Zero

        layout(size.width, size.height) {
            for (row in 0 until rows) {
                val cellY = origin.y + (row * cellLength)
                val columnsForRow = if (row % 2 == 0) columns else columns - 1

                for (column in 0 until columnsForRow) {
                    var cellX = origin.x + (column * (cellLength))
                    if (row % 2 != 0) {
                        cellX += (cellLength * 0.5).toInt()
                    }

                    var result = 0
                    if (row > 0) {
                        (0 until row).forEach { completedRow ->
                            result += if (completedRow % 2 == 0) {
                                columns
                            } else {
                                columns - 1
                            }
                        }
                    }

                    val index = result + column
                    if (index >= measurables.size) break

                    val offset = IntOffset(
                        x = cellX,
                        y = cellY
                    )

                    val placeable = measurables[index].measure(
                        Constraints.fixed(
                            width = cellLength,
                            height = cellLength
                        )
                    )

                    placeable.placeRelative(offset)
                }
            }
        }
    }
}

@SingleDevice
@Composable
fun TruckDonutCards_Preview() {
    SampleFoodTruckJCTheme {
        TruckDonutCards(donuts = Donut.all)
    }
}

