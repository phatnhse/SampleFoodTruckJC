package com.phatnhse.sample_food_truck_jc.ui.composable

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingNormal
import com.phatnhse.sample_food_truck_jc.ui.theme.PaddingSmall
import com.phatnhse.sample_food_truck_jc.ui.theme.RoundedLarge
import com.phatnhse.sample_food_truck_jc.ui.theme.onBackgroundSecondary
import com.phatnhse.sample_food_truck_jc.utils.PreviewSurface
import com.phatnhse.sample_food_truck_jc.utils.SingleDevicePreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

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
    val tabBackgroundSelected = colorScheme.secondaryContainer
    var tabPositions by remember {
        mutableStateOf<List<TabPosition>>(listOf())
    }

    val transition = updateTransition(selectedTabIndex, label = "")

    val indicatorStart by transition.animateDp(
        transitionSpec = {
            spring(
                dampingRatio = 0.8F,
                stiffness = Spring.StiffnessMedium
            )
        }, label = ""
    ) {
        if (tabPositions.isEmpty()) {
            0.dp
        } else {
            tabPositions[it].left
        }
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            spring(
                dampingRatio = 0.8F,
                stiffness = Spring.StiffnessMedium
            )
        }, label = ""
    ) {
        if (tabPositions.isEmpty()) {
            0.dp
        } else {
            tabPositions[it].right
        }
    }

    Column(modifier) {
        Box(
            Modifier
                .height(IntrinsicSize.Max)
                .padding(PaddingNormal)
        ) {
            Box(
                modifier = Modifier
                    .offset(x = indicatorStart)
                    .width(indicatorEnd - indicatorStart)
                    .fillMaxHeight()
                    .padding(PaddingSmall)
                    .clip(RoundedLarge)
                    .background(color = tabBackgroundSelected)
            )
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = tabBackground,
                modifier = Modifier
                    .height(48.dp)
                    .clip(RoundedLarge),
                indicator = @Composable { positions ->
                    tabPositions = positions
                },
                divider = {}
            ) {
                tabItems.forEachIndexed { index, title ->
                    Tab(
                        interactionSource = remember { NoRippleInteractionSource() },
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            onTabSelected(index)
                        },
                        text = {
                            Text(
                                text = title, style = typography.titleSmall.copy(
                                    color = colorScheme.onBackground
                                )
                            )
                        },
                    )
                }
            }
        }

        Box(modifier = Modifier.padding(PaddingNormal)){
            tabContent()
        }
    }
}

class NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}

@SingleDevicePreview
@Composable
fun Tab_Preview() {
    PreviewSurface {
        val tabs = listOf("Tab 1", "Tab 2", "Tab 3", "Tab 4")
        var index by remember { mutableStateOf(0) }

        TabLayout(Modifier.fillMaxSize(), tabItems = tabs, tabContent = {
            Text(text = "tab selected ${tabs[index]}")
        }, onTabSelected = {
            index = it
        })
    }
}