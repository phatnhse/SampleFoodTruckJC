package com.phatnhse.sample_food_truck_jc.food_truck_kit.brand

import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.R
import com.phatnhse.sample_food_truck_jc.food_truck_kit.brand.HeaderSize.REDUCE
import com.phatnhse.sample_food_truck_jc.food_truck_kit.brand.HeaderSize.STANDARD
import com.phatnhse.sample_food_truck_jc.utils.MultipleDevices
import com.phatnhse.sample_food_truck_jc.ui.theme.SkyEnd
import com.phatnhse.sample_food_truck_jc.ui.theme.SkyStart

@Composable
fun BrandHeader(
    modifier: Modifier = Modifier,
    animated: Boolean = true,
    headerSize: HeaderSize = STANDARD
) {
    val skyGradient = listOf(SkyStart, SkyEnd)
    val scale = headerSize.value
    val resources = LocalContext.current.resources

    val backgroundLayers = backgroundLayers.map {
        val backgroundLayer = it.toCanvasLayer(resources)
        val transition = if (animated) {
            rememberInfiniteTransition().animateFloat(
                initialValue = 0F, targetValue = 360F, animationSpec = infiniteRepeatable(
                    animation = tween(it.duration, easing = LinearEasing)
                )
            )
        } else null

        backgroundLayer to transition
    }

    val road = road.toCanvasLayer(resources)

    val truckLayer = truckFrames.map { it.toCanvasLayer(resources) }
    val truckTransition = if (animated) {
        rememberInfiniteTransition().animateFloat(
            initialValue = 1F, targetValue = 12F, animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing)
            )
        )
    } else null

    val foregroundLayer = foreground.toCanvasLayer(resources)
    val foregroundTransition = if (animated) {
        rememberInfiniteTransition().animateFloat(
            initialValue = 0F, targetValue = 360F, animationSpec = infiniteRepeatable(
                animation = tween(foreground.duration, easing = LinearEasing)
            )
        )
    } else null

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clipToBounds()
            .height(160.dp * headerSize.value)
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawRect(
                brush = Brush.radialGradient(
                    colors = skyGradient, center = Offset(
                        x = size.center.x * 0.5F, y = 400F * scale
                    ), radius = 600F
                )
            )
        }

        val canvasSize = (-160).dp * headerSize.value
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(
                        x = 0, y = canvasSize.roundToPx()
                    )
                }
        ) {
            val centerX = size.width * 0.5F
            val yOffsetBackground = if (density < 3.0F) 420.dp.toPx() else 460.dp.toPx()
            val yOffsetRoad = if (density < 3.0F) 280.dp.toPx() else 295.dp.toPx()
            val yOffsetTruck = if (density < 3.0F) 265.dp.toPx() else 275.dp.toPx()
            val yOffsetForeground = if (density < 3.0F) 440.dp.toPx() else 480.dp.toPx()

            // draw background
            backgroundLayers.map { (layer, rotation) ->
                withTransform({
                    translate(
                        left = centerX, top = yOffsetBackground * scale
                    )
                    rotation?.let {
                        rotate(
                            degrees = -it.value, pivot = layer.rect.center
                        )
                    }
                    scale(scaleX = scale, scaleY = scale, pivot = layer.rect.center)
                }) {
                    drawImage(
                        image = layer.image, topLeft = layer.rect.topLeft
                    )
                }
            }

            // draw road
            withTransform({
                translate(
                    left = centerX, top = yOffsetRoad * scale
                )
                scale(scaleX = scale, scaleY = scale, pivot = road.rect.center)
            }) {
                drawImage(image = road.image, topLeft = road.rect.topLeft)
            }

            // draw truck
            withTransform({
                translate(
                    left = centerX, top = yOffsetTruck * scale
                )
                scale(scaleX = scale, scaleY = scale, pivot = truckLayer[0].rect.center)
            }) {
                var frame = 0
                truckTransition?.let {
                    frame = truckTransition.value.toInt() % truckFrames.size
                }

                val truck = truckLayer[frame]
                drawImage(
                    image = truck.image, topLeft = truck.rect.topLeft
                )
            }

            // draw foreground
            withTransform({
                translate(
                    left = centerX, top = yOffsetForeground * scale
                )

                foregroundTransition?.let {
                    rotate(-it.value, foregroundLayer.rect.center)
                }

                scale(scaleX = scale, scaleY = scale, pivot = foregroundLayer.rect.center)
            }) {
                drawImage(
                    foregroundLayer.image, topLeft = foregroundLayer.rect.topLeft
                )
            }
        }
    }
}

enum class HeaderSize(val value: Float) {
    STANDARD(2F), REDUCE(1F)
}

data class ImageResource(
    @DrawableRes val imageId: Int,
    val duration: Int = 0,
    val yOffset: Int = 0
)

data class RotatedLayer(
    val image: ImageBitmap,
    val rect: Rect
)

fun ImageResource.toCanvasLayer(res: Resources): RotatedLayer {
    val image = (ImageBitmap.imageResource(
        res = res, id = this.imageId
    ))

    val width = image.width.toFloat()
    val height = image.height.toFloat()

    val topLeft = Offset(
        x = -width / 2, y = -height / 2
    )
    val size = Size(width, height)

    return RotatedLayer(
        image = image,
        rect = Rect(offset = topLeft, size = size)
    )
}

val backgroundLayers = listOf(
    ImageResource(
        imageId = R.drawable.small_clouds, duration = 760000
    ), ImageResource(
        imageId = R.drawable.medium_clouds, duration = 720000
    ), ImageResource(
        imageId = R.drawable.mountains, duration = 840000
    ), ImageResource(
        imageId = R.drawable.big_clouds, duration = 400000
    ), ImageResource(
        imageId = R.drawable.ocean, duration = 480000
    ), ImageResource(
        imageId = R.drawable.balloons_and_ships, duration = 540000
    ), ImageResource(
        imageId = R.drawable.tree_background, duration = 180000
    )
)

val road = ImageResource(
    imageId = R.drawable.road
)

val foreground = ImageResource(
    imageId = R.drawable.foreground, duration = 96000
)

// MARK: 250 ms per frame
val truckFrames = listOf(
    ImageResource(
        imageId = R.drawable.food_truck_frame_1
    ), ImageResource(
        imageId = R.drawable.food_truck_frame_2
    ), ImageResource(
        imageId = R.drawable.food_truck_frame_3
    ), ImageResource(
        imageId = R.drawable.food_truck_frame_4
    )
)


@MultipleDevices
@Composable
fun Preview() {
    Column {
        BrandHeader(animated = true, headerSize = STANDARD)
        BrandHeader(animated = true, headerSize = REDUCE)
    }
}