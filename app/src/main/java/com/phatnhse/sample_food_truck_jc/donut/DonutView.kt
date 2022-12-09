package com.phatnhse.sample_food_truck_jc.donut

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.phatnhse.sample_food_truck_jc.R

@Composable
fun DonutView() {
    Image(painterResource(R.mipmap.foodtruck_frame_1), "content description")
}

@Preview(showBackground = true)
@Composable
fun DonutView_Preview() {
    DonutView()
}