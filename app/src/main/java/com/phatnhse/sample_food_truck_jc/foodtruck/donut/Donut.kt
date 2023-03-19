package com.phatnhse.sample_food_truck_jc.foodtruck.donut

import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Dough
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Dough.Companion.blueberry
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Dough.Companion.lemonade
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Dough.Companion.plain
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Dough.Companion.strawberry
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Glaze
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Glaze.Companion.chocolate
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Glaze.Companion.lemon
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Glaze.Companion.spicy
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Ingredient
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Topping
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Topping.Companion.blueberryDrizzle
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Topping.Companion.blueberryLattice
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Topping.Companion.chocolateDrizzle
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Topping.Companion.lemonLines
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Topping.Companion.powderedSugar
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Topping.Companion.spicySauceDrizzle
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Topping.Companion.sprinkles
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Topping.Companion.starSprinkles
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Topping.Companion.sugarDrizzle
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient.Topping.Companion.sugarLattice

data class Donut(
    val id: Int,
    val name: String,
    val dough: Dough,
    val glaze: Glaze?,
    val topping: Topping?
) {
    val ingredients: List<Ingredient>
        get() {
            val result = mutableListOf<Ingredient>(dough)
            glaze?.let { result.add(it) }
            topping?.let { result.add(it) }
            return result
        }

    val flavors: FlavorProfile
        get() {
            return ingredients.map { it.flavors }.reduce { acc, flavorProfile ->
                acc + flavorProfile
            }
        }

    fun matches(searchText: String): Boolean {
        val foundInName = name.contains(searchText, ignoreCase = true)
        val foundInIngredients = ingredients.any {
            it.name.contains(searchText, ignoreCase = true)
        }

        return foundInName || foundInIngredients
    }

    companion object {
        val classic = Donut(
            id = 0,
            name = "The Classic",
            dough = plain,
            glaze = chocolate,
            topping = sprinkles
        )

        val blueberryFrosted = Donut(
            id = 1,
            name = "Blueberry Frosted",
            dough = blueberry,
            glaze = Glaze.blueberry,
            topping = sugarLattice
        )

        val strawberryDrizzle = Donut(
            id = 2,
            name = "Strawberry Drizzle",
            dough = strawberry,
            glaze = Glaze.strawberry,
            topping = sugarDrizzle
        )

        val cosmos = Donut(
            id = 3,
            name = "Cosmos",
            dough = Dough.chocolate,
            glaze = chocolate,
            topping = starSprinkles
        )

        val strawberrySprinkles = Donut(
            id = 4,
            name = "Strawberry Sprinkles",
            dough = plain,
            glaze = Glaze.strawberry,
            topping = starSprinkles
        )

        val lemonChocolate = Donut(
            id = 5,
            name = "Lemon Chocolate",
            dough = plain,
            glaze = chocolate,
            topping = lemonLines
        )

        val rainbow = Donut(
            id = 6,
            name = "Rainbow",
            dough = plain,
            glaze = Glaze.rainbow,
            topping = null
        )

        val picnicBasket = Donut(
            id = 7,
            name = "Picnic Basket",
            dough = Dough.chocolate,
            glaze = Glaze.strawberry,
            topping = blueberryLattice
        )

        val figureSkater = Donut(
            id = 8,
            name = "Figure Skater",
            dough = plain,
            glaze = Glaze.blueberry,
            topping = sugarDrizzle
        )

        val powderedChocolate = Donut(
            id = 9,
            name = "Powdered Chocolate",
            dough = Dough.chocolate,
            glaze = null,
            topping = powderedSugar,
        )

        val powderedStrawberry = Donut(
            id = 10,
            name = "Powdered Strawberry",
            dough = strawberry,
            glaze = null,
            topping = powderedSugar
        )

        val custard = Donut(
            id = 11,
            name = "Custard",
            dough = lemonade,
            glaze = spicy,
            topping = lemonLines
        )

        val superLemon = Donut(
            id = 12,
            name = "Super Lemon",
            dough = lemonade,
            glaze = lemon,
            topping = sprinkles
        )

        val fireZest = Donut(
            id = 13,
            name = "Fire Zest",
            dough = lemonade,
            glaze = spicy,
            topping = spicySauceDrizzle
        )

        val blackRaspberry = Donut(
            id = 14,
            name = "Black Raspberry",
            dough = plain,
            glaze = chocolate,
            topping = blueberryDrizzle
        )

        val daytime = Donut(
            id = 15,
            name = "Daytime",
            dough = lemonade,
            glaze = Glaze.rainbow,
            topping = null
        )

        val nighttime = Donut(
            id = 16,
            name = "Nighttime",
            dough = Dough.chocolate,
            glaze = chocolate,
            topping = chocolateDrizzle
        )

        val all = listOf(
            classic,
            blueberryFrosted,
            strawberryDrizzle,
            cosmos,
            strawberrySprinkles,
            lemonChocolate,
            rainbow,
            picnicBasket,
            figureSkater,
            powderedChocolate,
            powderedStrawberry,
            custard,
            superLemon,
            fireZest,
            blackRaspberry,
            daytime,
            nighttime
        )

        val preview = classic
    }
}