package com.phatnhse.sample_food_truck_jc.donut
//
data class FlavorProfile(
    val salty: Int = 0,
    val sweet: Int = 0,
    val bitter: Int = 0,
    val sour: Int = 0,
    val savory: Int = 0,
    val spicy: Int = 0
) {
//    fun subscript(flavor: Flavor): Int {
//
//    }
}

sealed class Flavor(open val id: String, open val name: String)

data class Salty(
    override val id: String = "salty", override val name: String = "Salty"
) : Flavor(id, name)

data class Sweet(
    override val id: String = "sweet", override val name: String = "Sweet"
) : Flavor(id, name)


data class Bitter(
    override val id: String = "bitter", override val name: String = "Bitter"
) : Flavor(id, name)


data class Sour(
    override val id: String = "sour", override val name: String = "SOUR"
) : Flavor(id, name)


data class Savory(
    override val id: String = "savory", override val name: String = "Savory"
) : Flavor(id, name)


data class Spicy(
    override val id: String = "spicy", override val name: String = "Spicy"
) : Flavor(id, name)