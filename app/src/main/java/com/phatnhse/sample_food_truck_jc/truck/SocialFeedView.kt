package com.phatnhse.sample_food_truck_jc.truck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.navigation.NavigationHeader
import com.phatnhse.sample_food_truck_jc.ui.composable.EmptySection
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.RoundedNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.bottomBarColor
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview

@Composable
fun SocialFeedView(
    previousViewTitle: String = "Food Truck",
    currentViewTitle: String = "Social Feed",
    onBackPressed: () -> Unit,
    posts: List<SocialFeedPost> = SocialFeedPost.standardContent
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        NavigationHeader(
            previousViewTitle = previousViewTitle,
            currentViewTitle = currentViewTitle,
            onBackPressed = onBackPressed
        )

        SocialFeedPlusMarketingView()

        EmptySection(title = "Posts") {
            posts.forEach {
                SocialFeedPostView(post = it)
            }
        }
    }
}

@Composable
fun SocialFeedPlusMarketingView() {
    val gradientColor = listOf(colorScheme.primary, bottomBarColor)
    val pxValue = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingNormal)
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColor,
                    start = Offset(pxValue, 0f),
                    end = Offset(pxValue, 800f)
                ),
                shape = RoundedNormal
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(PaddingNormal))

        Text(
            modifier = Modifier.padding(top = PaddingNormal),
            text = "Get Social Feed+",
            color = colorScheme.onPrimary
        )
        Text(
            modifier = Modifier.padding(top = PaddingNormal),
            text = "The definitive social-feed experience",
            color = colorScheme.onPrimary
        )
        Button(
            modifier = Modifier
                .padding(top = PaddingNormal),
            onClick = { },
            shape = RoundedNormal,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.onPrimary,
                contentColor = colorScheme.primary
            )
        ) {
            Text(text = "Get Started",)
        }

        Spacer(modifier = Modifier.height(PaddingNormal))
    }
}

@SingleDevicePreview
@Composable
fun SocialFeedView_Preview() {
    PreviewSurface {
        SocialFeedView(
            previousViewTitle = "",
            currentViewTitle = "",
            onBackPressed = {}
        )
    }
}