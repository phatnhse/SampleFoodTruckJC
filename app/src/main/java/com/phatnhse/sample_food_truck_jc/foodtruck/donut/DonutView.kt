package com.phatnhse.sample_food_truck_jc.foodtruck.donut

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.phatnhse.sample_food_truck_jc.utils.MultipleDevicesPreview

@Composable
fun DonutView(
    modifier: Modifier = Modifier,
    donut: Donut, visibleLayers: DonutLayer = all
) {
    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(1F, matchHeightConstraintsFirst = true)
    ) {
        var size = min(maxWidth, maxHeight)

        if (size < DONUT_THUMBNAIL_SIZE) {
            size = DONUT_THUMBNAIL_SIZE
        }

        if (visibleLayers.dough) {
            donut.dough.IngredientImage(
                modifier = Modifier.size(size)
            )
        }

        if (visibleLayers.glaze) {
            donut.glaze?.IngredientImage(
                modifier = Modifier.size(size)
            )
        }

        if (visibleLayers.topping) {
            donut.topping?.IngredientImage(
                modifier = Modifier.size(size)
            )
        }
    }
}

@MultipleDevicesPreview
@Composable
fun DonutView_Preview() {
    DonutView(
        modifier = Modifier.size(60.dp), donut = Donut.preview
    )
}

data class DonutLayer(
    val dough: Boolean = true, val glaze: Boolean = true, val topping: Boolean = true
)

private val all = DonutLayer()

private val DONUT_THUMBNAIL_SIZE = 72.dp