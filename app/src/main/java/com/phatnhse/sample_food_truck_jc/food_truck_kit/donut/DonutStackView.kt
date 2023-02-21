package com.phatnhse.sample_food_truck_jc.food_truck_kit.donut

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.SingleDevice

class OrderDetail(val donuts: List<Donut>)

@SuppressLint("UnrememberedMutableState")
@Composable
fun DonutStackView(
    modifier: Modifier = Modifier,
    orderDetail: OrderDetail,
    includeOverflowCount: Boolean = false
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.secondaryContainer
        )
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
                        .background(colorScheme.surfaceVariant)
                        .zIndex(1000F),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+${extra}",
                        style = typography.labelSmall.copy(
                            fontSize = 8.sp
                        )
                    )
                }
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
                donuts = Donut.all
            ),
            includeOverflowCount = true
        )
    }
}