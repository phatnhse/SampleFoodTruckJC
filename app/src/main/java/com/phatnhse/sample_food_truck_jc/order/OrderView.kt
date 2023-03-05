package com.phatnhse.sample_food_truck_jc.order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.phatnhse.sample_food_truck_jc.navigation.NavigationHeader
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevice

@Composable
fun OrderView(
    previousViewTitle: String,
    currentViewTitle: String,
    onBackPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(PaddingNormal)
    ) {
        NavigationHeader(
            previousViewTitle = previousViewTitle,
            currentViewTitle = currentViewTitle,
            onBackPressed = onBackPressed
        )
        Text(
            modifier = Modifier.padding(PaddingNormal),
            text = "This is the order book"
        )
    }
}

@SingleDevice
@Composable
fun TruckView_Preview() {
    PreviewSurface {
        OrderView(
            previousViewTitle = "Food Truck",
            currentViewTitle = "Order",
            onBackPressed = {}
        )
    }
}