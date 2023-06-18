package com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient

import androidx.compose.ui.graphics.Color
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.FlavorProfile

data class Dough(
    override val name: String,
    override val imageAssetName: String,
    override val flavors: FlavorProfile,
    override val imagePrefix: String = "dough"
) : Ingredient(name, flavors, imageAssetName, imagePrefix) {
    fun backgroundColorName(darkTheme: Boolean): Color {
        val colorName =
            "${imagePrefix}${imageAssetName.capitalize()}Bg${if (darkTheme) "Dark" else "Light"}"
        return doughBgColors.getOrDefault(colorName, Color(0xFF5B4E41))
    }

    companion object {
        val doughBgColors = mapOf(
            "doughBlueBgDark" to Color(0xFF4E6D80),
            "doughBlueBgLight" to Color(0xFF9CAEBF),

            "doughBrownBgDark" to Color(0xFF5B4E41),
            "doughBrownBgLight" to Color(0xFF998069),

            "doughGreenBgDark" to Color(0xFF46694D),
            "doughGreenBgLight" to Color(0xFF8CC096),

            "doughPinkBgDark" to Color(0xFF7B4F4D),
            "doughPinkBgLight" to Color(0xFFBE9A99),

            "doughPlainBgDark" to Color(0xFF6F604E),
            "doughPlainBgLight" to Color(0xFFC2A27B),

            "doughWhiteBgDark" to Color(0xFF586777),
            "doughWhiteBgLight" to Color(0xFFAEB5BD),

            "doughYellowBgDark" to Color(0xFF8D7D55),
            "doughYellowBgLight" to Color(0xFFD9C794)
        )

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