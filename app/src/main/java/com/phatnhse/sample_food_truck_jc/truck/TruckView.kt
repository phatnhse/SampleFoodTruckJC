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
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview

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
            BrandHeader(animated = true)
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

@Composable
fun NamePicker(
    header: String,
    names: List<String>,
    onNameClicked: (String) -> Unit
) {
    Column {
        // this will recompose when [header] changes, but not when [names] changes
        Text(header, style = MaterialTheme.typography.bodyLarge)
        Divider()

        // LazyColumn is the Compose version of a RecyclerView.
        // The lambda passed to items() is similar to a RecyclerView.ViewHolder.
        LazyColumn {
            items(names) { name ->
                // When an item's [name] updates, the adapter for that item
                // will recompose. This will not recompose when [header] changes
                NamePickerItem(name, onNameClicked)
            }
        }
    }
}

@Composable
private fun NamePickerItem(name: String, onClicked: (String) -> Unit) {
    Text(name, Modifier.clickable(onClick = { onClicked(name) }))
}

@SingleDevicePreview
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