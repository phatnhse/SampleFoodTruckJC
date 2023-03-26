package com.phatnhse.sample_food_truck_jc.donut

import android.inputmethodservice.Keyboard.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.phatnhse.sample_food_truck_jc.donut.BrowserLayout.*
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Flavor
import com.phatnhse.sample_food_truck_jc.foodtruck.general.listBulletPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.plusPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.squareGridPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.model.DonutSortOrder
import com.phatnhse.sample_food_truck_jc.foodtruck.model.FoodTruckViewModel
import com.phatnhse.sample_food_truck_jc.foodtruck.model.Timeframe
import com.phatnhse.sample_food_truck_jc.navigation.NavigationHeader
import com.phatnhse.sample_food_truck_jc.ui.composable.SearchView
import com.phatnhse.sample_food_truck_jc.ui.composable.noRippleClickable
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevice

@Composable
fun DonutGallery(
    previousViewTitle: String,
    currentViewTitle: String,
    onBackPressed: () -> Unit,
    model: FoodTruckViewModel
) {
    var searchText by remember { mutableStateOf("") }
    val donuts = model.donuts
    var expanded by remember { mutableStateOf(false) }
    var layout by remember { mutableStateOf(GRID) }
    var sort by remember {
        mutableStateOf(DonutSortOrder.SortByPopularity(Timeframe.WEEK))
    }
    var popularityTimeframe by remember {
        mutableStateOf(Timeframe.WEEK)
    }
    var sortFlavor by remember {
        mutableStateOf(Flavor.Sweet)
    }

    var filterDonuts = model.donuts(sort).filter {
        it.matches(searchText = searchText)
    }

    var selectedOption by remember { mutableStateOf("Option 1") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.background)
    ) {
        NavigationHeader(
            previousViewTitle = previousViewTitle,
            currentViewTitle = currentViewTitle,
            onBackPressed = onBackPressed,
            menuItems = {
                Row {
                    Icon(
                        modifier = Modifier.noRippleClickable {
                            // TODO Add new donut here
                        },
                        painter = plusPainter(),
                        contentDescription = "Add Donut",
                        tint = colorScheme.primary
                    )

                    Box(
                        modifier = Modifier.wrapContentSize(Alignment.TopEnd)
                    ) {
                        Icon(painter = squareGridPainter(),
                            contentDescription = "More",
                            tint = colorScheme.primary,
                            modifier = Modifier.noRippleClickable {
                                expanded = !expanded
                            })
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(text = { Text("Load") }, onClick = { })
                            DropdownMenuItem(text = { Text("Save") }, onClick = { })
                        }
                    }
                }
            }
        )
        Column {
            SearchView(modifier = Modifier.padding(PaddingNormal), onSearch = {
                searchText = it
            }, onCancel = {
                searchText = ""
            })

            DonutGalleryGrid(donuts = filterDonuts)
        }
    }
}

//fun List<Donut>.search(status: OrderStatus, searchText: String): List<Order> {
//    return this.filter { order ->
//        val searchCondition = order.matches(searchText) ||
//                order.donuts.any {
//                    it.matches(searchText)
//                }
//        order.status == status && searchCondition
//    }
//}


enum class BrowserLayout(val rawValue: String, val title: String) {
    GRID("grid", "Icons"), LIST("list", "List");

    val id: String
        get() = rawValue
}

@Composable
fun getPainter(layout: BrowserLayout): Painter {
    return when (layout) {
        GRID -> squareGridPainter()
        LIST -> listBulletPainter()
    }
}


@SingleDevice
@Composable
fun DonutGallery_Preview() {
    PreviewSurface {
        DonutGallery(
            previousViewTitle = "Food Truck",
            currentViewTitle = "Donuts",
            onBackPressed = {},
            model = FoodTruckViewModel(),
        )
    }
}