package com.phatnhse.sample_food_truck_jc.truck.cards

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.R
import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.DonutStackView
import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.OrderDetail
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.SingleDevice
import com.phatnhse.sample_food_truck_jc.order.Order
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

val padding: Dp = 12.dp

@Composable
fun TruckOrdersCard(
    modifier: Modifier = Modifier, viewModel: TruckOrdersCardViewModel = TruckOrdersCardViewModel()
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        OrderHeader()

        HeroSquareTilingLayout(
            modifier = Modifier.padding(vertical = padding),
            orders = viewModel.orders.asReversed().take(5),
            orderAdded = viewModel.state.value
        )

        LastOrder()
    }
}

@Composable
fun OrderHeader() {
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
}

@Composable
fun LastOrder() {
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
            modifier = Modifier.padding(padding), text = "hihi", color = colorScheme.onBackground
        )
    }
}

class PlaceableInfo(
    val initialOffset: IntOffset? = null,
    val targetOffset: IntOffset? = null,
    val initialSize: IntSize? = null,
    val targetSize: IntSize? = null
) {
    val animatedOffset = Animatable(
        initialOffset ?: IntOffset.Zero, IntOffset.VectorConverter
    )
    val animatedSize = Animatable(
        initialSize ?: IntSize.Zero, IntSize.VectorConverter
    )
}

fun runAnimationIfNeeded(
    scope: CoroutineScope, placeableInfo: List<PlaceableInfo>
) {
    fun <T> getAnimationSpec(): SpringSpec<T> {
        return spring(
            stiffness = Spring.StiffnessLow, dampingRatio = 1F
        )
    }

    scope.launch {
        try {
            val jobs = mutableListOf<Job>()
            placeableInfo.forEach { placeable ->
                placeable.targetOffset?.let { targetOffset ->
                    val animateOffsetJob = launch {
                        placeable.animatedOffset.animateTo(
                            targetValue = targetOffset, getAnimationSpec()
                        )
                    }
                    jobs.add(animateOffsetJob)
                }

                placeable.targetSize?.let { targetSize ->
                    val animateSizeJob = launch {
                        placeable.animatedSize.animateTo(
                            targetValue = targetSize, getAnimationSpec()
                        )
                    }

                    jobs.add(animateSizeJob)
                }
            }

            jobs.joinAll()
        } catch (_: CancellationException) {
            // we don't reset inProgress in case of cancellation as it means
            // there is a new animation started which would reset it later
        }
    }
}

enum class DonutSize { BIG, SMALL }

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnrememberedAnimatable")
@Composable
fun HeroSquareTilingLayout(
    modifier: Modifier = Modifier, orders: List<Order>, orderAdded: Boolean
) {
    val scope = rememberCoroutineScope()
    val itemPositions = remember { mutableMapOf<Int, IntOffset>() }
    val itemSizes = remember { mutableMapOf<DonutSize, IntSize>() }

    val placeableInfo = (0 until 4).map { index ->
        // animate from position 0 -> 1
        // animate from position 1 -> 2
        when (index) {
            0 -> {
                PlaceableInfo(
                    initialOffset = itemPositions[0],
                    targetOffset = itemPositions[1],
                    initialSize = itemSizes[DonutSize.BIG],
                    targetSize = itemSizes[DonutSize.SMALL]
                )
            }

            else -> {
                PlaceableInfo(
                    initialOffset = itemPositions[index], targetOffset = itemPositions[index + 1]
                )
            }
        }
    }

    runAnimationIfNeeded(scope, placeableInfo)

    Layout(modifier = modifier, content = {
        orders.map { order ->
            AnimatedContent(targetState = orderAdded, transitionSpec = {
                slideInHorizontally() with scaleOut()
            }) {
                DonutStackView(
                    modifier = Modifier.padding(padding), OrderDetail(donuts = order.donuts)
                )
            }
        }
    }) { measurables, constraints ->
        val spaceBetweenItems = padding.roundToPx()
        val totalHorizontalSpace = spaceBetweenItems * 4

        val maxWidth = constraints.maxWidth - totalHorizontalSpace
        val bigDonutWidth = (maxWidth * 0.5).toInt()
        val smallDonutWidth = (bigDonutWidth * 0.5).toInt()

        val bigDonutSize = IntSize(bigDonutWidth, bigDonutWidth + spaceBetweenItems)
        val smallDonutSize = IntSize(smallDonutWidth, smallDonutWidth)

        itemSizes[DonutSize.BIG] = bigDonutSize
        itemSizes[DonutSize.SMALL] = smallDonutSize

        layout(constraints.maxWidth, bigDonutWidth) {
            // measurement
            measurables.forEachIndexed { index, _ ->
                when (index) {
                    0 -> {
                        itemPositions[0] = IntOffset(x = spaceBetweenItems, y = 0)
                    }

                    else -> {
                        val tileIndex = index - 1
                        val xPos = if (tileIndex % 2 == 0) 0 else 1
                        val yPos = if (tileIndex < 2) 0 else 1

                        val bigDonutWithSpace = bigDonutWidth + spaceBetweenItems * 2
                        val smallDonutWithSpace = smallDonutWidth + spaceBetweenItems
                        val subViewTotalWidth = xPos * (smallDonutWithSpace)

                        val titleTopLeft = bigDonutWithSpace + subViewTotalWidth

                        itemPositions[index] = IntOffset(
                            x = titleTopLeft, y = yPos * (smallDonutWidth + spaceBetweenItems)
                        )
                    }
                }
            }

            // placement
            measurables.forEachIndexed { index, measurable ->
                when (index) {
                    0 -> {
                        val constraint = Constraints.fixed(
                            width = bigDonutSize.width, height = bigDonutSize.height
                        )
                        val placeable = measurable.measure(constraint)

                        placeable.placeRelative(
                            itemPositions[0] ?: IntOffset.Zero
                        )
                    }

                    else -> {
                        val constraint = if (index == 1) {
                            Constraints.fixed(
                                width = placeableInfo[0].animatedSize.value.width,
                                height = placeableInfo[0].animatedSize.value.height
                            )
                        } else {
                            Constraints.fixed(
                                width = smallDonutSize.width, height = smallDonutSize.height
                            )
                        }
                        val placeable = measurable.measure(constraint)

                        placeable.placeRelative(
                            placeableInfo[index - 1].animatedOffset.value
                        )
                    }
                }
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

