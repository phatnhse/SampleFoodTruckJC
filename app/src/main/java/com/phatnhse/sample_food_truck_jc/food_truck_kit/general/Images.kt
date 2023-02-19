package com.phatnhse.sample_food_truck_jc.food_truck_kit.general

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.phatnhse.sample_food_truck_jc.R
import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.Flavor
import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.Flavor.*
import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.ingredient.Ingredient

@Composable
fun donutSymbol(): Painter {
    return painterResource(id = R.drawable.donut)
}

@Composable
fun socialFeedSymbol(): Painter {
    return painterResource(id = R.drawable.text_bubble)
}

@Composable
fun shippingSymbol(): Painter {
    return painterResource(id = R.drawable.shipping_box)
}

@Composable
fun buildingSymbol(): Painter {
    return painterResource(id = R.drawable.building)
}

@Composable
fun tagSymbol(): Painter {
    return painterResource(id = R.drawable.tag)
}

@Composable
fun flavorSymbol(flavor: Flavor): Painter {
    return when (flavor) {
        is Bitter -> painterResource(id = R.drawable.bitter)
        is Salty -> painterResource(id = R.drawable.salty)
        is Savory -> painterResource(id = R.drawable.savory)
        is Sour -> painterResource(id = R.drawable.sour)
        is Spicy -> painterResource(id = R.drawable.spicy)
        is Sweet -> painterResource(id = R.drawable.sweet)
    }
}

@Composable
fun ingredientImage(ingredient: Ingredient, thumbnail: Boolean): Painter {
    val context = LocalContext.current
    val fileName = ingredient.imageResourceName(thumbnail)
    val iconResId: Int = context.resources
        .getIdentifier(fileName, "drawable", context.packageName)

    return painterResource(id = iconResId)
}

