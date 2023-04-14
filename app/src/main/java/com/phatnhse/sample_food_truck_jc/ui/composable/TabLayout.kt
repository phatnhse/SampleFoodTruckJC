package com.phatnhse.sample_food_truck_jc.ui.composable

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface


@Composable
fun TabLayout(
    modifier: Modifier = Modifier,
    titles: List<String>,
    defaultSelected: Int = 0,
    tabContent: @Composable () -> Unit,
    onTabSelected: (Int) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(defaultSelected) }
    val defaultColor = colorScheme.background
    val targetColor = colorScheme.surface

    val transition = updateTransition(targetState = selectedTabIndex, label = "")
    val indicatorLeft by transition.animateColor(
        transitionSpec = {
            spring(stiffness = Spring.StiffnessVeryLow)
        },
        label = "Indicator left"
    ) { page ->
        when (page) {
            0, 1 -> Color.Red
            else -> Color.Green
        }
    }




    Column(modifier) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = indicatorLeft,
            divider = {},
            indicator = {}
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        onTabSelected(index)
                    },
                    text = {
                        Text(
                            text = title,
                            color = Color.White
                        )
                    },
                )
            }
        }

        tabContent()
    }
}

@Preview
@Composable
fun Tab_Preview() {
    PreviewSurface {
        val tabs = listOf("Tab 1", "Tab 2", "Tab 3", "Tab 4")
        var index by remember { mutableStateOf(0) }

        TabLayout(Modifier.fillMaxSize(),
            titles = tabs,
            tabContent = {
                Text(text = "tab selected ${tabs[index]}")
            },
            onTabSelected = {
                index = it
            }
        )
    }
}