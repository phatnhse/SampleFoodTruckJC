package com.phatnhse.sample_food_truck_jc.food_truck_kit.donut

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.SingleDevice
import java.util.UUID

class OrderDetail(
    val index: Int,
    val donuts: List<Donut>
)

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun DonutStackView(
    modifier: Modifier = Modifier,
    orderDetail: OrderDetail,
    includeOverflowCount: Boolean = false
) {
    /*Log.d("nhp", orderDetail.index.toString())

    AnimatedContent(
        targetState = orderDetail.index,
        transitionSpec = {
            ContentTransform(
                targetContentEnter = slideInHorizontally(),
                initialContentExit = slideOut(targetOffset = { IntOffset(x = 0, y = 0) })
            )
        }
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.BottomEnd
        ) {
            DiagonalDonutStackLayout {
                orderDetail.donuts.map {
                    DonutView(donut = it)
                }
            }

            val extra = orderDetail.donuts.count() - 3
            if (extra > 0 && includeOverflowCount) {
                Box(
                    modifier = Modifier
                        .size(23.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .zIndex(1000F),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+${extra}",
                        color = Color.DarkGray,
                        fontSize = 8.sp
                    )
                }
            }
        }
    }*/

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        DiagonalDonutStackLayout {
            orderDetail.donuts.map {
                DonutView(donut = it)
            }
        }

        val extra = orderDetail.donuts.count() - 3
        if (extra > 0 && includeOverflowCount) {
            Box(
                modifier = Modifier
                    .size(23.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .zIndex(1000F),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+${extra}",
                    color = Color.DarkGray,
                    fontSize = 8.sp
                )
            }
        }
    }
}

@SingleDevice
@Composable
fun DonutStackView_Preview() {
    Column {
        DonutStackView(
            modifier = Modifier
                .height(120.dp)
                .width(120.dp),
            orderDetail = OrderDetail(
                index = 0,
                donuts = Donut.all
            ),
            includeOverflowCount = true
        )
    }
}