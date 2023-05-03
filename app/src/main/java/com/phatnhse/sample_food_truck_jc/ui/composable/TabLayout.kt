package com.phatnhse.sample_food_truck_jc.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.RoundedLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.onBackgroundSecondary
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview

@Composable
fun TabLayout(
    modifier: Modifier = Modifier,
    tabItems: List<String>,
    initialPage: Int = 0,
    tabContent: @Composable () -> Unit,
    onTabSelected: (Int) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(initialPage) }
    val tabBackground = colorScheme.onBackgroundSecondary(opacity = 0.2F)
    val tabBackgroundSelected = colorScheme.surface
    var tabPositionss by remember {
        mutableStateOf<List<TabPosition>>(listOf())
    }

//    val transition = updateTransition(selectedTabIndex, label = "")
//    val indicatorStart by transition.animateDp(
//        transitionSpec = {
//            if (initialState < targetState) {
//                spring(dampingRatio = 1f, stiffness = 50f)
//            } else {
//                spring(dampingRatio = 1f, stiffness = 1000f)
//            }
//        }, label = ""
//    ) {
//        tabPositions[it].left
//    }
//
//    val indicatorEnd by transition.animateDp(
//        transitionSpec = {
//            if (initialState < targetState) {
//                spring(dampingRatio = 1f, stiffness = 1000f)
//            } else {
//                spring(dampingRatio = 1f, stiffness = 50f)
//            }
//        }, label = ""
//    ) {
//        tabPositions[it].right
//    }

//    TabRowDefaults.Indicator(
//        Modifier
//            .fillMaxSize()
//            .wrapContentSize(align = Alignment.BottomStart)
//            .offset(x = indicatorStart)
//            .width(indicatorEnd - indicatorStart),
//        color = tabBackgroundSelected,
//    )

    Column(modifier) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = tabBackground,
            modifier = Modifier
                .padding(PaddingNormal)
                .clip(RoundedLarge),
            indicator = @Composable { tabPositions ->
                tabPositionss = tabPositions
            },
            divider = {}
        ) {
            tabItems.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        onTabSelected(index)
                    },
                    text = {
                        Text(
                            text = title,
                            style = typography.titleSmall.copy(
                                color = colorScheme.onBackground
                            )
                        )
                    },
                )
            }
        }

        tabContent()
    }

}

@SingleDevicePreview
@Composable
fun Tab_Preview() {
    PreviewSurface {
        val tabs = listOf("Tab 1", "Tab 2", "Tab 3", "Tab 4")
        var index by remember { mutableStateOf(0) }

        TabLayout(Modifier.fillMaxSize(),
            tabItems = tabs,
            tabContent = {
                Text(text = "tab selected ${tabs[index]}")
            },
            onTabSelected = {
                index = it
            }
        )
    }
}