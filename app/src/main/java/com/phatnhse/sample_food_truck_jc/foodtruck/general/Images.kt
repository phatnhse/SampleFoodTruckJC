package com.phatnhse.sample_food_truck_jc.foodtruck.general

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.phatnhse.sample_food_truck_jc.R

@Composable
fun truckSymbol(): Painter {
    return painterResource(id = R.drawable.truck)
}

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
fun arrowRightSymbol(): Painter {
    return painterResource(id = R.drawable.chevron_right)
}

@Composable
fun arrowLeftSymbol(): Painter {
    return painterResource(id = R.drawable.chevron_left)
}

@Composable
fun checkmarkCircleSymbol(): Painter {
    return painterResource(id = R.drawable.checkmark)
}


@Composable
fun paperplaneSymbol(): Painter {
    return painterResource(id = R.drawable.paperplane)
}


@Composable
fun timerSymbol(): Painter {
    return painterResource(id = R.drawable.timer)
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
fun trophySymbol(): Painter {
    return painterResource(id = R.drawable.trophy)
}

@Composable
fun clockSymbol(): Painter {
    return painterResource(id = R.drawable.clock)
}

@Composable
fun sliderSymbol(): Painter {
    return painterResource(id = R.drawable.slider)
}

@Composable
fun searchSymbol(): Painter {
    return painterResource(id = R.drawable.search)
}

//@Composable
//fun flavorSymbol(flavor: Flavor): Painter {
//    return when (flavor) {
//        is Bitter -> painterResource(id = R.drawable.bitter)
//        is Salty -> painterResource(id = R.drawable.salty)
//        is Savory -> painterResource(id = R.drawable.savory)
//        is Sour -> painterResource(id = R.drawable.sour)
//        is Spicy -> painterResource(id = R.drawable.spicy)
//        is Sweet -> painterResource(id = R.drawable.sweet)
//    }
//}
