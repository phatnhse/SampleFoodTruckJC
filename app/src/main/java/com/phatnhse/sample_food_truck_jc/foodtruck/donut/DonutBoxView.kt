package com.phatnhse.sample_food_truck_jc.foodtruck.donut

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.phatnhse.sample_food_truck_jc.foodtruck.general.boxBottomPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.boxLidPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.boxPainter
import com.phatnhse.sample_food_truck_jc.ui.composable.noRippleClickable
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview

@Composable
fun DonutBoxView(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val length = min(screenHeight, screenWidth)

    val scale by animateFloatAsState(
        targetValue = when (isOpen) {
            false -> 0.75F
            true -> 1F
        },
        animationSpec = spring()
    )

    val offset by animateDpAsState(
        targetValue = when (isOpen) {
            false -> -length.times(0.5F)
            true -> 0.dp
        },
        animationSpec = spring()
    )

    val opacity by animateFloatAsState(
        targetValue = when (isOpen) {
            false -> 0F
            true -> 1F
        },
        animationSpec = spring()
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = boxPainter(),
            contentDescription = "Box Inside",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
        )
        content()
        Image(
            painter = boxBottomPainter(),
            contentDescription = "Box Bottom",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
        )
        Image(
            modifier = Modifier
                .fillMaxSize()
                .scale(scale)
                .offset(y = offset),
            painter = boxLidPainter(),
            contentDescription = "Box Lid",
            contentScale = ContentScale.Fit,
            alpha = opacity,
        )
    }
}

@SingleDevicePreview
@Composable
fun DonutBoxView_Preview() {
    var open by remember {
        mutableStateOf(false)
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
                    .padding(48.dp),
                donut = Donut.preview
            )
        }
    }
}