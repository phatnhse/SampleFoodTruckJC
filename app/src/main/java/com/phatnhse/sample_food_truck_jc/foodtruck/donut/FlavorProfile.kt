package com.phatnhse.sample_food_truck_jc.foodtruck.donut

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.phatnhse.sample_food_truck_jc.R

data class FlavorProfile(
    var salty: Int = 0,
    var sweet: Int = 0,
    var bitter: Int = 0,
    var sour: Int = 0,
    var savory: Int = 0,
    var spicy: Int = 0
) {
    operator fun get(flavor: Flavor): Int {
        return when (flavor) {
            Flavor.Salty -> salty
            Flavor.Sweet -> sweet
            Flavor.Bitter -> bitter
            Flavor.Sour -> sour
            Flavor.Savory -> savory
            Flavor.Spicy -> spicy
        }
    }

    operator fun set(flavor: Flavor, newValue: Int) {
        when (flavor) {
            Flavor.Salty -> salty = newValue
            Flavor.Sweet -> sweet = newValue
            Flavor.Bitter -> bitter = newValue
            Flavor.Sour -> sour = newValue
            Flavor.Savory -> savory = newValue
            Flavor.Spicy -> spicy = newValue
        }
    }

    operator fun plus(other: FlavorProfile): FlavorProfile {
        return union(other)
    }

    fun union(other: FlavorProfile): FlavorProfile {
        val result = this.copy()
        for (flavor in Flavor.values()) {
            result[flavor] += other[flavor]
        }
        return result
    }

    fun formUnion(other: FlavorProfile) {
        this.copyFrom(union(other))
    }

    private fun copyFrom(other: FlavorProfile) {
        salty = other.salty
        sweet = other.sweet
        bitter = other.bitter
        sour = other.sour
        savory = other.savory
        spicy = other.spicy
    }

    val mostPotent: Pair<Flavor, Int>
        get() {
            var highestValue = 0
            var mostPotent = Flavor.Sweet
            for (flavor in Flavor.values()) {
                val value = this[flavor]
                if (value > highestValue) {
                    highestValue = value
                    mostPotent = flavor
                }
            }
            return Pair(mostPotent, highestValue)
        }

    val mostPotentFlavor: Flavor
        get() = mostPotent.first

    val mostPotentValue: Int
        get() = mostPotent.second
}

enum class Flavor(val displayName: String) {
    Salty("Salty"),
    Sweet("Sweet"),
    Bitter("Bitter"),
    Sour("Sour"),
    Savory("Savory"),
    Spicy("Spicy");
}

@Composable
fun flavorPainter(flavor: Flavor): Painter {
    return when (flavor) {
        Flavor.Bitter -> painterResource(id = R.drawable.bitter)
        Flavor.Salty -> painterResource(id = R.drawable.salty)
        Flavor.Savory -> painterResource(id = R.drawable.savory)
        Flavor.Sour -> painterResource(id = R.drawable.sour)
        Flavor.Spicy -> painterResource(id = R.drawable.spicy)
        Flavor.Sweet -> painterResource(id = R.drawable.sweet)
    }
}