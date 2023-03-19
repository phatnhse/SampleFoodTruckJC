package com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient

import com.phatnhse.sample_food_truck_jc.foodtruck.donut.FlavorProfile

data class Glaze(
    override val name: String,
    override val imageAssetName: String,
    override val flavors: FlavorProfile,
    override val imagePrefix: String = "glaze"
) : Ingredient(name, flavors, imageAssetName, imagePrefix) {
    companion object {

        val blueberry = Glaze(
            name = "Blueberry Spread",
            imageAssetName = "blue",
            flavors = FlavorProfile(salty = 1, sweet = 3, sour = -1, savory = 2)
        )

        val chocolate = Glaze(
            name = "Chocolate Glaze",
            imageAssetName = "brown",
            flavors = FlavorProfile(salty = 1, sweet = 1, bitter = 1, savory = 2)
        )

        val sourCandy = Glaze(
            name = "Sour Candy Glaze",
            imageAssetName = "green",
            flavors = FlavorProfile(bitter = 1, sour = 3, savory = -1, spicy = 2)
        )

        val spicy = Glaze(
            name = "Spicy Spread",
            imageAssetName = "orange",
            flavors = FlavorProfile(salty = 1, sour = 1, spicy = 3)
        )

        val strawberry = Glaze(
            name = "Strawberry Glaze",
            imageAssetName = "pink",
            flavors = FlavorProfile(salty = 1, sweet = 2, savory = 2)
        )

        val lemon = Glaze(
            name = "Lemon Spread",
            imageAssetName = "yellow",
            flavors = FlavorProfile(sweet = 1, sour = 3, spicy = 1)
        )

        val rainbow = Glaze(
            name = "Rainbow Glaze",
            imageAssetName = "rainbow",
            flavors = FlavorProfile(salty = 2, sweet = 2, spicy = 1)
        )

        val all = listOf(
            blueberry,
            chocolate,
            sourCandy,
            spicy,
            strawberry,
            lemon,
            rainbow
        )

    }
}
