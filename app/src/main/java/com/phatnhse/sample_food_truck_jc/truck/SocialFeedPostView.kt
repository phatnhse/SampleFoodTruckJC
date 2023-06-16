package com.phatnhse.sample_food_truck_jc.truck

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.DonutView
import com.phatnhse.sample_food_truck_jc.order.formattedDate
import com.phatnhse.sample_food_truck_jc.truck.cards.FlowLayout
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeExtraLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingSmall
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingTiny
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview

@Composable
fun SocialFeedPostView(post: SocialFeedPost) {
    Column {
        Row {
            Box(
                modifier = Modifier
                    .padding(PaddingSmall)
                    .background(
                        color = post.favoriteDonut.dough.backgroundColorName(isSystemInDarkTheme()),
                        shape = CircleShape
                    )
            ) {
                DonutView(
                    modifier = Modifier
                        .size(IconSizeExtraLarge)
                        .padding(PaddingTiny)
                        .align(Alignment.Center),
                    donut = post.favoriteDonut
                )
            }
            Column {
                Text(modifier = Modifier.padding(PaddingSmall), text = post.message)
                FlowLayout(alignCenter = false) {
                    post.tags.map {
                        SocialFeedTagView(
                            socialFeedTag = it,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
                Text(
                    modifier = Modifier.padding(start = PaddingSmall),
                    text = post.date.formattedDate(),
                    color = colorScheme.onBackground.copy(
                        alpha = 0.5F
                    ),
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.padding(PaddingSmall))
            }
        }
    }
}

@SingleDevicePreview
@Composable
fun SocialFeedPostView_Preview() {
    PreviewSurface {
        SocialFeedPostView(
            SocialFeedPost.socialFeedPlusContent[0]
        )
    }
}