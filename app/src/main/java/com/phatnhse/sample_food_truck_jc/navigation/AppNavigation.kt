package com.phatnhse.sample_food_truck_jc.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.phatnhse.sample_food_truck_jc.donut.DonutEditor
import com.phatnhse.sample_food_truck_jc.donut.DonutGallery
import com.phatnhse.sample_food_truck_jc.donut.TopFiveDonutsView
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
import com.phatnhse.sample_food_truck_jc.truck.SalesHistoryView
import com.phatnhse.sample_food_truck_jc.truck.SocialFeedView
import com.phatnhse.sample_food_truck_jc.truck.TruckView

const val LauncherViewId = "Food Truck"

@Composable
fun AppNavigation(
    navController: NavHostController,
    appLaunchEntry: MutableState<MenuItem>,
    foodTruckViewModel: FoodTruckViewModel
) {
    fun openHome() {
        navController.navigate(LauncherViewId) {
            launchSingleTop = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = appLaunchEntry.value.title
    ) {
        composable(LauncherViewId) {
            HomeView(onMenuItemClicked = {
                navController.navigate(it.title)
            })
        }
        composable(MenuItem.Truck.title) {
            val previous = navController.previousBackStackEntry?.destination?.route
            TruckView(
                currentViewTitle = appLaunchEntry.value.title,
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
            val openFromHome = appLaunchEntry.value == MenuItem.Orders
            val previous = navController.previousBackStackEntry?.destination?.route
            OrderView(
                currentViewTitle = appLaunchEntry.value.title,
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
                })
        }
        composable(MenuItem.SocialFeed.title) {
            val previous = navController.previousBackStackEntry?.destination?.route
            SocialFeedView(
                previousViewTitle = previous ?: LauncherViewId,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
        composable(MenuItem.SalesHistory.title) {
            val previous = navController.previousBackStackEntry?.destination?.route
            SalesHistoryView(
                previousViewTitle = previous ?: LauncherViewId,
                model = foodTruckViewModel,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
        composable(MenuItem.Donuts.title) {
            val openFromHome = appLaunchEntry.value == MenuItem.Donuts
            val previous = navController.previousBackStackEntry?.destination?.route
            DonutGallery(
                currentViewTitle = appLaunchEntry.value.title,
                previousViewTitle = previous ?: LauncherViewId,
                onBackPressed = {
                    if (openFromHome) {
                        openHome()
                    } else {
                        navController.popBackStack()
                    }
                },
                onDonutClicked = {
                    val donutId = it.id
                    navController.navigate("donuts/${donutId}")
                },
                onNewDonutClicked = {
                    navController.navigate(MenuItem.DonutEditor.title)
                },
                model = foodTruckViewModel
            )
        }
        composable(MenuItem.DonutEditor.title) {
            val openFromHome = appLaunchEntry.value == MenuItem.Donuts
            DonutEditor(
                onBackPressed = {
                    if (openFromHome) {
                        openHome()
                    } else {
                        navController.popBackStack()
                    }
                },
                donutId = foodTruckViewModel.newDonut.id,
                createNewDonut = true,
                model = foodTruckViewModel
            )
        }
        composable(MenuItem.TopFive.title) {
            val previous = navController.previousBackStackEntry?.destination?.route
            TopFiveDonutsView(
                previousViewTitle = previous ?: LauncherViewId,
                model = foodTruckViewModel,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
        composable("orders/{orderId}") {
            val orderId = it.arguments?.getString("orderId")
            val previous = navController.previousBackStackEntry?.destination?.route
            OrderDetailView(
                currentViewTitle = appLaunchEntry.value.title,
                previousViewTitle = previous ?: LauncherViewId,
                onBackPressed = {
                    navController.popBackStack()
                },
                orderId = orderId ?: "",
                viewModel = foodTruckViewModel
            )
        }
        composable("donuts/{donutId}") {
            val donutId = it.arguments?.getString("donutId")
            DonutEditor(
                onBackPressed = {
                    navController.popBackStack()
                },
                createNewDonut = false,
                donutId = donutId?.toIntOrNull() ?: -1,
                model = foodTruckViewModel
            )
        }
    }
}

sealed class MenuItem(val title: String) {
    object Truck : MenuItem("Truck")
    object SocialFeed : MenuItem("Social Feed")
    object Orders : MenuItem("Orders")
    object SalesHistory : MenuItem("Sales History")
    object Donuts : MenuItem("Donuts")
    object DonutEditor : MenuItem("Donut Editor")
    object TopFive : MenuItem("Top 5")
    data class City(
        val id: String
    ) : MenuItem("City")

    companion object {
        fun valueOf(title: String): MenuItem {
            return MenuItem::class.sealedSubclasses.mapNotNull { it.objectInstance }.first {
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