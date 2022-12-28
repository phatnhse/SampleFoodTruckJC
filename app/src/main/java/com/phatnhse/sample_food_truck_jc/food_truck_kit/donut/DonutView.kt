package com.phatnhse.sample_food_truck_jc.food_truck_kit.donut

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.ingredientImage
import kotlin.math.min

@Composable
fun DonutView(
    donut: Donut,
    visibleLayers: DonutLayer = all
) {
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = Modifier
            .aspectRatio(ratio = 1F, matchHeightConstraintsFirst = true)
            .fillMaxSize()
    ) {
        val useThumb = with(density) {
            min(maxWidth.toPx(), maxHeight.toPx()) <= DONUT_THUMBNAIL_SIZE
        }

        if (visibleLayers.dough) {
            Image(
                painter = ingredientImage(
                    donut.dough,
                    thumbnail = useThumb
                ), contentDescription = donut.dough.name
            )
        }

        if (visibleLayers.glaze) {
            donut.glaze?.let {
                Image(
                    painter = ingredientImage(
                        donut.glaze,
                        thumbnail = useThumb
                    ), contentDescription = it.name
                )
            }
        }

        if (visibleLayers.topping) {
            donut.topping?.let {
                Image(
                    painter = ingredientImage(
                        donut.topping,
                        thumbnail = useThumb
                    ), contentDescription = it.name
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun DonutView_Preview() {
    DonutView(donut = Donut.preview)
}

data class DonutLayer(
    val dough: Boolean = true,
    val glaze: Boolean = true,
    val topping: Boolean = true
)

private val all = DonutLayer()
private const val DONUT_THUMBNAIL_SIZE = 192