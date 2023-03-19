package com.phatnhse.sample_food_truck_jc.foodtruck.city

data class City(
    val name: String,
    val parkingSpots: List<ParkingSpot>
) {
    val id: String = name

    companion object {
        val sanFrancisco = City(
            name = "San Francisco",
            parkingSpots = listOf(
                ParkingSpot(
                    name = "Coit Tower",
                    location = Location(latitude = 37.8024, longitude = -122.4058),
                    cameraDistance = 300.0
                ),
                ParkingSpot(
                    name = "Fisherman's Wharf",
                    location = Location(latitude = 37.8099, longitude = -122.4103),
                    cameraDistance = 700.0
                ),
                ParkingSpot(
                    name = "Ferry Building",
                    location = Location(latitude = 37.7956, longitude = -122.3935),
                    cameraDistance = 450.0
                ),
                ParkingSpot(
                    name = "Golden Gate Bridge",
                    location = Location(latitude = 37.8199, longitude = -122.4783),
                    cameraDistance = 2000.0
                ),
                ParkingSpot(
                    name = "Oracle Park",
                    location = Location(latitude = 37.7786, longitude = -122.3893),
                    cameraDistance = 650.0
                ),
                ParkingSpot(
                    name = "The Castro Theatre",
                    location = Location(latitude = 37.7609, longitude = -122.4350),
                    cameraDistance = 400.0
                ),
                ParkingSpot(
                    name = "Sutro Tower",
                    location = Location(latitude = 37.7552, longitude = -122.4528)
                ),
                ParkingSpot(
                    name = "Bay Bridge",
                    location = Location(latitude = 37.7983, longitude = -122.3778)
                )
            )
        )

        val cupertino = City(
            name = "Cupertino",
            parkingSpots = listOf(
                ParkingSpot(
                    name = "Apple Park",
                    location = Location(latitude = 37.3348, longitude = -122.0090),
                    cameraDistance = 1100.0
                ),
                ParkingSpot(
                    name = "Infinite Loop",
                    location = Location(latitude = 37.3317, longitude = -122.0302)
                )
            )
        )

        val london = City(
            name = "London",
            parkingSpots = listOf(
                ParkingSpot(
                    name = "Big Ben",
                    location = Location(latitude = 51.4994, longitude = -0.1245),
                    cameraDistance = 850.0
                ),
                ParkingSpot(
                    name = "Buckingham Palace",
                    location = Location(latitude = 51.5014, longitude = -0.1419),
                    cameraDistance = 750.0
                ),
                ParkingSpot(
                    name = "Marble Arch",
                    location = Location(latitude = 51.5131, longitude = -0.1589)
                ),
                ParkingSpot(
                    name = "Piccadilly Circus",
                    location = Location(latitude = 51.510_067, longitude = -0.133_869)
                ),
                ParkingSpot(
                    name = "Shakespeare's Globe",
                    location = Location(latitude = 51.5081, longitude = -0.0972)
                ),
                ParkingSpot(
                    name = "Tower Bridge",
                    location = Location(latitude = 51.5055, longitude = -0.0754)
                )
            )
        )

        val all = listOf(cupertino, sanFrancisco, london)

        fun getCityFromId(id: String): City? {
            return all.firstOrNull {
                it.id == id
            }
        }
    }
}

