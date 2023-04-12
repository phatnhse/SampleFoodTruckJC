package com.phatnhse.sample_food_truck_jc.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import com.phatnhse.sample_food_truck_jc.ui.composable.Section
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingSmall
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview

@Composable
fun HomeMenuNavigationSection(
    modifier: Modifier = Modifier,
    title: String,
    menuItems: List<MenuItem>,
    symbolColor: Color = colorScheme.primary,
    onClicked: (MenuItem) -> Unit
) {
    Section(
        modifier = modifier,
        title = title,
        rows = menuItems.map { it.getTitle() },
        leadingViews = menuItems.map { menuItem ->
            {
                Image(
                    modifier = Modifier.size(IconSizeLarge).padding(start = PaddingSmall),
                    painter = menuItem.getSymbol(),
                    contentDescription = menuItem.getTitle(),
                    colorFilter = ColorFilter.tint(
                        color = symbolColor
                    )
                )
            }
        },
        onItemClicked = { item ->
            onClicked(MenuItem.valueOf(item))
        }
    )
}

@SingleDevicePreview
@Composable
fun HomeMenuNavigationHeader_Preview() {
    SampleFoodTruckJCTheme {
        HomeMenuNavigationSection(
            title = "donuts",
            menuItems = listOf(
                MenuItem.Donuts,
                MenuItem.DonutEditor,
                MenuItem.TopFive
            ),
            onClicked = { menuItem ->

            }
        )
    }
}