package com.phatnhse.sample_food_truck_jc.truck

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phatnhse.sample_food_truck_jc.food_truck_kit.city.City
import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.Donut
import com.phatnhse.sample_food_truck_jc.food_truck_kit.donut.DonutView
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.SingleDevice
import com.phatnhse.sample_food_truck_jc.food_truck_kit.general.buildingSymbol
import com.phatnhse.sample_food_truck_jc.truck.SocialFeedTag.Companion.tags
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme
import java.util.Calendar
import java.util.Date
import java.util.UUID

data class SocialFeedPost(
    val id: UUID = UUID.randomUUID(),
    val favoriteDonut: Donut,
    val message: String,
    val date: Date,
    val tags: List<SocialFeedTag>
) {

    companion object {
        private val date = Date()

        private fun olderDate(): Date {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.MINUTE, -60 * (5..30).random())
            return calendar.time
        }

        val socialFeedPlusContent = listOf(
            SocialFeedPost(
                favoriteDonut = Donut.picnicBasket,
                message = "I'm going to place a huge order next time the Food Truck is in San Francisco!!",
                date = olderDate(),
                tags = listOf(
                    SocialFeedTag.CityTag(City.sanFrancisco),
                    SocialFeedTag.DonutTag(Donut.classic),
                    SocialFeedTag.TitleTag("Like two dozen!")
                )
            ), SocialFeedPost(
                favoriteDonut = Donut.rainbow,
                message = "Just told my coworkers about the Food Truck and we are currently a group of 20 heading out.",
                date = olderDate(),
                tags = listOf(
                    SocialFeedTag.TitleTag("How many donuts are too many"),
                    SocialFeedTag.TitleTag("Trick question"),
                    SocialFeedTag.TitleTag("Please don't tell me")
                )
            ), SocialFeedPost(
                favoriteDonut = Donut.fireZest,
                message = "Once the Food Truck adds carrot-flavored donuts; I'm going to order a million of them!",
                date = olderDate(),
                tags = listOf(
                    SocialFeedTag.TitleTag("Carrot"),
                    SocialFeedTag.TitleTag("Carrots of Social Feed"),
                    SocialFeedTag.TitleTag("Bunnies of Social Feed"),
                    SocialFeedTag.TitleTag("Carrots"),
                    SocialFeedTag.TitleTag("Donuts for Bunnies")
                )
            )
        )
    }
}

sealed class SocialFeedTag(
    val id: String, open val title: String
) {
    data class TitleTag(override val title: String) : SocialFeedTag(
        id = "tag title $title", title = title
    )

    data class DonutTag(val donut: Donut) : SocialFeedTag(
        id = "tag donut ${donut.id}", title = donut.name
    )

    data class CityTag(val city: City) : SocialFeedTag(
        id = "tag donut ${city.id}", title = city.name
    )

    companion object {
        val tags = listOf(
            DonutTag(Donut.powderedChocolate),
            DonutTag(Donut.blueberryFrosted),
            TitleTag("Warmed Up"),
            TitleTag("Room Temperature"),
            CityTag(City.sanFrancisco),
            DonutTag(Donut.rainbow),
            TitleTag("Rainbow Sprinkles"),
            DonutTag(Donut.strawberrySprinkles),
            TitleTag("Dairy Free"),
            CityTag(City.cupertino),
            CityTag(City.london),
            TitleTag("Gluten Free"),
            DonutTag(Donut.fireZest),
            DonutTag(Donut.blackRaspberry),
            TitleTag("Carrots"),
            TitleTag("Donut vs Doughnut")
        )
    }
}

@Composable
fun SocialFeedTagView(modifier: Modifier = Modifier, socialFeedTag: SocialFeedTag) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TagIcon(socialFeedTag = socialFeedTag)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = socialFeedTag.title, fontSize = 12.sp)
        }
    }
}

@Composable
private fun TagIcon(socialFeedTag: SocialFeedTag) {
    val defaultModifier = Modifier
        .height(16.dp)
        .width(16.dp)

    when (socialFeedTag) {
        is SocialFeedTag.CityTag -> {
            Icon(modifier = defaultModifier, painter = buildingSymbol(), contentDescription = "")
        }

        is SocialFeedTag.DonutTag -> {
            DonutView(
                modifier = defaultModifier, donut = socialFeedTag.donut
            )
        }

        is SocialFeedTag.TitleTag -> {
            Icon(modifier = defaultModifier, painter = buildingSymbol(), contentDescription = "")
        }
    }
}

@SingleDevice
@Composable
fun TagView_Preview() {
    SampleFoodTruckJCTheme {
        Column {
            tags.map {
                SocialFeedTagView(socialFeedTag = it)
            }
        }
    }
}