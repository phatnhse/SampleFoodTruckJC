package com.phatnhse.sample_food_truck_jc.brand

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.R
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme
import com.phatnhse.sample_food_truck_jc.ui.theme.SkyEnd
import com.phatnhse.sample_food_truck_jc.ui.theme.SkyStart

@Composable
fun BrandHeader(
    animated: Boolean,
    headerSize: HeaderSize
) {
    val skyGradient = listOf(SkyStart, SkyEnd)
    val scale = headerSize.value
    val resources = LocalContext.current.resources

    val backgroundLayers = backgroundLayers.map {
        val backgroundLayer = it.toCanvasLayer(resources)
        val backgroundRotation = if (animated) {
            rememberInfiniteTransition().animateFloat(
                initialValue = 0F, targetValue = 360F, animationSpec = infiniteRepeatable(
                    animation = tween(it.duration, easing = LinearEasing)
                )
            )
        } else null

        backgroundLayer to backgroundRotation
    }

    val road = road.toCanvasLayer(resources)

    val truckImage = truckFrames.map { it.toCanvasLayer(resources) }
    val truckFrameChange = if (animated) {
        rememberInfiniteTransition().animateFloat(
            // 12 frames per second
            initialValue = 0F, targetValue = 12F, animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing)
            )
        )
    } else null

    val foreground = foreGround.toCanvasLayer(resources)
    val foregroundRotation = if (animated) {
        rememberInfiniteTransition().animateFloat(
            initialValue = 0F, targetValue = 360F, animationSpec = infiniteRepeatable(
                animation = tween(foreGround.duration, easing = LinearEasing)
            )
        )
    } else null

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp * headerSize.value)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                brush = Brush.radialGradient(
                    colors = skyGradient, center = Offset(
                        x = size.center.x * 0.5F, y = 400F * scale
                    ), radius = 600F
                )
            )
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = 0.dp, y = (-240).dp)
        ) {
            val centerX = size.width * 0.5F
            val yOffset = if (headerSize == HeaderSize.REDUCE) 300 else 0

            // draw background
            backgroundLayers.map { (layer, rotation) ->
                withTransform({
                    translate(
                        left = centerX,
                        top = 1920 * scale + yOffset
                    )
                    rotation?.let {
                        rotate(
                            degrees = -it.value,
                            pivot = layer.rect.center
                        )
                    }
                    scale(scaleX = scale, scaleY = scale, pivot = layer.rect.center)
                }) {
                    drawImage(
                        image = layer.image,
                        topLeft = layer.rect.topLeft
                    )
                }
            }

            // draw road
            withTransform({
                translate(
                    left = centerX,
                    top = 1150 * scale + yOffset
                )
                scale(scaleX = scale, scaleY = scale, pivot = road.rect.center)
            }) {
                drawImage(
                    image = road.image,
                    topLeft = road.rect.topLeft
                )
            }

            // draw truck
            withTransform({
                translate(
                    left = centerX,
                    top = 1050 * scale + yOffset
                )
                scale(scaleX = scale, scaleY = scale, pivot = truckImage[0].rect.center)
            }) {
                var frame = 0
                truckFrameChange?.let {
                    frame = truckFrameChange.value.toInt() % truckFrames.size
                }

                val truck = truckImage[frame]
                drawImage(
                    image = truck.image,
                    topLeft = truck.rect.topLeft
                )
            }

            // draw foreground
            withTransform({
                translate(
                    left = centerX,
                    top = 2060 * scale + yOffset
                )

                foregroundRotation?.let {
                    rotate(-it.value, foreground.rect.center)
                }

                scale(scaleX = scale, scaleY = scale, pivot = foreground.rect.center)
            }) {
                drawImage(
                    foreground.image, topLeft = foreground.rect.topLeft
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BrandHeader_Preview() {
    SampleFoodTruckJCTheme {
        Column {
            BrandHeader(
                animated = true, headerSize = HeaderSize.REDUCE
            )

            BrandHeader(
                animated = true, headerSize = HeaderSize.STANDARD
            )
        }
    }
}

enum class HeaderSize(val value: Float) {
    STANDARD(1.0F), REDUCE(0.5F)
}

data class ImageResource(
    @DrawableRes val imageId: Int, val duration: Int = 0
)

data class RotatedLayer(
    val image: ImageBitmap, val rect: Rect
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
        image = image, rect = Rect(offset = topLeft, size = size)
    )
}


val backgroundLayers = listOf(
    ImageResource(
        imageId = R.mipmap.small_clouds, duration = 760000
    ), ImageResource(
        imageId = R.mipmap.medium_clouds, duration = 720000
    ), ImageResource(
        imageId = R.mipmap.mountains, duration = 840000
    ), ImageResource(
        imageId = R.mipmap.big_clouds, duration = 400000
    ), ImageResource(
        imageId = R.mipmap.ocean, duration = 480000
    ), ImageResource(
        imageId = R.mipmap.balloons, duration = 540000
    ), ImageResource(
        imageId = R.mipmap.tree, duration = 180000
    )
)

val road = ImageResource(
    imageId = R.mipmap.road
)


val foreGround = ImageResource(
    imageId = R.mipmap.foreground, duration = 96000
)

// MARK: 250 ms per frame
val truckFrames = listOf(
    ImageResource(
        imageId = R.mipmap.foodtruck_frame_1
    ), ImageResource(
        imageId = R.mipmap.foodtruck_frame_2
    ), ImageResource(
        imageId = R.mipmap.foodtruck_frame_3
    ), ImageResource(
        imageId = R.mipmap.foodtruck_frame_4
    )
)