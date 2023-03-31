package com.phatnhse.sample_food_truck_jc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.phatnhse.sample_food_truck_jc.foodtruck.model.FoodTruckViewModel
import com.phatnhse.sample_food_truck_jc.navigation.AppNavigation
import com.phatnhse.sample_food_truck_jc.navigation.MenuItem
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface

class MainActivity : ComponentActivity() {
    private val viewModel = FoodTruckViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App(viewModel) }
    }
}

@Composable
fun App(viewModel: FoodTruckViewModel) {
    val navController = rememberNavController()
    val defaultMenu = remember {
        mutableStateOf<MenuItem>(MenuItem.Truck)
    }
    PreviewSurface {
        AppNavigation(
            navController = navController,
            appLaunchEntry = defaultMenu,
            viewModel = viewModel
        )
    }
}