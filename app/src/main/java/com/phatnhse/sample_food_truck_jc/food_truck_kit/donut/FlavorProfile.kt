package com.phatnhse.sample_food_truck_jc.food_truck_kit.donut

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.phatnhse.sample_food_truck_jc.R
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.flavorSymbol

data class FlavorProfile(
    val salty: Int = 0,
    val sweet: Int = 0,
    val bitter: Int = 0,
    val sour: Int = 0,
    val savory: Int = 0,
    val spicy: Int = 0
) {
    fun getFlavor(flavor: Flavor): Int {
        return when (flavor) {
            is Flavor.Bitter -> bitter
            is Flavor.Salty -> salty
            is Flavor.Savory -> savory
            is Flavor.Sour -> sour
            is Flavor.Spicy -> spicy
            is Flavor.Sweet -> sweet
        }
    }

    fun union(other: FlavorProfile): FlavorProfile {
        return FlavorProfile(
            salty + other.salty,
            sweet + other.sweet,
            bitter + other.bitter,
            sour + other.sour,
            savory + other.savory,
            spicy + other.spicy
        )
    }

    operator fun plus(other: FlavorProfile): FlavorProfile {
        return union(other)
    }
}

sealed class Flavor(
    open val name: String, @DrawableRes open val imageRes: Int
) {
    data class Salty(
        override val name: String = "Salty", override val imageRes: Int = R.drawable.salty
    ) : Flavor(name, imageRes)

    data class Sweet(
        override val name: String = "Sweet", override val imageRes: Int = R.drawable.sweet
    ) : Flavor(name, imageRes)


    data class Bitter(
        override val name: String = "Bitter", override val imageRes: Int = R.drawable.bitter
    ) : Flavor(name, imageRes)


    data class Sour(
        override val name: String = "Sour", override val imageRes: Int = R.drawable.sour
    ) : Flavor(name, imageRes)

    data class Savory(
        override val name: String = "Savory", override val imageRes: Int = R.drawable.savory
    ) : Flavor(name, imageRes)

    data class Spicy(
        override val name: String = "Spicy", override val imageRes: Int = R.drawable.spicy
    ) : Flavor(name, imageRes)
}