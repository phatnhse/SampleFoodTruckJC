package com.phatnhse.sample_food_truck_jc.donut

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.phatnhse.sample_food_truck_jc.donut.BrowserLayout.GRID
import com.phatnhse.sample_food_truck_jc.donut.BrowserLayout.LIST
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Flavor
import com.phatnhse.sample_food_truck_jc.foodtruck.general.checkMarkPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.forkKnifePainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.listBulletPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.plusPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.squareGridPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.textFormatPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.trophyPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.model.DonutSortOrder
import com.phatnhse.sample_food_truck_jc.foodtruck.model.FoodTruckViewModel
import com.phatnhse.sample_food_truck_jc.foodtruck.model.Timeframe
import com.phatnhse.sample_food_truck_jc.navigation.NavigationHeader
import com.phatnhse.sample_food_truck_jc.ui.composable.SearchView
import com.phatnhse.sample_food_truck_jc.ui.composable.noRippleClickable
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeSmaller
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingLarge
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
    // filter
    var expanded by remember { mutableStateOf(false) }
    var layout by remember { mutableStateOf(GRID) }
    var sort by remember { mutableStateOf<DonutSortOrder>(DonutSortOrder.SortByPopularity(Timeframe.WEEK)) }
    var popularityTimeframe by remember { mutableStateOf(Timeframe.WEEK) }
    var sortFlavor by remember { mutableStateOf(Flavor.Sweet) }

    // search
    var searchText by remember { mutableStateOf("") }
    val filterDonuts = model.donuts(sort).filter {
        it.matches(searchText = searchText)
    }

    @Composable
    fun FilterMenu(
        modifier: Modifier = Modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        Box(
            modifier = modifier
        ) {
            Icon(painter = squareGridPainter(),
                contentDescription = "More",
                tint = colorScheme.primary,
                modifier = Modifier.noRippleClickable {
                    expanded = !expanded
                })

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                BrowserLayout.values().forEach {
                    InlineDropdownMenuIcon(
                        isChecked = layout == it,
                        text = it.title,
                        painter = getPainter(layout = it),
                    ) {
                        layout = it
                        expanded = false
                    }
                }

                InlineDropdownMenuIcon(
                    isChecked = sort == DonutSortOrder.SortByName,
                    text = "Name",
                    painter = textFormatPainter()
                ) {
                    sort = DonutSortOrder.SortByName
                    expanded = false
                }

                InlineDropdownMenuIcon(
                    isChecked = sort == DonutSortOrder.SortByPopularity(popularityTimeframe),
                    text = "Popularity",
                    painter = forkKnifePainter()
                ) {
                    sort = DonutSortOrder.SortByPopularity(popularityTimeframe)
                    expanded = false
                }

                InlineDropdownMenuIcon(
                    isChecked = sort == DonutSortOrder.SortByFlavor(sortFlavor),
                    text = "Flavor",
                    painter = trophyPainter()
                ) {
                    sort = DonutSortOrder.SortByFlavor(sortFlavor)
                    expanded = false
                }

                when (sort) {
                    is DonutSortOrder.SortByFlavor -> {
                        Flavor.values().forEach {
                            InlineDropdownMenuIcon(
                                isChecked = sortFlavor == it,
                                text = it.displayName
                            ) {
                                sortFlavor = it
                                sort = DonutSortOrder.SortByFlavor(sortFlavor)
                                expanded = false
                            }
                        }
                    }

                    is DonutSortOrder.SortByPopularity -> {
                        Timeframe.values().forEach {
                            InlineDropdownMenuIcon(
                                isChecked = popularityTimeframe == it,
                                text = it.title
                            ) {
                                popularityTimeframe = it
                                sort = DonutSortOrder.SortByPopularity(popularityTimeframe)
                                expanded = false
                            }
                        }
                    }

                    DonutSortOrder.SortByName -> {}
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.background)
    ) {
        NavigationHeader(previousViewTitle = previousViewTitle,
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

                    Spacer(modifier = Modifier.width(PaddingLarge))

                    FilterMenu()
                }
            })
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

@Composable
fun InlineDropdownMenuIcon(
    isChecked: Boolean,
    text: String,
    painter: Painter? = null,
    onClick: () -> Unit
) {
    Column {
        DropdownMenuItem(
            leadingIcon = if (isChecked) {
                {
                    Icon(
                        modifier = Modifier.size(IconSizeSmaller),
                        painter = checkMarkPainter(),
                        contentDescription = "$text selected",
                        tint = colorScheme.onBackground
                    )
                }
            } else null,
            trailingIcon = if (painter != null) {
                {
                    Icon(
                        modifier = Modifier.size(IconSizeSmaller),
                        painter = painter,
                        contentDescription = text,
                        tint = colorScheme.onBackground
                    )
                }
            } else null,
            text = {
                Text(text = text, style = MaterialTheme.typography.titleSmall)
            },
            onClick = onClick
        )
    }
}

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