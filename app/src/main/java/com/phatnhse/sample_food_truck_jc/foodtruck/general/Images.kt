package com.phatnhse.sample_food_truck_jc.foodtruck.general

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.phatnhse.sample_food_truck_jc.R

@Composable
fun truckPainter(): Painter {
    return painterResource(id = R.drawable.truck)
}

@Composable
fun donutPainter(): Painter {
    return painterResource(id = R.drawable.donut)
}

@Composable
fun socialFeedPainter(): Painter {
    return painterResource(id = R.drawable.text_bubble)
}

@Composable
fun boxPainter(): Painter {
    return painterResource(id = R.drawable.box_inside)
}

@Composable
fun lockPainter(): Painter {
    return painterResource(id = R.drawable.lock)
}

@Composable
fun boxBottomPainter(): Painter {
    return painterResource(id = R.drawable.box_bottom)
}

@Composable
fun boxLidPainter(): Painter {
    return painterResource(id = R.drawable.box_lid)
}

@Composable
fun shippingPainter(fill: Boolean = false): Painter {
    // TODO consider to load svg by name runtime
    // like "shippingbox" +"_fill+ "_other_extras"
    return if (fill) {
        painterResource(id = R.drawable.shipping_box_fill)
    } else {
        painterResource(id = R.drawable.shipping_box)
    }
}

@Composable
fun arrowRightPainter(): Painter {
    return painterResource(id = R.drawable.chevron_right)
}

@Composable
fun arrowLeftPainter(): Painter {
    return painterResource(id = R.drawable.chevron_left)
}

@Composable
fun checkmarkCirclePainter(): Painter {
    return painterResource(id = R.drawable.checkmark_circle)
}

@Composable
fun checkMarkPainter(): Painter {
    return painterResource(id = R.drawable.checkmark)
}

@Composable
fun paperplanePainter(): Painter {
    return painterResource(id = R.drawable.paperplane)
}


@Composable
fun timerPainter(): Painter {
    return painterResource(id = R.drawable.timer)
}

@Composable
fun buildingPainter(): Painter {
    return painterResource(id = R.drawable.building)
}

@Composable
fun tagPainter(): Painter {
    return painterResource(id = R.drawable.tag)
}

@Composable
fun trophyPainter(): Painter {
    return painterResource(id = R.drawable.trophy)
}

@Composable
fun clockPainter(): Painter {
    return painterResource(id = R.drawable.clock)
}

@Composable
fun sliderPainter(): Painter {
    return painterResource(id = R.drawable.slider)
}

@Composable
fun textFormatPainter(): Painter {
    return painterResource(id = R.drawable.textformat)
}

@Composable
fun searchPainter(): Painter {
    return painterResource(id = R.drawable.search)
}

@Composable
fun arrowUpDownPainter(): Painter {
    return painterResource(id = R.drawable.chevron_up_down)
}

@Composable
fun arrowDownPainter(): Painter {
    return painterResource(id = R.drawable.chevron_down)
}

@Composable
fun starPainter(): Painter {
    return painterResource(id = R.drawable.star)
}

@Composable
fun squareGridPainter(): Painter {
    return painterResource(id = R.drawable.square_grid_2x2)
}

@Composable
fun plusPainter(): Painter {
    return painterResource(id = R.drawable.plus)
}

@Composable
fun listBulletPainter(): Painter {
    return painterResource(id = R.drawable.list_bullet)
}

@Composable
fun forkKnifePainter(): Painter {
    return painterResource(id = R.drawable.fork_knife)
}
