package com.phatnhse.sample_food_truck_jc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.phatnhse.sample_food_truck_jc.navigation.AppNavigation
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}

@Composable
fun App() {
    PreviewSurface {
        AppNavigation()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    App()
}