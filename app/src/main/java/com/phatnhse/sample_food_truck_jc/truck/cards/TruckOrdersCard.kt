package com.phatnhse.sample_food_truck_jc.truck.cards

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.unit.sp
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

val padding = 12.dp

@Composable
fun TruckOrdersCard(
    modifier: Modifier = Modifier,
    viewModel: TruckOrdersCardViewModel = TruckOrdersCardViewModel()
) {
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

        HeroSquareTilingLayout(
            modifier = Modifier.padding(vertical = 8.dp),
            spacing = padding,
            orders = viewModel.orders.asReversed().take(5)
        )

        LatestOrder(viewModel.orderAdded)
    }
}

@Composable
fun LatestOrder(
    orderAdded: MutableState<Boolean>
) {
    val latestOrder = updateTransition(targetState = orderAdded.value, "order added animation")

    val animateFontWeight = latestOrder.animateInt(
        label = "animate weight"
    ) {
        when (it) {
            true -> 800
            false -> 400
        }
    }
    val animateFontSize = latestOrder.animateInt(
        label = "animate font size"
    ) {
        when (it) {
            true -> 16
            false -> 15
        }
    }

    if (latestOrder.currentState == latestOrder.targetState) {
        orderAdded.value = false
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(padding),
            text = "Order#1224",
            fontSize = animateFontSize.value.sp,
            color = colorScheme.onBackground,
            fontWeight = FontWeight(animateFontWeight.value)
        )

        Image(
            modifier = Modifier
                .height((animateFontSize.value + 9).dp)
                .width(((animateFontSize.value + 9)).dp),
            painter = painterResource(id = R.drawable.donut),
            contentDescription = "Donut",
            colorFilter = ColorFilter.tint(color = colorScheme.onBackground)
        )

        Text(
            modifier = Modifier.padding(padding),
            text = "Lorem Ipsum",
            fontSize = animateFontSize.value.sp,
            color = colorScheme.onBackground,
            fontWeight = FontWeight(animateFontWeight.value)
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
        initialOffset ?: IntOffset.Zero,
        IntOffset.VectorConverter
    )
    val animatedSize = Animatable(
        initialSize ?: IntSize.Zero,
        IntSize.VectorConverter
    )
}

enum class DonutSize { BIG, SMALL }

@SuppressLint("UnrememberedAnimatable")
@Composable
fun HeroSquareTilingLayout(
    modifier: Modifier = Modifier,
    spacing: Dp,
    orders: List<Order>
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
                    initialOffset = itemPositions[index],
                    targetOffset = itemPositions[index + 1]
                )
            }
        }
    }

    runAnimationIfNeeded(scope, placeableInfo)

    Layout(modifier = modifier, content = {
        orders.map { order ->
            DonutStackView(
                modifier = Modifier.padding(spacing),
                orderDetail = OrderDetail(donuts = order.donuts)
            )
        }
    }) { measurables, constraints ->
        val spaceBetweenItems = spacing.roundToPx()
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
                        itemPositions[0] = IntOffset(spaceBetweenItems, 0)
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
                            width = bigDonutSize.width,
                            height = bigDonutSize.height
                        )
                        val placeable = measurable.measure(constraint)

                        placeable.placeRelativeWithLayer(
                            itemPositions[index] ?: IntOffset.Zero
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
                                width = smallDonutSize.width,
                                height = smallDonutSize.height
                            )
                        }
                        val placeable = measurable.measure(constraint)

                        placeable.placeRelativeWithLayer(
                            placeableInfo[index - 1].animatedOffset.value
                        )
                    }
                }
            }
        }
    }
}


fun runAnimationIfNeeded(
    scope: CoroutineScope,
    placeableInfo: List<PlaceableInfo>
) {
    fun <T> getAnimationSpec(): SpringSpec<T> {
        return spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = 0.8F
        )
    }

    scope.launch {
        try {
            val jobs = mutableListOf<Job>()
            placeableInfo.forEach { placeable ->
                placeable.targetOffset?.let { targetOffset ->
                    val animateOffsetJob = launch {
                        placeable.animatedOffset.animateTo(
                            targetValue = targetOffset,
                            animationSpec = getAnimationSpec()
                        )
                    }
                    jobs.add(animateOffsetJob)
                }

                placeable.targetSize?.let { targetSize ->
                    val animateSizeJob = launch {
                        placeable.animatedSize.animateTo(
                            targetValue = targetSize,
                            animationSpec = getAnimationSpec()
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

@SingleDevice
@Composable
fun TruckOrdersCard_Preview() {
    SampleFoodTruckJCTheme {
        TruckOrdersCard()
    }
}
