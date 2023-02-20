package com.phatnhse.sample_food_truck_jc.truck.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.socialFeedSymbol
import com.phatnhse.sample_food_truck_jc.navigation.HeaderNavigation
import com.phatnhse.sample_food_truck_jc.truck.SocialFeedTag
import com.phatnhse.sample_food_truck_jc.truck.SocialFeedTagView
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme
import java.lang.Integer.min

@Composable
fun TruckSocialFeedCard(
    modifier: Modifier = Modifier,
    tags: List<SocialFeedTag> = SocialFeedTag.tags
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface
        )
    ) {
        HeaderNavigation(
            title = "Social Feed",
            symbol = socialFeedSymbol(),
            color = colorScheme.onBackground.copy(alpha = 0.5F)
        )

        FlowLayout {
            tags.map {
                SocialFeedTagView(
                    socialFeedTag = it,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Trending Topics",
                fontSize = 12.sp,
                color = colorScheme.onBackground.copy(alpha = 0.5F)
            )
        }
    }
}

@Composable
fun FlowLayout(
    modifier: Modifier = Modifier,
    children: @Composable () -> Unit
) {
    data class RowInfo(
        val placeables: MutableMap<Placeable, IntOffset>,
        var width: Int,
        var height: Int
    )

    Layout(
        modifier = modifier,
        content = children,
    ) { measurables, constraints ->
        var (xPos, yPos, maxY) = listOf(0, 0, 0)
        val rows = mutableListOf<RowInfo>()
        var curIndex = 0

        measurables.map { measurable ->
            val placeable = measurable.measure(constraints)
            if (xPos + placeable.width > constraints.maxWidth) {
                curIndex++
                xPos = 0
                yPos += maxY
                maxY = 0
            }

            val offset = IntOffset(x = xPos, y = yPos)

            xPos += placeable.width
            if (maxY < placeable.height) {
                maxY = placeable.height
            }

            val rowInfo = rows.getOrNull(curIndex)
            if (rowInfo == null) {
                val placeableInfo = mutableMapOf(placeable to offset)
                rows.add(RowInfo(placeableInfo, placeable.width, maxY))
            } else {
                rowInfo.placeables[placeable] = offset
                rowInfo.width += placeable.width
                rowInfo.height = maxY
            }
        }

        val totalHeight = rows.sumOf { it.height }

        layout(constraints.maxWidth, min(constraints.maxHeight, totalHeight)) {
            rows.forEach { (placeables, width) ->
                placeables.forEach { (placeable, offset) ->
                    val horizontalSpacing = (constraints.maxWidth - width) * 0.5
                    placeable.placeRelative(
                        x = offset.x + horizontalSpacing.toInt(),
                        y = offset.y
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TruckSocialFeedCard_Preview() {
    SampleFoodTruckJCTheme {
        TruckSocialFeedCard(
            modifier = Modifier,
            tags = SocialFeedTag.tags
        )
    }
}