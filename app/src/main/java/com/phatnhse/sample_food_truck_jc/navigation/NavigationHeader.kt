package com.phatnhse.sample_food_truck_jc.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phatnhse.sample_food_truck_jc.foodtruck.general.arrowLeftPainter
import com.phatnhse.sample_food_truck_jc.ui.composable.noRippleClickable
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingSmall
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview

@Composable
fun NavigationHeader(
    modifier: Modifier = Modifier,
    previousViewTitle: String,
    currentViewTitle: String,
    onBackPressed: () -> Unit,
    menuItems: (@Composable () -> Unit)? = null,
) {
    Column {
        Spacer(modifier = Modifier.height(PaddingNormal))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = PaddingNormal),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .noRippleClickable { onBackPressed.invoke() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .height(12.dp)
                        .width(6.dp),
                    painter = arrowLeftPainter(),
                    contentDescription = "Back Icon",
                    colorFilter = ColorFilter.tint(
                        color = colorScheme.primary
                    )
                )

                Text(
                    modifier = Modifier.padding(
                        horizontal = PaddingNormal, vertical = PaddingSmall
                    ),
                    text = previousViewTitle,
                    color = colorScheme.primary,
                    style = typography.titleMedium
                )
            }

            if (menuItems != null) {
                menuItems()
            }
        }

        Text(
            modifier = Modifier.padding(
                horizontal = PaddingNormal
            ), text = currentViewTitle, style = typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                color = colorScheme.onBackground
            )
        )
    }
}

@SingleDevicePreview
@Composable
fun NavigationBar_Preview() {
    PreviewSurface {
        NavigationHeader(previousViewTitle = "Truck Home",
            currentViewTitle = "Truck",
            onBackPressed = {
                // do nothing
            },
            menuItems = { Text(text = "Menu Item") })
    }
}