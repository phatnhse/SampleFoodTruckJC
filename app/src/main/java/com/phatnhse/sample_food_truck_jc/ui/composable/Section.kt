package com.phatnhse.sample_food_truck_jc.ui.composable

import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Donut
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutView
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Flavor
import com.phatnhse.sample_food_truck_jc.foodtruck.general.arrowRightPainter
import com.phatnhse.sample_food_truck_jc.order.Order
import com.phatnhse.sample_food_truck_jc.order.formattedDate
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeTiny
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.onBackgroundSecondary
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface

@Composable
fun EmptySection(
    modifier: Modifier = Modifier,
    title: String,
    showExpandableIcon: Boolean = false,
    content: @Composable () -> Unit
) {
    var collapsed by remember { mutableStateOf(false) }
    val animateCollapsingButton =
        updateTransition(targetState = collapsed, "update transition collapse")
    val animateRotate = animateCollapsingButton.animateFloat(label = "animate collapse") {
        when (it) {
            true -> 0F
            false -> 90F
        }
    }

    Column(
        modifier = modifier.padding(PaddingNormal)
    ) {
        if (title.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = PaddingNormal, bottom = PaddingNormal, end = PaddingNormal),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title.uppercase(), style = typography.titleSmall.copy(
                        color = colorScheme.onSurface.copy(
                            alpha = 0.5f
                        ), fontSize = 13.sp, fontWeight = FontWeight.Normal
                    )
                )
                if (showExpandableIcon) {
                    Icon(modifier = Modifier
                        .size(IconSizeTiny)
                        .noRippleClickable {
                            collapsed = !collapsed
                        }
                        .rotate(animateRotate.value),
                        painter = arrowRightPainter(),
                        contentDescription = "Expand and collapse icon",
                        tint = colorScheme.primary)
                }
            }
        }

        if (!collapsed) {
            Card(
                modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surface
                )
            ) {
                content()
            }
        }
    }
}

@Composable
fun Section(
    modifier: Modifier = Modifier,
    title: String,
    rows: List<String>,
    onItemClicked: ((String) -> Unit)? = null,
    leadingViews: List<@Composable () -> Unit> = emptyList(),
    trailingViews: List<@Composable () -> Unit> = emptyList(),
    showExpandableIcon: Boolean = false,
    useArrowTrailingViewAsDefault: Boolean = true
) {
    EmptySection(
        modifier = modifier, title = title, showExpandableIcon = showExpandableIcon
    ) {
        List(rows.size) { index ->
            SectionItem(
                title = rows.get(index),
                showDivider = index != rows.lastIndex,
                leadingView = leadingViews.getOrNull(index),
                trailingView = trailingViews.getOrNull(index),
                onItemClicked = onItemClicked,
                useArrowTrailingViewAsDefault = useArrowTrailingViewAsDefault
            )
        }
    }
}

@Composable
fun SectionItem(
    title: String,
    showDivider: Boolean,
    leadingView: @Composable (() -> Unit)? = null,
    trailingView: @Composable (() -> Unit)? = null,
    onItemClicked: ((String) -> Unit)? = null,
    useArrowTrailingViewAsDefault: Boolean = true
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .clickable {
                onItemClicked?.invoke(title)
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingView != null) {
            Spacer(modifier = Modifier.width(PaddingNormal))
            leadingView()
        }

        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(end = PaddingNormal),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(PaddingNormal), text = title
                )

                when {
                    trailingView != null -> {
                        trailingView()
                    }

                    useArrowTrailingViewAsDefault -> {
                        Image(
                            modifier = Modifier
                                .height(12.dp)
                                .width(6.dp),
                            painter = arrowRightPainter(),
                            contentDescription = "Access detail page",
                            colorFilter = ColorFilter.tint(
                                color = colorScheme.onBackgroundSecondary()
                            )
                        )
                    }
                }
            }

            if (showDivider) {
                CustomDivider()
            }
        }
    }
}

@Preview
@Composable
fun Section_Preview() {
    PreviewSurface {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            EmptySection(
                title = "Empty Section"
            ) {
                Column {
                    Flavor.values().map {
                        Text(text = it.displayName)
                    }
                }
            }

            Section(title = "status", rows = listOf(
                "Placed", "Order Started"
            ),
                trailingViews = listOf({
                    Icon(
                        painter = Order.preview.status.iconSystemName(),
                        contentDescription = "Order status"
                    )
                }, {
                    Text(text = Order.preview.creationDate.formattedDate())
                }), onItemClicked = {})

            Section(title = "",
                rows = listOf("Classic", "Sprinkles", "Blueberry Frosted"),
                leadingViews = listOf(
                    {
                        DonutView(
                            modifier = Modifier.size(IconSizeLarge), donut = Donut.classic
                        )
                    },
                    {
                        DonutView(
                            modifier = Modifier.size(IconSizeLarge),
                            donut = Donut.strawberrySprinkles
                        )
                    },
                    {
                        DonutView(
                            modifier = Modifier.size(IconSizeLarge), donut = Donut.blueberryFrosted
                        )
                    },
                ),
                onItemClicked = { item ->
                    Log.i("nhp", "$item clicked")
                })
        }
    }
}