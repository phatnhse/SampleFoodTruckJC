package com.phatnhse.sample_food_truck_jc.foodtruck.donut.ingredient

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.phatnhse.sample_food_truck_jc.foodtruck.donut.FlavorProfile

sealed class Ingredient(
    open val name: String,
    open val flavors: FlavorProfile,
    open val imageAssetName: String,
    open val imagePrefix: String
) {
    fun id(): String {
        return "$imagePrefix-$imageAssetName"
    }

    @SuppressLint("DiscouragedApi")
    @Composable
    fun IngredientImage(
        modifier: Modifier = Modifier
    ) {
        val context = LocalContext.current
        val fileName = imageResourceName()
        val iconResId: Int = context.resources
            .getIdentifier(fileName, "drawable", context.packageName)

        Image(
            modifier = modifier,
            painter = painterResource(id = iconResId),
            contentDescription = name,
            contentScale = ContentScale.Fit
        )
    }

    private fun imageResourceName(): String {
        return "${imagePrefix}_${imageAssetName}_full"
    }
}