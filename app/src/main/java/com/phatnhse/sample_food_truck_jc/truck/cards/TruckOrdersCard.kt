package com.phatnhse.sample_food_truck_jc.truck.cards

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.R
import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.DonutStackView
import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.OrderDetail
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.SingleDevice
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme

@Composable
fun TruckOrdersCard(
    modifier: Modifier = Modifier,
    padding: Dp = 12.dp,
    viewModel: TruckOrdersCardViewModel = TruckOrdersCardViewModel()
) {
    val orders = viewModel.orders

    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = padding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp),
                painter = painterResource(id = R.drawable.shipping_box),
                contentDescription = "Donut",
                colorFilter = ColorFilter.tint(color = colorScheme.tertiary)
            )

            Text(
                modifier = Modifier.padding(padding),
                text = "New Order",
                color = colorScheme.tertiary,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )

            Image(
                modifier = Modifier
                    .height(12.dp)
                    .width(6.dp),
                painter = painterResource(id = R.drawable.chevron_right),
                contentDescription = "Donut",
                colorFilter = ColorFilter.tint(color = colorScheme.onBackground)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        HeroSquareTilingLayout(
            modifier = Modifier, spacing = padding
        ) {
            orders.asReversed().take(5).mapIndexed { reversedIndex, order ->
                val orderIndex = reversedIndex + orders.size - 1

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.surface
                    )
                ) {
                    DonutStackView(
                        modifier = Modifier.padding(padding),
                        OrderDetail(index = orderIndex, donuts = order.donuts)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(padding),
                text = "Order#1224",
                color = colorScheme.onBackground
            )

            Image(
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp),
                painter = painterResource(id = R.drawable.donut),
                contentDescription = "Donut",
                colorFilter = ColorFilter.tint(color = colorScheme.onBackground)
            )

            Text(
                modifier = Modifier.padding(padding),
                text = "hihi",
                color = colorScheme.onBackground
            )
        }
    }
}

private enum class SubViewState {
    IDLE, MOVING
}

private data class DonutPlaceable(
    val placeable: Placeable,
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int
)

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun HeroSquareTilingLayout(
    modifier: Modifier = Modifier,
    spacing: Dp = 12.dp,
    content: @Composable () -> Unit,
) {
    val donutViews: MutableList<DonutPlaceable> = mutableListOf()

    val view1 = remember {
        mutableStateOf(SubViewState.IDLE)
    }
    val animation1 = updateTransition(targetState = view1.value, label = "view1")
    val position = animation1.animateFloat() {
        when()
    }

    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        val subViews = measurables.take(5)

        val spacingInPx = spacing.roundToPx()
        val halfSpacing = (spacingInPx * 0.5).toInt()
        val totalSpacing = spacingInPx * 4

        val maxWidth = constraints.maxWidth - totalSpacing
        val size = minOf(maxWidth, maxWidth)

        val halfSize = (size * 0.5).toInt()
        val tileSize = (halfSize * 0.5).toInt()

        // measurement
        subViews.forEachIndexed { index, subView ->
            when (index) {
                0 -> {
                    val placeable = subView.measure(
                        Constraints.fixed(width = halfSize, height = halfSize)
                    )

                    donutViews.add(
                        index,
                        DonutPlaceable(
                            placeable = placeable,
                            x = 0 + spacingInPx,
                            y = 0,
                            width = halfSize,
                            height = halfSize
                        )
                    )
                }

                else -> {
                    val tileIndex = index - 1
                    val xPos = if (tileIndex % 2 == 0) 0 else 1
                    val yPos = if (tileIndex < 2) 0 else 1

                    val placeable = subView.measure(
                        Constraints.fixed(
                            width = tileSize,
                            height = tileSize - halfSpacing
                        )
                    )

                    val firstSubViewTotalWidth = halfSize + spacingInPx + spacingInPx
                    val subViewTotalWidth = xPos * (tileSize + spacingInPx)
                    val topLefOffset = firstSubViewTotalWidth + subViewTotalWidth

                    donutViews.add(
                        index,
                        DonutPlaceable(
                            placeable = placeable,
                            x = topLefOffset,
                            y = yPos * (tileSize + halfSpacing),
                            width = tileSize,
                            height = tileSize - halfSpacing
                        )
                    )
                }
            }
        }


        // placement
        layout(constraints.maxWidth, halfSize) {
            donutViews.forEachIndexed { index, donutPlaceable ->
                donutPlaceable.placeable.placeRelative(
                    donutPlaceable.x,
                    donutPlaceable.y
                )
            }
        }
    }
}

@SingleDevice
@Composable
fun TruckOrdersCard_Preview() {
    SampleFoodTruckJCTheme {
        TruckOrdersCard()
    }
}

