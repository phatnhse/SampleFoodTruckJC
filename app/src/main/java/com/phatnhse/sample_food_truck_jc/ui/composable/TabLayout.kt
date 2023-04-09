package com.phatnhse.sample_food_truck_jc.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
    //    val indicator = @Composable { tabPositions: List<TabPosition> ->
//        FancyAnimatedIndicator(tabPositions = tabPositions, selectedTabIndex = state)
//    }


    // TODO work on the animated tab layout
    var state by remember { mutableStateOf(defaultSelected) }

    Column(modifier) {
        TabRow(
            selectedTabIndex = state
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = state == index,
                    onClick = {
                        state = index
                        onTabSelected(index)
                    },
                    text = { Text(title) }
                )
            }
        }

        tabContent()
    }
}

//@Composable
//fun FancyAnimatedIndicator() {
//    val colors = listOf(
//        colorScheme.primary,
//        colorScheme.secondary,
//        colorScheme.tertiary,
//    )
//    val transition = updateTransition(selectedTabIndex)
//    val indicatorStart by transition.animateDp(
//        transitionSpec = {
//            // Handle directionality here, if we are moving to the right, we
//            // want the right side of the indicator to move faster, if we are
//            // moving to the left, we want the left side to move faster.
//            if (initialState < targetState) {
//                spring(dampingRatio = 1f, stiffness = 50f)
//            } else {
//                spring(dampingRatio = 1f, stiffness = 1000f)
//            }
//        }
//    ) {
//        tabPositions[it].left
//    }
//
//    val indicatorEnd by transition.animateDp(
//        transitionSpec = {
//            // Handle directionality here, if we are moving to the right, we
//            // want the right side of the indicator to move faster, if we are
//            // moving to the left, we want the left side to move faster.
//            if (initialState < targetState) {
//                spring(dampingRatio = 1f, stiffness = 1000f)
//            } else {
//                spring(dampingRatio = 1f, stiffness = 50f)
//            }
//        }
//    ) {
//        tabPositions[it].right
//    }
//
//    val indicatorColor by transition.animateColor {
//        colors[it % colors.size]
//    }
//
//    FancyIndicator(
//        // Pass the current color to the indicator
//        indicatorColor,
//        modifier = Modifier
//            // Fill up the entire TabRow, and place the indicator at the start
//            .fillMaxSize()
//            .wrapContentSize(align = Alignment.BottomStart)
//            // Apply an offset from the start to correctly position the indicator around the tab
//            .offset(x = indicatorStart)
//            // Make the width of the indicator follow the animated width as we move between tabs
//            .width(indicatorEnd - indicatorStart)
//    )
//}
//
//@Composable
//fun FancyTab(
//    title: String,
//    selected: Boolean,
//    onClick: (Int) -> Unit
//) {
//    Column(
//        Modifier
//            .padding(10.dp)
//            .height(50.dp)
//            .fillMaxWidth(),
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {
//        Box(
//            Modifier
//                .size(10.dp)
//                .align(Alignment.CenterHorizontally)
//                .background(
//                    color = if (selected) colorScheme.primary
//                    else colorScheme.background
//                )
//        )
//        Text(
//            text = title,
//            style = typography.bodyLarge,
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )
//    }
//}

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