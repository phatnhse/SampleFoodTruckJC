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
import com.phatnhse.sample_food_truck_jc.navigation.HomeMenuNavigationHeader
import com.phatnhse.sample_food_truck_jc.navigation.LauncherViewId
import com.phatnhse.sample_food_truck_jc.navigation.MenuItem
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingExtraLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface

@Composable
fun HomeView(
    modifier: Modifier = Modifier, onMenuItemClicked: (MenuItem) -> Unit
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
            Card(
                modifier = modifier.padding(PaddingNormal), colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surface
                )
            ) {
                HomeMenuNavigationHeader(
                    menuItem = MenuItem.Truck, onClicked = onMenuItemClicked
                )
                HomeMenuNavigationHeader(
                    menuItem = MenuItem.Orders, onClicked = onMenuItemClicked
                )
                HomeMenuNavigationHeader(
                    menuItem = MenuItem.SocialFeed, onClicked = onMenuItemClicked
                )
                HomeMenuNavigationHeader(
                    menuItem = MenuItem.SalesHistory,
                    showDivider = false,
                    onClicked = onMenuItemClicked
                )
            }

            Card(
                modifier = modifier.padding(PaddingNormal), colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surface
                )
            ) {
                HomeMenuNavigationHeader(
                    menuItem = MenuItem.Donuts, onClicked = onMenuItemClicked
                )
                HomeMenuNavigationHeader(
                    menuItem = MenuItem.DonutEditor, onClicked = onMenuItemClicked
                )
                HomeMenuNavigationHeader(
                    menuItem = MenuItem.TopFive, onClicked = onMenuItemClicked, showDivider = false
                )
            }

            Card(
                modifier = modifier.padding(PaddingNormal), colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surface
                )
            ) {
                HomeMenuNavigationHeader(
                    menuItem = MenuItem.City(cupertino.id),
                    symbolColor = colorScheme.secondary,
                    onClicked = onMenuItemClicked
                )
                HomeMenuNavigationHeader(
                    menuItem = MenuItem.City(sanFrancisco.id),
                    symbolColor = colorScheme.secondary,
                    onClicked = onMenuItemClicked
                )
                HomeMenuNavigationHeader(
                    menuItem = MenuItem.City(london.id),
                    symbolColor = colorScheme.secondary,
                    onClicked = onMenuItemClicked
                )
            }
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