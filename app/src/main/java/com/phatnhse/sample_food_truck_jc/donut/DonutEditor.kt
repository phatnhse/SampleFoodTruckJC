package com.phatnhse.sample_food_truck_jc.donut

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Donut
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutView
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.Flavor
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.FlavorPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Dough
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Glaze
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Ingredient
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Topping
import com.phatnhse.sample_food_truck_jc.foodtruck.general.arrowDownPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.arrowUpDownPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.starPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.model.FoodTruckViewModel
import com.phatnhse.sample_food_truck_jc.navigation.NavigationHeader
import com.phatnhse.sample_food_truck_jc.ui.composable.CustomDivider
import com.phatnhse.sample_food_truck_jc.ui.composable.EmptySection
import com.phatnhse.sample_food_truck_jc.ui.composable.Section
import com.phatnhse.sample_food_truck_jc.ui.composable.noRippleClickable
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeSmall
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeTiny
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingSmall
import com.phatnhse.sample_food_truck_jc.ui.theme.ShapeRoundedLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.ShapeRoundedNormal
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevice

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DonutEditor(
    onBackPressed: () -> Unit,
    createNewDonut: Boolean,
    donutId: Int,
    model: FoodTruckViewModel
) {
    var donut by remember {
        val current = if (createNewDonut) {
            model.newDonut
        } else {
            model.donut(donutId)
        }
        mutableStateOf(current)
    }

    var actionExpanded by remember {
        mutableStateOf(false)
    }

    val (topFlavor, topFlavorValue) = donut.flavors.mostPotent

    LaunchedEffect(donut) {
        if (!createNewDonut) {
            val donutIndex = model.findDonutIndex(donutId)
            model.donuts[donutIndex] = donut
        }
    }

    Column(
        modifier = Modifier
            .background(colorScheme.background)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavigationHeader(
            previousViewTitle = "",
            currentViewTitle = "",
            onBackPressed = onBackPressed,
            menuItems = {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .noRippleClickable {
                                actionExpanded = true
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = donut.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.width(PaddingSmall))

                        Box(
                            modifier = Modifier
                                .background(
                                    color = colorScheme.onBackground.copy(alpha = 0.1F),
                                    shape = ShapeRoundedLarge
                                )
                                .padding(3.dp),
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(IconSizeTiny),
                                painter = arrowDownPainter(),
                                contentDescription = null,
                                tint = colorScheme.onBackground.copy(alpha = 0.6F)
                            )
                        }
                    }

                    if (actionExpanded) {
                        Popup(
                            alignment = Alignment.Center,
                            offset = IntOffset(0, 120),
                            properties = PopupProperties(
                                usePlatformDefaultWidth = true
                            ),
                            onDismissRequest = {
                                actionExpanded = false
                            }
                        ) {
                            Card {
                                Row(
                                    modifier = Modifier
                                        .defaultMinSize(minWidth = 200.dp)
                                        .padding(PaddingNormal),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "My Action",
                                        fontSize = 14.sp,
                                    )
                                    Icon(
                                        modifier = Modifier.size(IconSizeSmall),
                                        painter = starPainter(),
                                        contentDescription = "My Action"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingNormal),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = PaddingNormal),
                contentAlignment = Alignment.Center
            ) {
                DonutView(
                    modifier = Modifier.size(80.dp), donut = donut
                )
            }
        }

        Section(
            title = "Donut",
            rows = listOf(donut.name),
            showExpandableIcon = false,
            useArrowTrailingViewAsDefault = false
        )

        EmptySection(
            title = "Flavor Profile",
            showExpandableIcon = false,
        ) {
            Flavor.values().map { flavor ->
                val isTopFlavor = topFlavor == flavor
                val flavorValue = donut.flavors[flavor]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = PaddingNormal,
                            vertical = PaddingSmall
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier.weight(5F),
                    ) {
                        Icon(
                            modifier = Modifier.size(IconSizeSmall),
                            painter = FlavorPainter(
                                flavor = flavor
                            ),
                            contentDescription = "Flavor ${flavor.displayName}",
                            tint = colorScheme.onBackground.copy(
                                alpha = if (isTopFlavor) {
                                    1F
                                } else {
                                    0.5F
                                }
                            )
                        )
                        Spacer(modifier = Modifier.width(PaddingNormal))
                        Text(
                            text = flavor.displayName,
                            fontSize = 14.sp,
                            color = colorScheme.onBackground.copy(
                                alpha = if (isTopFlavor) {
                                    1F
                                } else {
                                    0.5F
                                }
                            )
                        )
                    }

                    LinearProgressIndicator(
                        modifier = Modifier
                            .weight(12F)
                            .height(PaddingNormal)
                            .clip(ShapeRoundedNormal),
                        progress = flavorValue / topFlavorValue.toFloat(),
                        color = if (isTopFlavor) {
                            colorScheme.primary
                        } else {
                            colorScheme.onBackground.copy(
                                alpha = 0.5F
                            )
                        }
                    )
                    Text(
                        modifier = Modifier.weight(1F),
                        textAlign = TextAlign.End,
                        text = flavorValue.toString()
                    )
                }
            }
        }

        EmptySection(
            title = "Ingredients",
            showExpandableIcon = false,
        ) {
            IngredientSection(
                donut = donut,
                onDoughChanged = {
                    donut = donut.copy(dough = it)
                },
                onGlazeChanged = {
                    donut = donut.copy(glaze = it)
                },
                onToppingChanged = {
                    donut = donut.copy(topping = it)
                }
            )
        }
    }
}

@Composable
fun IngredientSection(
    donut: Donut,
    onDoughChanged: (Dough) -> Unit,
    onGlazeChanged: (Glaze?) -> Unit,
    onToppingChanged: (Topping?) -> Unit
) {
    Column {
        IngredientRow(
            title = "Dough",
            selectedIngredient = donut.dough,
            selectionGroups = listOf(Dough.all),
            addNoneOption = false
        ) { ingredient ->
            onDoughChanged(ingredient as Dough)
        }

        CustomDivider()

        IngredientRow(
            title = "Glaze",
            selectedIngredient = donut.glaze,
            selectionGroups = listOf(Glaze.all),
            addNoneOption = true
        ) { ingredient ->
            onGlazeChanged(ingredient as? Glaze?)
        }

        CustomDivider()

        IngredientRow(
            title = "Topping",
            selectedIngredient = donut.topping,
            selectionGroups = Topping.all,
            addNoneOption = true
        ) { ingredient ->
            onToppingChanged(ingredient as? Topping?)
        }
    }
}

@Composable
fun IngredientRow(
    title: String,
    selectedIngredient: Ingredient?,
    selectionGroups: List<List<Ingredient>>,
    addNoneOption: Boolean,
    newIngredientSelected: (Ingredient?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PaddingNormal),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title, fontSize = 14.sp
        )

        Row(
            modifier = Modifier.noRippleClickable {
                expanded = !expanded
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(PaddingNormal),
                text = selectedIngredient?.name ?: "",
                color = colorScheme.onBackground.copy(
                    alpha = 0.5F
                ),
                fontSize = 14.sp
            )

            Icon(
                painter = arrowUpDownPainter(),
                contentDescription = "Open dropdown menu",
                tint = colorScheme.onBackground.copy(
                    alpha = 0.5F
                )
            )

            DropdownMenu(
                modifier = Modifier
                    .defaultMinSize(minWidth = 200.dp)
                    .background(
                        color = colorScheme.background, shape = ShapeRoundedLarge
                    ),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                if (addNoneOption) {
                    Column {
                        InlineDropdownMenuIcon(
                            isChecked = selectedIngredient == null,
                            text = "None",
                            showDivider = false
                        ) {
                            expanded = false
                            newIngredientSelected(null)
                        }

                        CustomDivider(thickness = PaddingSmall)
                    }
                }

                selectionGroups.forEachIndexed { groupIndex, ingredients ->
                    Column {
                        ingredients.forEachIndexed { index, ingredient ->
                            InlineDropdownMenuIcon(
                                isChecked = selectedIngredient == ingredient,
                                text = ingredient.name,
                                showDivider = index != ingredients.lastIndex,
                            ) {
                                expanded = false
                                newIngredientSelected(ingredient)
                            }
                        }

                        if (groupIndex != selectionGroups.lastIndex) {
                            CustomDivider(thickness = PaddingSmall)
                        }
                    }
                }
            }
        }
    }
}

@SingleDevice
@Composable
fun DonutEditor_Preview() {
    PreviewSurface {
        DonutEditor(
            onBackPressed = {},
            donutId = Donut.classic.id,
            createNewDonut = true,
            model = FoodTruckViewModel()
        )
    }
}