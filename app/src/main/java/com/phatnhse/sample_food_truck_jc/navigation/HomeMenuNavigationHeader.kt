package com.phatnhse.sample_food_truck_jc.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.R
import com.phatnhse.sample_food_truck_jc.ui.theme.IconSizeLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme
import com.phatnhse.sample_food_truck_jc.utils.SingleDevice
@Composable
fun HomeMenuNavigationHeader(
    modifier: Modifier = Modifier,
    menuItem: MenuItem,
    symbolColor: Color = colorScheme.primary,
    showDivider: Boolean = true,
    onClicked: (MenuItem) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClicked(menuItem) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Row {
            Spacer(modifier = Modifier.size(PaddingLarge))
            Image(
                modifier = Modifier.size(IconSizeLarge),
                painter = menuItem.getSymbol(),
                contentDescription = menuItem.getTitle(),
                colorFilter = ColorFilter.tint(
                    color = symbolColor
                )
            )
        }

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = PaddingLarge),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(PaddingLarge),
                    text = menuItem.getTitle(),
                    color = colorScheme.onBackground,
                    style = typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal
                    )
                )

                Image(
                    modifier = Modifier
                        .height(12.dp)
                        .width(6.dp),
                    painter = painterResource(id = R.drawable.chevron_right),
                    contentDescription = "Chevron Right",
                    colorFilter = ColorFilter.tint(
                        color = colorScheme.onBackground.copy(alpha = 0.5F)
                    )
                )
            }

            if (showDivider) {
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = (0.5F).dp,
                    color = colorScheme.surfaceVariant
                )
            }
        }
    }
}

@SingleDevice
@Composable
fun HomeMenuNavigationHeader_Preview() {
    SampleFoodTruckJCTheme {
        HomeMenuNavigationHeader(
            menuItem = MenuItem.Truck,
            onClicked = {}
        )
    }
}