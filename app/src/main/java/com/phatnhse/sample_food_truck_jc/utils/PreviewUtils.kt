package com.phatnhse.sample_food_truck_jc.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, device = "id:pixel_6_pro")
@Preview(showBackground = true, device = "id:pixel_5")
@Preview(showBackground = true, device = "id:pixel_2")
annotation class MultipleDevices


@Preview(showBackground = true, device = "id:pixel_6_pro")
annotation class SingleDevice