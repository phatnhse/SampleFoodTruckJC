package com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.ingredient

import androidx.compose.ui.text.toUpperCase
import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.FlavorProfile

data class Dough(
    override val name: String,
    override val imageAssetName: String,
    override val flavors: FlavorProfile,
    override val imagePrefix: String = "dough"
) : Ingredient(name, flavors, imageAssetName, imagePrefix) {
    fun backgroundColorName(darkTheme: Boolean): String {
        return "${imagePrefix.uppercase()}${imageAssetName.uppercase()}Bg${if (darkTheme) "Dark" else "Light"}"
    }

    companion object {
        val blueberry = Dough(
            name = "Blueberry Dough",
            imageAssetName = "blue",
            flavors = FlavorProfile(salty = 1, sweet = 2, savory = 2)
        )

        val chocolate = Dough(
            name = "Chocolate Dough",
            imageAssetName = "brown",
            flavors = FlavorProfile(salty = 1, sweet = 3, bitter = 1, sour = -1, savory = 1)
        )

        val sourCandy = Dough(
            name = "Sour Candy",
            imageAssetName = "green",
            flavors = FlavorProfile(salty = 1, sweet = 2, sour = 3, savory = -1)
        )

        val strawberry = Dough(
            name = "Strawberry Dough",
            imageAssetName = "pink",
            flavors = FlavorProfile(sweet = 3, savory = 2)
        )

        val plain = Dough(
            name = "Plain",
            imageAssetName = "plain",
            flavors = FlavorProfile(salty = 1, sweet = 1, bitter = 1, savory = 2)
        )

        val powdered = Dough(
            name = "Powdered Dough",
            imageAssetName = "white",
            flavors = FlavorProfile(salty = -1, sweet = 4, savory = 1)
        )

        val lemonade = Dough(
            name = "Lemonade",
            imageAssetName = "yellow",
            flavors = FlavorProfile(salty = 1, sweet = 1, sour = 3)
        )

        val all = listOf(
            blueberry,
            chocolate,
            sourCandy,
            strawberry,
            plain,
            powdered,
            lemonade
        )
    }
}