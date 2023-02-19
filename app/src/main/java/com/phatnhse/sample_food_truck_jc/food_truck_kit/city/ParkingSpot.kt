package com.phatnhse.sample_food_truck_jc.food_truck_kit.city

data class Location(
    val latitude: Double,
    val longitude: Double
)

data class ParkingSpot(
    val name: String,
    val location: Location,
    val cameraDistance: Double = 1000.0
) {
    val id = name
}