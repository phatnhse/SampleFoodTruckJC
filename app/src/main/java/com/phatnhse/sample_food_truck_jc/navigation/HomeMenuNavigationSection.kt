package com.phatnhse.sample_food_truck_jc.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.foodtruck.general.arrowRightSymbol
import com.phatnhse.sample_food_truck_jc.ui.composable.CustomDivider
import com.phatnhse.sample_food_truck_jc.ui.composable.Section
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme
import com.phatnhse.sample_food_truck_jc.utils.SingleDevice

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
                    modifier = Modifier.size(IconSizeLarge),
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

@SingleDevice
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