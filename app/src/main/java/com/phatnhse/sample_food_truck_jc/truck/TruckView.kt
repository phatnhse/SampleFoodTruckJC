package com.phatnhse.sample_food_truck_jc.truck

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.phatnhse.sample_food_truck_jc.foodtruck.brand.BrandHeader
import com.phatnhse.sample_food_truck_jc.foodtruck.model.FoodTruckViewModel
import com.phatnhse.sample_food_truck_jc.navigation.NavigationHeader
import com.phatnhse.sample_food_truck_jc.truck.cards.TruckDonutCards
import com.phatnhse.sample_food_truck_jc.truck.cards.TruckOrdersCard
import com.phatnhse.sample_food_truck_jc.truck.cards.TruckSocialFeedCard
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevice

@Composable
fun TruckView(
    previousViewTitle: String,
    currentViewTitle: String,
    onNavigateToOrders: () -> Unit,
    onNavigateToDonuts: () -> Unit,
    onNavigateToSocialFeed: () -> Unit,
    onBackPressed: () -> Unit,
    viewModel: FoodTruckViewModel

) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            BrandHeader(animated = false)
            NavigationHeader(
                previousViewTitle = previousViewTitle,
                currentViewTitle = currentViewTitle,
                onBackPressed = onBackPressed
            )
        }

        Column(
            modifier = Modifier.padding(vertical = PaddingNormal),
            verticalArrangement = Arrangement.spacedBy(
                space = PaddingNormal,
                alignment = Alignment.CenterVertically
            )
        ) {
            TruckOrdersCard(
                onNavigateToOrders = onNavigateToOrders,
                viewModel = viewModel
            )
            TruckDonutCards(
                onNavigateToDonuts = onNavigateToDonuts,
                viewModel = viewModel
            )
            TruckSocialFeedCard(onNavigateToSocialFeed = onNavigateToSocialFeed)
        }
    }
}

@SingleDevice
@Composable
fun TruckView_Preview() {
    PreviewSurface {
        TruckView(
            previousViewTitle = "Food Truck",
            currentViewTitle = "Truck",
            onNavigateToOrders = { },
            onNavigateToDonuts = { },
            onNavigateToSocialFeed = { },
            onBackPressed = { },
            viewModel = FoodTruckViewModel.preview
        )
    }
}