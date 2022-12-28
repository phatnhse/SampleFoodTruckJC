package com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.ingredient

import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.FlavorProfile

sealed class Ingredient(
    open val name: String,
    open val flavors: FlavorProfile,
    open val imageAssetName: String,
    open val imagePrefix: String
) {
    fun id(): String {
        return "$imagePrefix-$imageAssetName"
    }

    fun imageResourceName(showThumb: Boolean): String {
        return "${imagePrefix}_${imageAssetName}_${if (showThumb) "thumb" else "full"}"
    }
}