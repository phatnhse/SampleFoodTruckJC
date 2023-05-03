package com.phatnhse.sample_food_truck_jc.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.foodtruck.general.searchPainter
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.SampleFoodTruckJCTheme
import com.phatnhse.sample_food_truck_jc.ui.theme.RoundedLarge

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    hintText: String = "Search",
    cancelText: String = "Cancel",
    onSearch: (String) -> Unit = {},
    onCancel: () -> Unit = {}
) {
    var query by remember { mutableStateOf("") }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier
                .background(
                    color = colorScheme.surfaceVariant,
                    shape = RoundedLarge
                )
                .weight(1f)
                .height(48.dp),
            value = query,
            onValueChange = {
                query = it
                onSearch(it)
            },
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    Modifier.padding(horizontal = PaddingNormal),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = searchPainter(), contentDescription = "Search Icon")
                    Spacer(modifier = Modifier.width(PaddingNormal))
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (query.isEmpty()) {
                            Text(text = hintText)
                        }
                        innerTextField()
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(query)
            })
        )

        if (query.isNotEmpty()) {
            Text(modifier = Modifier
                .clickable {
                    query = ""
                    onCancel()
                }
                .padding(PaddingNormal), color = colorScheme.primary, text = cancelText)
        }
    }
}

@Preview
@Composable
fun SearchView_Preview() {
    SampleFoodTruckJCTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(400.dp)
        ) {
            SearchView(onSearch = {}, onCancel = {})
        }
    }
}