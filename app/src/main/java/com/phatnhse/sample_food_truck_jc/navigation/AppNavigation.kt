package com.phatnhse.sample_food_truck_jc.navigation

import android.net.Uri
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.phatnhse.sample_food_truck_jc.foodtruck.city.City.Companion.getCityFromId
import com.phatnhse.sample_food_truck_jc.foodtruck.general.buildingPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.clockPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.donutPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.shippingPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.sliderPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.socialFeedPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.trophyPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.general.truckPainter
import com.phatnhse.sample_food_truck_jc.foodtruck.model.FoodTruckViewModel
import com.phatnhse.sample_food_truck_jc.home.HomeView
import com.phatnhse.sample_food_truck_jc.order.OrderDetailView
import com.phatnhse.sample_food_truck_jc.order.OrderView
import com.phatnhse.sample_food_truck_jc.truck.TruckView
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface

const val LauncherViewId = "Food Truck"

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    selection: MutableState<MenuItem> = remember { mutableStateOf(MenuItem.Truck) }
) {
    val foodTruckViewModel = FoodTruckViewModel()

    fun openHome() {
        navController.navigate(LauncherViewId) {
            launchSingleTop = true
        }
    }

    NavHost(
        navController = navController, startDestination = selection.value.title
    ) {
        composable(LauncherViewId) {
            HomeView(onMenuItemClicked = {
                selection.value = it
                navController.navigate(it.title)
            })
        }
        composable(MenuItem.Truck.title) {
            val previous = navController.previousBackStackEntry?.destination?.route
            TruckView(
                currentViewTitle = selection.value.title,
                previousViewTitle = previous ?: LauncherViewId, // should be open from home page
                onNavigateToDonuts = {
                    navController.navigate(MenuItem.Donuts.title)
                },
                onNavigateToSocialFeed = {
                    navController.navigate(MenuItem.SocialFeed.title)
                },
                onNavigateToOrders = {
                    navController.navigate(MenuItem.Orders.title)
                },
                onBackPressed = {
                    openHome()
                },
                viewModel = foodTruckViewModel
            )
        }
        composable(MenuItem.Orders.title) {
            val openFromHome = selection.value == MenuItem.Orders
            val previous = navController.previousBackStackEntry?.destination?.route
            OrderView(
                currentViewTitle = selection.value.title,
                previousViewTitle = previous ?: LauncherViewId,
                onBackPressed = {
                    if (openFromHome) {
                        openHome()
                    } else {
                        navController.popBackStack()
                    }
                },
                model = foodTruckViewModel,
                orderClicked = {
                    val orderId = it.id
                    val encodedOrderId = Uri.encode(orderId)
                    navController.navigate("orders/${encodedOrderId}")
                }
            )
        }
        composable(MenuItem.SocialFeed.title) { Text(text = "SocialFeed") }
        composable(MenuItem.SalesHistory.title) { Text(text = "SalesHistory") }
        composable(MenuItem.Donuts.title) { Text(text = "Donuts") }
        composable(MenuItem.DonutEditor.title) { Text(text = "DonutEditor") }
        composable(MenuItem.TopFive.title) { Text(text = "TopFive") }
        composable("orders/{orderId}") {
            val orderId = it.arguments?.getString("orderId")
            val previous = navController.previousBackStackEntry?.destination?.route
            OrderDetailView(
                currentViewTitle = selection.value.title,
                previousViewTitle = previous ?: LauncherViewId,
                onBackPressed = {
                    navController.popBackStack()
                },
                orderId = orderId ?: "",
                viewModel = foodTruckViewModel
            )
        }
    }
}

sealed class MenuItem(val title: String) {
    object Truck : MenuItem("Truck")
    object SocialFeed : MenuItem("SocialFeed")
    object Orders : MenuItem("Orders")
    object SalesHistory : MenuItem("SalesHistory")
    object Donuts : MenuItem("Donuts")
    object DonutEditor : MenuItem("DonutEditor")
    object TopFive : MenuItem("TopFive")
    data class City(
        val id: String
    ) : MenuItem("City")

    companion object {
        fun valueOf(title: String): MenuItem {
            return MenuItem::class.sealedSubclasses
                .mapNotNull { it.objectInstance }
                .first {
                    it.title == title
                }
        }
    }
}

@Composable
fun MenuItem.getSymbol(): Painter {
    return when (this) {
        is MenuItem.City -> buildingPainter()
        MenuItem.DonutEditor -> sliderPainter()
        MenuItem.Donuts -> donutPainter()
        MenuItem.Orders -> shippingPainter()
        MenuItem.SalesHistory -> clockPainter()
        MenuItem.SocialFeed -> socialFeedPainter()
        MenuItem.TopFive -> trophyPainter()
        MenuItem.Truck -> truckPainter()
    }
}

fun MenuItem.getTitle(): String {
    return when (this) {
        is MenuItem.City -> getCityFromId(id)?.name ?: ""
        else -> this.title
    }
}

@Preview
@Composable
fun NavigationDrawer_Preview() {
    PreviewSurface {
        AppNavigation(rememberNavController())
    }
}