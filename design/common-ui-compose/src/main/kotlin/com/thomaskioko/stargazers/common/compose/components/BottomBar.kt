package com.thomaskioko.stargazers.common.compose.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thomaskioko.stargazers.common.compose.R
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

@Composable
fun StargazerBottomNavigation() {
    val tabs = HomeSections.values()
    val (selectedTab, setSelectedTab) = remember { mutableStateOf(HomeSections.TRENDING) }
    StargazersScaffold(
        bottomBar = {
            BottomNavigation(
                modifier = Modifier.navigationBarsHeight(additional = 56.dp)
            ) {
                tabs.forEach { tab ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = tab.icon),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = stringResource(id = tab.title))
                        },
                        alwaysShowLabel = true,
                        selected = tab == selectedTab,
                        onClick = { setSelectedTab(tab) },
                        selectedContentColor = MaterialTheme.colors.secondary,
                        unselectedContentColor = LocalContentColor.current,
                        modifier = Modifier.navigationBarsPadding()
                    )
                }
            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Crossfade(selectedTab) { section ->
            when (section) {
                HomeSections.TRENDING -> {
                    /** Navigate to screen **/
                }
                HomeSections.BROWSE -> {
                    /** Navigate to screen **/
                }
                HomeSections.BOOKMARKED -> {
                    /** Navigate to screen **/
                }
            }
        }
    }
}

@Preview("Bottom Navigation")
@Composable
fun StargazerBottomNavigationPreview() {
    StargazerTheme {
        StargazerBottomNavigation()
    }
}

@Preview("Bottom Navigation • Dark")
@Composable
fun StargazerBottomNavigationDarkPreview() {
    StargazerTheme(darkTheme = true) {
        StargazerBottomNavigation()
    }
}


private enum class HomeSections(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    TRENDING(R.string.bottom_nav_item_trending, R.drawable.ic_outline_list),
    BROWSE(R.string.bottom_nav_item_browse, R.drawable.ic_list),
    BOOKMARKED(R.string.bottom_nav_item_favorite, R.drawable.ic_bookmarks)
}
