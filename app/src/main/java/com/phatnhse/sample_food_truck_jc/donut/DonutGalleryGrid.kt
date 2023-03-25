package com.phatnhse.sample_food_truck_jc.donut

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Donut
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutView
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.flavorPainter
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingSmall
import com.phatnhse.sample_food_truck_jc.utils.MultipleDevices
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface

@Composable
fun DonutGalleryGrid(
    modifier: Modifier = Modifier,
    donuts: List<Donut>
) {
    val minCellSize = 130.dp
    val minThumbnailSize = 60.dp

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minCellSize)
    ) {
        itemsIndexed(donuts, key = { _, item ->
            item.id
        }) { _, donut ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                DonutView(
                    modifier = Modifier
                        .size(minThumbnailSize)
                        .padding(top = PaddingNormal),
                    donut = donut
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = donut.name,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(PaddingSmall))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val flavor = donut.flavors.mostPotentFlavor
                    Image(
                        painter = flavorPainter(flavor = flavor),
                        contentDescription = "Flavor Image",
                        modifier = Modifier.size(16.dp),
                        colorFilter = ColorFilter.tint(
                            colorScheme.onBackground.copy(
                                alpha = 0.5F
                            )
                        )
                    )
                    Text(
                        modifier = Modifier.padding(start = PaddingSmall),
                        text = flavor.name,
                        color = colorScheme.onBackground.copy(
                            alpha = 0.5F
                        ),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@MultipleDevices
@Composable
fun DonutGalleryGrid_Preview() {
    PreviewSurface {
        DonutGalleryGrid(
            donuts = Donut.all
        )
    }
}