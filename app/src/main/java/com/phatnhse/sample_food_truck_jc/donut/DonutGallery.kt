package com.phatnhse.sample_food_truck_jc.donut

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.donut.BrowserLayout.GRID
import com.phatnhse.sample_food_truck_jc.donut.BrowserLayout.LIST
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Donut
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutView
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
import com.phatnhse.sample_food_truck_jc.ui.composable.CustomDivider
import com.phatnhse.sample_food_truck_jc.ui.composable.SearchView
import com.phatnhse.sample_food_truck_jc.ui.composable.noRippleClickable
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeSmaller
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeTiny
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.ShapeRoundedLarge
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview

@Composable
fun DonutGallery(
    previousViewTitle: String,
    currentViewTitle: String,
    onBackClicked: () -> Unit,
    onDonutClicked: (Donut) -> Unit,
    onNewDonutClicked: () -> Unit,
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
                contentDescription = "Filter donuts",
                tint = colorScheme.primary,
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .noRippleClickable {
                        expanded = !expanded
                    })

            DropdownMenu(modifier = Modifier
                .defaultMinSize(minWidth = 200.dp)
                .background(
                    color = colorScheme.background, shape = ShapeRoundedLarge
                ),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                BrowserLayout.values().forEachIndexed { index, browserLayout ->
                    InlineDropdownMenuIcon(
                        isChecked = layout == browserLayout,
                        text = browserLayout.title,
                        painter = getPainter(layout = browserLayout)
                    ) {
                        layout = browserLayout
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
                    painter = trophyPainter(),
                    showDivider = sort != DonutSortOrder.SortByName
                ) {
                    sort = DonutSortOrder.SortByFlavor(sortFlavor)
                    expanded = false
                }

                when (sort) {
                    is DonutSortOrder.SortByFlavor -> {
                        Flavor.values().forEachIndexed { index, flavorValue ->
                            InlineDropdownMenuIcon(
                                isChecked = sortFlavor == flavorValue,
                                text = flavorValue.displayName,
                                showDivider = index != Flavor.values().lastIndex
                            ) {
                                sortFlavor = flavorValue
                                sort = DonutSortOrder.SortByFlavor(sortFlavor)
                                expanded = false
                            }
                        }
                    }

                    is DonutSortOrder.SortByPopularity -> {
                        Timeframe.values().forEachIndexed { index, timeframe ->
                            InlineDropdownMenuIcon(
                                isChecked = popularityTimeframe == timeframe,
                                text = timeframe.title,
                                showDivider = index != Timeframe.values().lastIndex
                            ) {
                                popularityTimeframe = timeframe
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
        NavigationHeader(
            previousViewTitle = previousViewTitle,
            currentViewTitle = currentViewTitle,
            onBackPressed = onBackClicked,
            menuItems = {
                Row {
                    Icon(
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .noRippleClickable {
                                onNewDonutClicked()
                            },
                        painter = plusPainter(),
                        contentDescription = "Add Donut",
                        tint = colorScheme.primary
                    )

                    FilterMenu()
                }
            })

        SearchView(modifier = Modifier.padding(PaddingNormal), onSearch = {
            searchText = it
        }, onCancel = {
            searchText = ""
        })

        when (layout) {
            GRID -> {
                DonutGalleryGrid(
                    donuts = filterDonuts,
                    onDonutClicked = onDonutClicked
                )
            }

            LIST -> {
                DonutGalleryList(donuts = filterDonuts)
            }
        }
    }
}

@Composable
fun DonutGalleryList(donuts: List<Donut>) {
    LazyColumn(
    ) {
        itemsIndexed(
            items = donuts,
            key = { index, donut -> donut.id }
        ) { index, donut ->
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .clickable {

                    }
                    .padding(horizontal = PaddingNormal),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DonutView(modifier = Modifier.size(48.dp), donut = donut)

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(PaddingLarge),
                            text = donut.name
                        )
                    }

                    if (index != donuts.lastIndex) {
                        CustomDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun InlineDropdownMenuIcon(
    isChecked: Boolean,
    text: String,
    painter: Painter? = null,
    showDivider: Boolean = true,
    onClick: () -> Unit
) {
    Column {
        DropdownMenuItem(trailingIcon = if (painter != null) {
            {
                Icon(
                    modifier = Modifier.size(IconSizeSmaller),
                    painter = painter,
                    contentDescription = text,
                    tint = colorScheme.onBackground
                )
            }
        } else null, text = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isChecked) {
                    Icon(
                        modifier = Modifier.size(IconSizeTiny),
                        painter = checkMarkPainter(),
                        contentDescription = "$text selected"
                    )
                } else {
                    Box(modifier = Modifier.size(IconSizeTiny))
                }

                Spacer(modifier = Modifier.width(PaddingNormal))

                Text(text = text, style = MaterialTheme.typography.titleSmall)
            }
        }, onClick = onClick
        )

        if (showDivider) {
            CustomDivider()
        }
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


@SingleDevicePreview
@Composable
fun DonutGallery_Preview() {
    PreviewSurface {
        DonutGallery(
            previousViewTitle = "Food Truck",
            currentViewTitle = "Donuts",
            onBackClicked = {},
            onDonutClicked = {},
            onNewDonutClicked = {},
            model = FoodTruckViewModel.preview,
        )
    }
}