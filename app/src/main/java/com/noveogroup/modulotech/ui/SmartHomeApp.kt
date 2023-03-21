package com.noveogroup.modulotech.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.noveogroup.modulotech.ui.common.DrawableImage
import com.noveogroup.modulotech.ui.navigation.NavigationHost
import com.noveogroup.modulotech.ui.navigation.SmartHomeTab
import com.noveogroup.modulotech.ui.theme.SmartHomeAppTheme

@Preview
@Composable
fun SmartHomeApp() {
    SmartHomeAppTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { SmartHomeBottomBar(navController) }
        ) { innerPaddingModifier ->
            NavigationHost(
                navController = navController,
                modifier = Modifier.padding(innerPaddingModifier)
            )
        }
    }
}

@Composable
private fun SmartHomeBottomBar(
    navController: NavController,
) {
    val bottomNavigationTabs = SmartHomeTab.values()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface
    ) {
        bottomNavigationTabs.forEach { tab ->
            BottomNavigationTab(
                tab = tab,
                isSelected = tab.route == currentRoute,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun RowScope.BottomNavigationTab(
    tab: SmartHomeTab,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    BottomNavigationItem(
        icon = { DrawableImage(tab.icon) },
        label = { Text(stringResource(tab.title)) },
        selected = isSelected,
        onClick = onClick,
        alwaysShowLabel = false,
        selectedContentColor = MaterialTheme.colors.secondary,
        unselectedContentColor = LocalContentColor.current,
        modifier = Modifier.navigationBarsPadding()
    )
}

@Preview
@Composable
private fun NavigationBottomBarPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        SmartHomeBottomBar(navController = navController)
    }
}
