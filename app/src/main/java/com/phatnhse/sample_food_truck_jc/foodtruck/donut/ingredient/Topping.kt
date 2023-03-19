package com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient

import com.phatnhse.sample_food_truck_jc.foodtruck.donut.FlavorProfile

data class Topping(
    override val name: String,
    override val imageAssetName: String,
    override val flavors: FlavorProfile,
    override val imagePrefix: String = "topping"
) : Ingredient(name, flavors, imageAssetName, imagePrefix) {
    companion object {
        val powderedSugar = Topping(
            name = "Powdered Sugar",
            imageAssetName = "powdersugar",
            flavors = FlavorProfile(salty = 1, sweet = 4)
        )

        val sprinkles = Topping(
            name = "Sprinkles",
            imageAssetName = "sprinkles",
            flavors = FlavorProfile(sweet = 3, savory = 2)
        )

        val starSprinkles = Topping(
            name = "Star Sprinkles",
            imageAssetName = "sprinkles_stars",
            flavors = FlavorProfile(sweet = 3, savory = 2)
        )

        val blueberryLattice = Topping(
            name = "Blueberry Lattice",
            imageAssetName = "crisscross_blue",
            flavors = FlavorProfile(salty = 1, sweet = 2, bitter = 1, sour = -1, savory = 2)
        )

        val chocolateLattice = Topping(
            name = "Chocolate Lattice",
            imageAssetName = "crisscross_brown",
            flavors = FlavorProfile(salty = 2, sweet = 1, bitter = 2)
        )

        val sourAppleLattice = Topping(
            name = "Sour Apple Lattice",
            imageAssetName = "crisscross_green",
            flavors = FlavorProfile(sweet = 1, sour = 3, savory = -1, spicy = 2)
        )

        val spicySauceLattice = Topping(
            name = "Spicy Sauce Lattice",
            imageAssetName = "crisscross_orange",
            flavors = FlavorProfile(salty = 1, savory = 1, spicy = 3)
        )

        val strawberryLattice = Topping(
            name = "Strawberry Lattice",
            imageAssetName = "crisscross_pink",
            flavors = FlavorProfile(salty = 1, sweet = 2, savory = 2)
        )

        val sugarLattice = Topping(
            name = "Sugar Lattice",
            imageAssetName = "crisscross_white",
            flavors = FlavorProfile(salty = 2, sweet = 3)
        )

        val lemonLattice = Topping(
            name = "Lemon Lattice",
            imageAssetName = "crisscross_yellow",
            flavors = FlavorProfile(bitter = 2, sour = 2, spicy = 1)
        )

        val blueberryLines = Topping(
            name = "Blueberry Lines",
            imageAssetName = "crisscross_blue",
            flavors = FlavorProfile(salty = 1, sweet = 2, bitter = 1, sour = -1, savory = 2)
        )

        val chocolateLines = Topping(
            name = "Chocolate Lines",
            imageAssetName = "straight_brown",
            flavors = FlavorProfile(salty = 2, sweet = 1, bitter = 2)
        )

        val sourAppleLines = Topping(
            name = "Sour Apple Lines",
            imageAssetName = "straight_green",
            flavors = FlavorProfile(sweet = 1, sour = 3, savory = -1, spicy = 2)
        )

        val spicySauceLines = Topping(
            name = "Spicy Sauce Lines",
            imageAssetName = "straight_orange",
            flavors = FlavorProfile(salty = 1, savory = 1, spicy = 3)
        )

        val strawberryLines = Topping(
            name = "Strawberry Lines",
            imageAssetName = "straight_pink",
            flavors = FlavorProfile(salty = 1, sweet = 2, savory = 2)
        )

        val sugarLines = Topping(
            name = "Sugar Lines",
            imageAssetName = "straight_white",
            flavors = FlavorProfile(salty = 2, sweet = 3)
        )

        val lemonLines = Topping(
            name = "Lemon Lines",
            imageAssetName = "straight_yellow",
            flavors = FlavorProfile(bitter = 2, sour = 2, spicy = 1)
        )

        val blueberryDrizzle = Topping(
            name = "Blueberry Drizzle",
            imageAssetName = "zigzag_blue",
            flavors = FlavorProfile(salty = 1, sweet = 2, bitter = 1, sour = -1, savory = 2)
        )

        val chocolateDrizzle = Topping(
            name = "Chocolate Drizzle",
            imageAssetName = "zigzag_brown",
            flavors = FlavorProfile(salty = 2, sweet = 1, bitter = 2)
        )

        val sourAppleDrizzle = Topping(
            name = "Sour Apple Drizzle",
            imageAssetName = "zigzag_green",
            flavors = FlavorProfile(sweet = 1, sour = 3, savory = -1, spicy = 2)
        )

        val spicySauceDrizzle = Topping(
            name = "Spicy Sauce Drizzle",
            imageAssetName = "zigzag_orange",
            flavors = FlavorProfile(salty = 1, savory = 1, spicy = 3)
        )

        val strawberryDrizzle = Topping(
            name = "Strawberry Drizzle",
            imageAssetName = "zigzag_pink",
            flavors = FlavorProfile(salty = 1, sweet = 2, savory = 2)
        )

        val sugarDrizzle = Topping(
            name = "Sugar Drizzle",
            imageAssetName = "zigzag_white",
            flavors = FlavorProfile(salty = 2, sweet = 3)
        )

        val lemonDrizzle = Topping(
            name = "Lemon Drizzle",
            imageAssetName = "zigzag_yellow",
            flavors = FlavorProfile(bitter = 2, sour = 2, spicy = 1)
        )

        val other = listOf(
            powderedSugar, sprinkles, starSprinkles
        )

        val lattices = listOf(
            blueberryLattice,
            chocolateLattice,
            sourAppleLattice,
            spicySauceLattice,
            strawberryLattice,
            sugarLattice,
            lemonLattice
        )

        val lines = listOf(
            blueberryLines,
            chocolateLines,
            sourAppleLines,
            spicySauceLines,
            strawberryLines,
            sugarLines,
            lemonLines
        )

        val drizzles = listOf(
            blueberryDrizzle,
            chocolateDrizzle,
            sourAppleDrizzle,
            spicySauceDrizzle,
            strawberryDrizzle,
            sugarDrizzle,
            lemonDrizzle
        )

        val all = listOf(
            other + lattices + lines + drizzles
        )
    }
}