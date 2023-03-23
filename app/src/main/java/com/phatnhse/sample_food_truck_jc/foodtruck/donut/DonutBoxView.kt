package com.phatnhse.sample_food_truck_jc.foodtruck.donut

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.R
import com.phatnhse.sample_food_truck_jc.ui.composable.noRippleClickable
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingLarge
import com.phatnhse.sample_food_truck_jc.utils.SingleDevice


@Composable
fun DonutBoxView(
    modifier: Modifier = Modifier, isOpen: Boolean, content: @Composable () -> Unit
) {
    val boxInside: Painter = painterResource(id = R.drawable.box_inside)
    val boxBottom: Painter = painterResource(id = R.drawable.box_bottom)
    val boxLid: Painter = painterResource(id = R.drawable.box_lid)

    val updateTransition = updateTransition(targetState = isOpen, label = "")
    val scale = updateTransition.animateFloat(label = "") {
        when (it) {
            true -> 1F
            false -> 0.75F
        }
    }

    val boxYOffset = updateTransition.animateDp(
        label = ""
    ) {
        when (it) {
            true -> 0.dp
            false -> (-200).dp
        }
    }

    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Image(
            painter = boxInside,
            contentDescription = "Box Inside",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
        content()
        Image(
            painter = boxBottom,
            contentDescription = "Box Bottom",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
        if (isOpen) {
            Image(
                painter = boxLid,
                contentDescription = "Box Lid",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(scale.value)
                    .offset(y = boxYOffset.value)
            )
        }
    }
}

@SingleDevice
@Composable
fun DonutBoxView_Preview() {
    var open by remember {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable {
                open = !open
            },
        contentAlignment = Alignment.Center
    ) {
        DonutBoxView(
            modifier = Modifier.size(300.dp),
            isOpen = open
        ) {
            DonutView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PaddingLarge), donut = Donut.preview
            )
        }
    }
}