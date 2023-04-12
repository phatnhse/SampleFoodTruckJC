package com.phatnhse.sample_food_truck_jc.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.phatnhse.sample_food_truck_jc.foodtruck.city.City.Companion.cupertino
import com.phatnhse.sample_food_truck_jc.foodtruck.city.City.Companion.london
import com.phatnhse.sample_food_truck_jc.foodtruck.city.City.Companion.sanFrancisco
import com.phatnhse.sample_food_truck_jc.navigation.HomeMenuNavigationSection
import com.phatnhse.sample_food_truck_jc.navigation.LauncherViewId
import com.phatnhse.sample_food_truck_jc.navigation.MenuItem
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingExtraLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    onMenuItemClicked: (MenuItem) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.background
        )
    ) {
        Spacer(modifier = modifier.height(PaddingExtraLarge))
        Text(
            modifier = Modifier.padding(
                horizontal = PaddingNormal
            ),
            text = LauncherViewId,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                color = colorScheme.onBackground
            )
        )

        Column {
            HomeMenuNavigationSection(
                title = "",
                menuItems = listOf(
                    MenuItem.Truck,
                    MenuItem.Orders,
                    MenuItem.SocialFeed,
                    MenuItem.SalesHistory
                ),
                onClicked = { menuItem ->
                    onMenuItemClicked(menuItem)
                }
            )

            HomeMenuNavigationSection(
                title = "donuts",
                menuItems = listOf(
                    MenuItem.Donuts,
                    MenuItem.DonutEditor,
                    MenuItem.TopFive
                ),
                onClicked = { menuItem ->
                    onMenuItemClicked(menuItem)
                }
            )

            HomeMenuNavigationSection(
                title = "cities",
                symbolColor = colorScheme.secondary,
                menuItems = listOf(
                    MenuItem.City(cupertino.id),
                    MenuItem.City(sanFrancisco.id),
                    MenuItem.City(london.id),
                ),
                onClicked = { menuItem ->
                    onMenuItemClicked(menuItem)
                }
            )
        }
    }
}

@Preview
@Composable
fun Preview_HomeView() {
    PreviewSurface {
        HomeView(modifier = Modifier, onMenuItemClicked = {})
    }
}