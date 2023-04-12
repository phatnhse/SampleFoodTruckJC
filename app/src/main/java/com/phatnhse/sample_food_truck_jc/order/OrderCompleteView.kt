package com.phatnhse.sample_food_truck_jc.order

import android.app.Activity
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutBoxView
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutView
import com.phatnhse.sample_food_truck_jc.ui.composable.noRippleClickable
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview
import kotlinx.coroutines.delay
import java.time.LocalDateTime

@Composable
fun OrderCompleteView(
    modifier: Modifier = Modifier,
    order: Order,
    onOrderDone: (Order) -> Unit,
    startAnimation: Boolean,
) {
    // this review function will not working if the app is not on play store
    val localContext = LocalContext.current
    val reviewManager = remember { ReviewManagerFactory.create(localContext) }
    val reviewInfo = rememberReviewTask(reviewManager)
    LaunchedEffect(key1 = reviewInfo) {
        reviewInfo?.let {
            reviewManager.launchReviewFlow(localContext as Activity, reviewInfo)
        }
    }

    var boxOpen by remember { mutableStateOf(false) }
    var boxBounce by remember { mutableStateOf(false) }

    val boxBouncingAnimation by animateDpAsState(
        targetValue = when (boxBounce) {
            false -> 0.dp
            true -> 10.dp
        },
        animationSpec = spring(
            dampingRatio = 0.8F,
            stiffness = Spring.StiffnessMedium
        ),
        finishedListener = {
            boxBounce = false
        }
    )

    LaunchedEffect(boxOpen) {
        if (boxOpen) {
            delay(150)
            boxBounce = true
        }
    }

    fun toggleAnimation() {
        boxOpen = !boxOpen
    }

    LaunchedEffect(startAnimation) {
        if (startAnimation) {
            delay(500)
            toggleAnimation()
        }
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(
                    horizontal = PaddingNormal,
                    vertical = PaddingLarge
                ),
                text = order.id,
                style = typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp,
                    color = colorScheme.onBackground
                )
            )

            Text(
                modifier = Modifier
                    .padding(PaddingNormal)
                    .noRippleClickable {
                        onOrderDone(order)
                    },
                text = "Done",
                color = colorScheme.primary,
                style = typography.titleMedium
            )
        }

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .offset(y = boxBouncingAnimation)
                    .noRippleClickable {
                        toggleAnimation()
                    },
                contentAlignment = Alignment.Center
            ) {
                DonutBoxView(
                    modifier = Modifier.size(300.dp),
                    isOpen = boxOpen
                ) {
                    DonutView(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(48.dp),
                        donut = order.donuts.first()
                    )
                }
            }

            Spacer(Modifier.height(PaddingNormal))

            Text(
                "${order.id} completed!",
                color = colorScheme.onSurface,
                style = typography.titleMedium
            )

            Text(
                "${order.totalSales} donuts • ${LocalDateTime.now()?.formattedDate()}",
                color = colorScheme.onSurface,
                style = typography.bodySmall
            )
        }
    }
}

@Composable
fun rememberReviewTask(reviewManager: ReviewManager): ReviewInfo? {
    var reviewInfo: ReviewInfo? by remember {
        mutableStateOf(null)
    }

    reviewManager.requestReviewFlow().addOnCompleteListener {
        if (it.isSuccessful) {
            reviewInfo = it.result
        }
    }

    return reviewInfo
}

@SingleDevicePreview
@Composable
fun OrderCompleteView_Preview() {
    SampleFoodTruckJCTheme {
        OrderCompleteView(
            order = Order.preview,
            onOrderDone = {},
            startAnimation = false
        )
    }
}